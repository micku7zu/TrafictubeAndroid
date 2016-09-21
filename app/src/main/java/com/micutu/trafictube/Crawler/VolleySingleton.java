package com.micutu.trafictube.Crawler;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instance = null;
    private RequestQueue queue = null;

    private VolleySingleton(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static void makeRequest(Context context, Request request) {
        if(instance == null) {
            instance = new VolleySingleton(context);
        }

        instance.queue.add(request);
    }
}
