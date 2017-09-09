package com.example.gilad.panoptic;

import android.content.Intent;
import android.content.res.Configuration;

import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;


public class ArticleWebpage extends AppCompatActivity {

    private WebView browser = null;
    private ProgressBar progressBar = null;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_item_share:
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("url"));
                intent.setType("text/plain");

                startActivity(Intent.createChooser(intent, "Share using"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_webpage);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.ic_top_menu_logo);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent circleClicked = getIntent();
        browser = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        browser.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress){
                progressBar.setProgress(progress);
                if (progress == 100){
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        if (savedInstanceState == null) {
            browser.getSettings().setJavaScriptEnabled(true);
            browser.setWebViewClient(new MyWebClient());
            browser.loadUrl(circleClicked.getStringExtra("url"));
        } else {
            browser.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        browser.saveState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
