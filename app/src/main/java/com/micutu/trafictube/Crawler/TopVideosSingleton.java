package com.micutu.trafictube.Crawler;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.micutu.trafictube.Data.Site;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.Data.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopVideosSingleton {
    private final static String TAG = NormalVideos.class.getSimpleName();

    private static TopVideosSingleton instance = null;
    private String content = null;
    private long lastUpdate = 0;

    protected TopVideosSingleton() {
        content = null;
        lastUpdate = 0;
    }

    public static TopVideosSingleton getInstance() {
        if (instance == null) {
            instance = new TopVideosSingleton();
        }

        return instance;
    }

    public static void getVideoOfTheDay(Context context, final VideoListResponse listener) {
        getInstance().getContent(context, new TopVideosSingleton.Response() {
            @Override
            public void onResponse(String content) {
                try {
                    if (content == null) {
                        throw new Exception("Can't get content from site.");
                    }

                    listener.onResponse(getInstance().getVideoOfTheDayFromResponse(content), new HashMap<String, Object>());
                } catch (final Exception e) {
                    listener.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", e.toString());
                    }}));
                }
            }
        });
    }

    private Video getVideoOfTheDayFromResponse(String content) {
        content = content.split("class=\"clipul-zilei\">")[1].split("<div class=\"site")[0];

        return getVideoFromHtml(content);
    }

    public static void getGeneralTopVideos(Context context, final VideosListResponse listener) {
        getInstance().getContent(context, new TopVideosSingleton.Response() {
            @Override
            public void onResponse(String content) {
                try {
                    if (content == null) {
                        throw new Exception("Can't get content from site.");
                    }

                    listener.onResponse(getInstance().getGeneralTopVideosFromResponse(content), new HashMap<String, Object>());
                } catch (final Exception e) {
                    listener.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", e.toString());
                    }}));
                }
            }
        });
    }

    public static void getWeeklyTopVideos(Context context, final VideosListResponse listener) {
        getInstance().getContent(context, new TopVideosSingleton.Response() {
            @Override
            public void onResponse(String content) {
                try {
                    if (content == null) {
                        throw new Exception("Can't get content from site.");
                    }

                    listener.onResponse(getInstance().getWeeklyTopVideosFromResponse(content), new HashMap<String, Object>());
                } catch (final Exception e) {
                    listener.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", e.toString());
                    }}));
                }
            }
        });
    }

    private List<Video> getWeeklyTopVideosFromResponse(String content) {
        content = content.split("ULTIMA SAPTAMANA</h4>")[1].split("<h4>Top General")[0];

        String temps[] = content.split("the-video");
        List<Video> videos = new ArrayList<Video>();
        for (int i = 1; i < temps.length; i++) {
            videos.add(getVideoFromHtml(temps[i]));
        }
        return videos;
    }

    private List<Video> getGeneralTopVideosFromResponse(String content) {
        content = content.split("General</h4>")[1].split("<h4>Top")[0];

        String temps[] = content.split("the-video");
        List<Video> videos = new ArrayList<Video>();
        for (int i = 1; i < temps.length; i++) {
            videos.add(getVideoFromHtml(temps[i]));
        }
        return videos;
    }

    private Video getVideoFromHtml(String html) {
        Video video = new Video();
        User user = new User();

        /* ugly crawler */
        String[] titleArray = html.split("<h3")[1].split("</a>")[0].split("\">");


        video.setTitle(titleArray[titleArray.length - 1]);
        video.setLink(html.split("href=\"")[1].split("\"")[0]);
        user.setName(html.split("\"author\">")[1].split("</a>")[0].split("\">")[1]);
        user.setUsername(html.split("\"author\">")[1].split("/\" tit")[0].split("author/")[1]);
        video.setUser(user);
        video.setImage(html.split("background-image: url\\(")[1].split("\\);\">")[0].replaceAll("\\s+", ""));
        video.setVotes(Integer.parseInt(html.split("class=\"voturi\">")[1].split(" voturi</span>")[0].split("</i>")[1]));

        return video;
    }

    private void getContent(Context context, final TopVideosSingleton.Response response) {
        TopVideosSingleton instance = getInstance();

        Log.d(TAG, "Content: " + instance.content + " - Time: " + (getCurrentTime() - instance.lastUpdate));

        /* 5 minute cache */
        if (instance.content == null || (getCurrentTime() - instance.lastUpdate) > 300) {
            Log.d(TAG, "Get new content");
            getNewContent(context, response);
            return;
        }

        Log.d(TAG, "Get content from cache");
        response.onResponse(instance.content);
    }

    private void getNewContent(Context context, final Response onResponse) {
        String link = Site.link + "/top-rated/";
        StringRequest request = new StringRequest(Request.Method.GET, link, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TopVideosSingleton.getInstance().content = content;
                onResponse.onResponse(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TopVideosSingleton.getInstance().content = content;
                onResponse.onResponse(null);
            }
        });

        Log.d(TAG, "Make request");
        VolleySingleton.makeRequest(context, request);
    }

    private long getCurrentTime() {
        return System.currentTimeMillis() / 1000L;
    }

    private interface Response {
        public void onResponse(String content);
    }
}