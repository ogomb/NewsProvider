package com.example.ogomb.myapplication;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class DetailNewsActivity extends AppCompatActivity {
    private final static String EXTRA_URL = "com.example.ogomb.myapplication.url_name";
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        webView = findViewById(R.id.news_web_view);

        progressBar = findViewById(R.id.news_progress_bar);
        progressBar.setMax(100);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
               if (newProgress == 100){
                   progressBar.setVisibility(View.GONE);
               }else {
                   progressBar.setVisibility(View.VISIBLE);
                   progressBar.setProgress(newProgress);
               }
            }

            public void onReceivedTitle(WebView webView, String title){
                android.support.v7.app.ActionBar actionBar = getSupportActionBar();
                actionBar.setSubtitle(title);
            }

        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                return false;
            }
        });
        webView.loadUrl(getIntent().getStringExtra(EXTRA_URL));

    }


    public static Intent newIntent(Context context, String news_url){
        Intent intent = new Intent(context, DetailNewsActivity.class);
        intent.putExtra(EXTRA_URL, news_url);
        return intent;
    }
}
