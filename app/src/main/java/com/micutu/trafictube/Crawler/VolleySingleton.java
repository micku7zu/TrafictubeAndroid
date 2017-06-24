package com.micutu.trafictube.Crawler;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.micutu.trafictube.Caches.LruBitmapCache;

public class VolleySingleton {
    private static VolleySingleton instance = null;
    private RequestQueue queue = null;
    private RequestQueue imageQueue = null;
    private ImageLoader imageLoader = null;

    private VolleySingleton(Context context) {
        queue = Volley.newRequestQueue(context);
        imageQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(imageQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(context)));
    }

    private static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }

        return instance;
    }

    public static void makeRequest(Context context, Request request) {
        VolleySingleton.makeRequest(context, request, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2);
    }

    public static void makeRequest(Context context, Request request, Integer timeout) {
        /* set default timeout and 2 retries */
        Log.d("REQUEST", request.getUrl() + ", TIMEOUT: " + timeout);
        request.setRetryPolicy(new DefaultRetryPolicy(timeout, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getInstance(context).queue.add(request);
    }

    public static ImageLoader getImageLoader(Context context) {
        return getInstance(context).imageLoader;
    }
}
