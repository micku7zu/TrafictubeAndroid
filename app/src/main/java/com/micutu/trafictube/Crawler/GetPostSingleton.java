package com.micutu.trafictube.Crawler;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.micutu.trafictube.Data.Post;

import java.util.HashMap;

public class GetPostSingleton {
    private final static String TAG = GetPostSingleton.class.getSimpleName();

    public static void getPost(Context context, String postLink, final PostResponse postResponse) {
        if (postLink == null) {
            postResponse.onResponse(null, new HashMap<String, Object>());
            return;
        }

        StringRequest request = new StringRequest(Request.Method.GET, postLink, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

    private static Post parsePostPage(String content) {
        if(!content.contains("the-main-postPost")) {
            return null;
        }

        Post post = new Post();


        return post;
    }

}
