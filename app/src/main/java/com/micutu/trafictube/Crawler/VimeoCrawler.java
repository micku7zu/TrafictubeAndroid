package com.micutu.trafictube.Crawler;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VimeoCrawler {


    //find a good way to get vimeo video
    public static void getVimeoVideoDirectUrlYoutubeDl(Context context, final String vimeoId, final VimeoResponse vimeoResponse) {
        StringRequest request = new StringRequest(Request.Method.GET, "http://178.62.229.78:45321/api/info?url=https://vimeo.com/" + vimeoId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            vimeoResponse.onResponse(parseQQVimeo(response));
                        } catch (final Exception e) {
                            vimeoResponse.onResponse("");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                vimeoResponse.onResponse("");
            }
        });

        VolleySingleton.makeRequest(context, request);
    }

    private static String parseQQVimeo(String content) {
        try {
            JSONObject json = new JSONObject(content);
            JSONArray formats = json.getJSONObject("info").getJSONArray("formats");

            String url = "";
            int max = 0;
            for (int i = 0; i < formats.length(); i++) {
                JSONObject format = formats.getJSONObject(i);

                if (format.getString("format_id").contains("http")) {
                    int resolution = format.getInt("height");
                    if (resolution > max) {
                        max = resolution;
                        url = format.getString("url");
                    }
                }
            }

            return url;
        } catch (Exception e) {
            Log.d("TEST", "exceptie");
            e.printStackTrace();
            return "";
        }
    }

    public static void getVimeoVideoDirectUrl(Context context, String vimeoUrl, final VimeoResponse vimeoResponse) {
        final WebView webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webView.setWebViewClient(new WebViewClient() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
                    String url = request.getUrl().toString();
                    if (url.contains("expires=")) {
                        vimeoResponse.onResponse(url);
                    }
                    return super.shouldInterceptRequest(view, url);
                }

            });
        } else {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
                    if (url.contains("expires=")) {
                        vimeoResponse.onResponse(url);
                    }
                    return super.shouldInterceptRequest(view, url);
                }

            });
        }
        webView.loadUrl(vimeoUrl);
    }


    public interface VimeoResponse {
        void onResponse(String vimeoDirectUrl);
    }
}
