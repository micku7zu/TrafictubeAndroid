package com.micutu.trafictube.Crawler;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.micutu.trafictube.Crawler.Responses.PostResponse;
import com.micutu.trafictube.Crawler.Responses.VideoResponse;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.Data.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetPostSingleton {
    private final static String TAG = GetPostSingleton.class.getSimpleName();

    public static void getPostVideo(Context context, String postLink, final VideoResponse videoResponse) {
        if (postLink == null) {
            videoResponse.onResponse(null, new HashMap<String, Object>());
            return;
        }

        StringRequest request = new StringRequest(Request.Method.GET, postLink, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    videoResponse.onResponse(getVideoFromPostPage(response), new HashMap<String, Object>());
                } catch (final Exception e) {
                    System.out.println(response);
                    //videoResponse.onResponse(null, (new HashMap<String, Object>() {{
                    //    put("error", Log.getStackTraceString(e));
                    //}}));
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                videoResponse.onResponse(null, (new HashMap<String, Object>() {{
                    put("error", error.getMessage());
                }}));
            }
        });


        VolleySingleton.makeRequest(context, request);
    }

    public static void getPost(Context context, String postLink, final PostResponse postResponse) {
        if (postLink == null) {
            postResponse.onResponse(null, new HashMap<String, Object>());
            return;
        }

        StringRequest request = new StringRequest(Request.Method.GET, postLink, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    postResponse.onResponse(parsePostPage(response), new HashMap<String, Object>());
                } catch (final Exception e) {
                    postResponse.onResponse(null, (new HashMap<String, Object>() {{
                        put("error", Log.getStackTraceString(e));
                    }}));
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                postResponse.onResponse(null, (new HashMap<String, Object>() {{
                    put("error", error.getMessage());
                }}));
            }
        });


        VolleySingleton.makeRequest(context, request);
    }

    private static Video getVideoFromPostPage(String content) {
        if (!content.contains("class=\"youtube-player\"")) {
            return null;
        }

        content = content.split("class=\"youtube-player\"")[1].split("\">")[0];

        Video video = new Video();

        video.setId(content.split("data-id=\"")[1].split("\"")[0]);
        video.setType(content.split("data-vt=\"")[1].split("\"")[0]);
        video.setImageUrl(content.split("data-src=\"")[1].split("\"")[0]);

        return video;
    }

    private static Post parsePostPage(String content) {
        if (!content.contains("the-main-postPost")) {
            return null;
        }

        content = content.split("id=\"post-")[1].split("id=\"comments")[0];

        Post post = new Post();
        User user = new User();

        String temp = content.split("ugat de <a href=\"")[1].split("</a></span>")[0];
        user.setName(temp.split("\">")[1]);
        user.setUsername(temp.split("/author/")[1].split("/\"")[0]);
        user.setAvatar(content.split("srcset='")[1].split("' class=")[0]);

        post.setTitle(content.split("entry-title\">")[1].split("</h1>")[0]);
        post.setUser(user);
        post.setId(Integer.parseInt(content.split("thumbs_rating_vote\\(")[1].split(", ")[0]));
        post.setVotes(Integer.parseInt(content.split("thumbs-rating-up")[1].split("</span>")[0].split(">")[1]));
        post.setTimeAgo(content.split("post-date\">")[1].split("</p>")[0]);
        try {
            post.setImage(content.split("data-src=\"")[1].split("\"")[0]);
        } catch (Exception e) {
            post.setImage(null);
        }

        List<String> tagsList = new ArrayList<String>();
        String[] tags = content.split("rel=\"tag\">");
        try {
            for (int i = 1; i < tags.length; i++) {
                tagsList.add(tags[i].split("</a>")[0]);
            }
        } catch (Exception e) {
            tagsList = null;
        }
        post.setTags(tagsList);
        post.setHtmlContent(content.split("the-content\" itemprop=\"text\">")[1].split("</div>")[0]);


        return post;
    }

}
