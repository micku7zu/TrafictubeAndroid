package com.micutu.trafictube.Crawler;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.micutu.trafictube.Crawler.Responses.PostListResponse;
import com.micutu.trafictube.Crawler.Responses.PostsListResponse;
import com.micutu.trafictube.Data.Site;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.Data.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopPostsSingleton {
    private final static String TAG = NormalPosts.class.getSimpleName();

    private static TopPostsSingleton instance = null;
    private String content = null;
    private long lastUpdate = 0;

    protected TopPostsSingleton() {
        content = null;
        lastUpdate = 0;
    }

    public static TopPostsSingleton getInstance() {
        if (TopPostsSingleton.instance == null) {
            TopPostsSingleton.instance = new TopPostsSingleton();
        }

        return TopPostsSingleton.instance;
    }

    public static void getPostOfTheDay(Context context, final PostListResponse listener) {
        getInstance().getContent(context, new TopPostsSingleton.Response() {
            @Override
            public void onResponse(String content) {
                try {
                    if (content == null) {
                        throw new Exception("Can't get content from site.");
                    }

                    listener.onResponse(getInstance().getPostOfTheDayFromResponse(content), new HashMap<String, Object>());
                } catch (final Exception e) {
                    listener.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", Log.getStackTraceString(e));
                    }}));
                }
            }
        });
    }

    private Post getPostOfTheDayFromResponse(String content) {
        content = content.split("class=\"clipul-zilei\">")[1].split("<div class=\"site")[0];

        return getPostFromHtml(content);
    }

    public static void getGeneralTopPosts(Context context, final PostsListResponse listener) {
        getInstance().getContent(context, new TopPostsSingleton.Response() {
            @Override
            public void onResponse(String content) {
                try {
                    if (content == null) {
                        throw new Exception("Can't get content from site.");
                    }

                    listener.onResponse(getInstance().getGeneralTopPostsFromResponse(content), new HashMap<String, Object>());
                } catch (final Exception e) {
                    listener.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", Log.getStackTraceString(e));
                    }}));
                }
            }
        });
    }

    public static void getWeeklyTopPosts(Context context, final PostsListResponse listener) {
        getInstance().getContent(context, new TopPostsSingleton.Response() {
            @Override
            public void onResponse(String content) {
                try {
                    if (content == null) {
                        throw new Exception("Can't get content from site.");
                    }

                    listener.onResponse(getInstance().getWeeklyTopPostsFromResponse(content), new HashMap<String, Object>());
                } catch (final Exception e) {
                    listener.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", Log.getStackTraceString(e));
                    }}));
                }
            }
        });
    }

    private List<Post> getWeeklyTopPostsFromResponse(String content) {
        content = content.split("ULTIMA SAPTAMANA</h4>")[1].split("<h4>Top General")[0];

        String temps[] = content.split("the-video");
        List<Post> posts = new ArrayList<Post>();
        for (int i = 1; i < temps.length; i++) {
            posts.add(getPostFromHtml(temps[i]));
        }
        return posts;
    }

    private List<Post> getGeneralTopPostsFromResponse(String content) {
        content = content.split("General</h4>")[1].split("<h4>Top")[0];

        String temps[] = content.split("the-video");
        List<Post> posts = new ArrayList<Post>();
        for (int i = 1; i < temps.length; i++) {
            posts.add(getPostFromHtml(temps[i]));
        }
        return posts;
    }

    private static Post getPostFromHtml(String html) {
        Post post = new Post();
        User user = new User();

        /* ugly crawler */
        String[] titleArray = html.split("<h3")[1].split("</a>")[0].split("\">");


        post.setTitle(titleArray[titleArray.length - 1]);
        post.setLink(html.split("href=\"")[1].split("\"")[0]);
        user.setName(html.split("\"author\">")[1].split("</a>")[0].split("\">")[1]);
        user.setUsername(html.split("\"author\">")[1].split("/\" tit")[0].split("author/")[1]);
        post.setUser(user);
        post.setImage(html.split("background-image: url\\(")[1].split("\\);\">")[0].replaceAll("\\s+", ""));
        post.setVotes(Integer.parseInt(html.split("class=\"voturi\">")[1].split(" voturi</span>")[0].split("</i>")[1]));

        return post;
    }

    private void getContent(Context context, final TopPostsSingleton.Response response) {
        TopPostsSingleton instance = getInstance();

        /* 5 minute cache */
        if (instance.content == null || (getCurrentTime() - instance.lastUpdate) > 300) {
            getNewContent(context, response);
            return;
        }

        response.onResponse(instance.content);
    }

    private void getNewContent(Context context, final Response onResponse) {
        String link = Site.link + "/top-rated/";
        StringRequest request = new StringRequest(Request.Method.GET, link, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TopPostsSingleton.getInstance().content = response;
                instance.lastUpdate = getCurrentTime();
                onResponse.onResponse(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TopPostsSingleton.getInstance().content = null;
                onResponse.onResponse(null);
            }
        });

        VolleySingleton.makeRequest(context, request, 15000);
    }

    public static void getTwoDaysTopPosts(Context context, final PostsListResponse onResponse) {
        StringRequest request = new StringRequest(Request.Method.GET, Site.link, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    onResponse.onResponse(getTwoDaysTopPostsFromResponse(response), new HashMap<String, Object>());
                } catch (final Exception e) {
                    onResponse.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", Log.getStackTraceString(e));
                    }}));
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                onResponse.onResponse(null, (new HashMap<String, Object>() {{
                    put("error", error.getMessage());
                }}));
            }
        });

        VolleySingleton.makeRequest(context, request, 15000);
    }

    private static List<Post> getTwoDaysTopPostsFromResponse(String content) {
        content = content.split("class=\"top-clips\">")[1].split("class=\"latest-clips\"")[0];

        String temps[] = content.split("the-video");
        List<Post> posts = new ArrayList<Post>();
        for (int i = 1; i < temps.length; i++) {
            posts.add(getPostFromHtml(temps[i]));
        }
        return posts;
    }

    private long getCurrentTime() {
        return System.currentTimeMillis() / 1000L;
    }

    private interface Response {
        public void onResponse(String content);
    }
}