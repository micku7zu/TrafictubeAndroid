package com.micutu.trafictube.Crawler;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.micutu.trafictube.Data.VideoPost;

import java.util.HashMap;

public class GetVideoSingleton {
    private final static String TAG = GetVideoSingleton.class.getSimpleName();

    public static void getVideo(Context context, String videoLink, final VideoResponse videoResponse) {
        if (videoLink == null) {
            videoResponse.onResponse(null, new HashMap<String, Object>());
            return;
        }

        StringRequest request = new StringRequest(Request.Method.GET, videoLink, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

    private static VideoPost parseVideoPage(String content) {
        if(!content.contains("the-main-videoPost")) {
            return null;
        }

        VideoPost videoPost = new VideoPost();


        return videoPost;
    }

}
