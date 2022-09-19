package com.sahil4.news4u;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ReadNews extends AppCompatActivity {
    WebView news;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        Intent intent = getIntent();
        String URL = intent.getStringExtra("url");
        news = findViewById(R.id.news);

        news.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String URL) {
                view.loadUrl(URL);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String URL) {
                super.onPageFinished(view, URL);
            }
        });

        news.getSettings().setLoadsImagesAutomatically(true);
        news.getSettings().setJavaScriptEnabled(true);
        news.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        news.loadUrl(URL);
    }
}