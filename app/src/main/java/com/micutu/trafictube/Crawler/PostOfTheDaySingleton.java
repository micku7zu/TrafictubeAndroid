package com.micutu.trafictube.Crawler;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.micutu.trafictube.Caches.LruBitmapCache;
import com.micutu.trafictube.Crawler.Responses.PostListResponse;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Data.Site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostOfTheDaySingleton {
    private static PostOfTheDaySingleton instance = null;

    private Post post = null;

    private PostOfTheDaySingleton(Context context) {
    }

    private static PostOfTheDaySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new PostOfTheDaySingleton(context);
        }

        return instance;
    }

    public static void setPost(Context context, String response) {
        try {
            PostOfTheDaySingleton instance = getInstance(context);
            instance.post = instance.getPostOfTheDayFromResponse(response);
        } catch (Exception ignored) {
            Log.d("PostOfTheDay", "eroare la setPost");
        }
    }

    public static void getPost(Context context, final PostListResponse listener) {
        final PostOfTheDaySingleton instance = getInstance(context);

        if (instance.post != null) {
            //Log.d("PostOfTheDay", "il aveam deja.");
            listener.onResponse(instance.post, null);
            return;
        }

        instance.getPostFromSite(context, new PostListResponse() {

            @Override
            public void onResponse(Post post, Map<String, Object> extra) {
                instance.post = post;
                listener.onResponse(post, null);
            }
        });
    }

    private void getPostFromSite(Context context, final PostListResponse listener) {
        StringRequest request = new StringRequest(Request.Method.GET, Site.link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listener.onResponse(getPostOfTheDayFromResponse(response), null);
                        } catch (final Exception e) {
                            listener.onResponse(null, (new HashMap<String, Object>() {{
                                put("error", Log.getStackTraceString(e));
                            }}));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                listener.onResponse(null, (new HashMap<String, Object>() {{
                    put("error", error.getMessage());
                }}));
            }
        });

        VolleySingleton.makeRequest(context, request);
    }

    private Post getPostOfTheDayFromResponse(String content) {
        content = content.split("class=\"clipul-zilei\">")[1].split("<div class=\"site")[0];

        return TopPostsSingleton.getPostFromHtml(content);
    }
}
