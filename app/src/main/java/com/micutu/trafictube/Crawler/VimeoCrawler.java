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


public class VimeoCrawler {


    //find a good way to get vimeo video
    public static void getVimeoVideoDirectUrlSavedeo(Context context, String vimeoId, final VimeoResponse vimeoResponse) {
        StringRequest request = new StringRequest(Request.Method.GET, "https://qqvimeo.com/" + vimeoId,
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
        String ret = "";

        try {
            String[] temp = content.split("<span class=\"fa-download\"></span>")[0].split("href=\"");
            ret = temp[temp.length - 1].split("\"")[0];
            ret = Html.fromHtml(ret).toString();
        } catch (Exception e) {
        }

        return ret;
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
