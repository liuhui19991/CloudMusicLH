package com.carporange.cloudmusic.ui.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.L;


/**
 * Created by liuhui on 2016/7/27.
 */
public class WebPageActivity extends BaseActivity {

    private String url;
    private String name = "";
    private ProgressBar bar;
    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fun_webview;
    }

    @Override
    public void initActionBar() {
        //传递的数据
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        name = intent.getStringExtra("title");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initViews() {
        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        webView = (WebView) findViewById(R.id.myWebView);

        initWebSetting();
        webView.loadUrl(url);
    }


    /**
     * 设置webview属性
     */
    private void initWebSetting() {

        WebSettings ws = webView.getSettings();

        ws.setJavaScriptEnabled(true); // 设置支持javascript脚本
        ws.setAllowFileAccess(true); // 允许访问文件
        ws.setDefaultTextEncodingName("utf-8"); // 设置文本编码
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);// 设置缓存模式
        ws.setTextSize(WebSettings.TextSize.NORMAL);

        ws.setUseWideViewPort(true);// 这个很关键  自适应大小
        ws.setLoadWithOverviewMode(true);

        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClientCustom());
        webView.setWebChromeClient(new MyWebChromeClient());

    }

    private class WebViewClientCustom extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);// 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                bar.setVisibility(View.INVISIBLE);
            } else {
                if (View.INVISIBLE == bar.getVisibility()) {
                    bar.setVisibility(View.VISIBLE);
                }
                bar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        webView.pauseTimers();

    }

    @Override
    public void onResume() {
        super.onResume();
        webView.resumeTimers();
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            webView.loadUrl("about:blank");
//            finish();
//            return true;
//        }
//        return false;
//    }

    @Override
    public void onBackPressed() {
        webView.loadUrl("about:blank");
        finish();
    }
}

