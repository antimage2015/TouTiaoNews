package com.crazy.toutiaonews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *  Created by antimage on 2016/1/16.
 */
public class NewsAllActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String str_urls = intent.getStringExtra("NEWS_URLS");

        WebView webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(str_urls);

        setContentView(webView);

    }
}
