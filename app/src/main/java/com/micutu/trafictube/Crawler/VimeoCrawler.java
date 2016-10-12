package com.micutu.trafictube.Crawler;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class VimeoCrawler {

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
