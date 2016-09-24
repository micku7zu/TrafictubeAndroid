package com.micutu.trafictube.Crawler;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.micutu.trafictube.Caches.LruBitmapCache;

public class VolleySingleton {
    private static VolleySingleton instance = null;
    private RequestQueue queue = null;
    private ImageLoader imageLoader = null;

    private VolleySingleton(Context context) {
        queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue, new LruBitmapCache(LruBitmapCache.getCacheSize(context)));
    }

    private static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }

        return instance;
    }

    public static void makeRequest(Context context, Request request) {
        getInstance(context).queue.add(request);
    }

    public static ImageLoader getImageLoader(Context context) {
        return getInstance(context).imageLoader;
    }
}
