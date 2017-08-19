package com.example.gilad.panoptic;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Raaz on 19/08/2017.
 */

class MyWebClient extends WebViewClient {
    @Override
    // show the web page in webview but not in web browser
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}