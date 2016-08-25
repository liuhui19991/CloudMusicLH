package com.carporange.cloudmusic.ui.activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.T;


/**
 * Created by liuhui on 2016/7/27.
 */
public class WebPageActivity extends BaseActivity {

    private String url;
    private String name = "";
    private ProgressBar bar;
    private WebView webView;
    private Dialog mWaitDialog;
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
        if (mWaitDialog == null) {
            mWaitDialog = new Dialog(mContext, R.style.TRANSDIALOG);
        }
        mWaitDialog.setContentView(R.layout.trans_dialog);
        mWaitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        initWebSetting();
        webView.loadUrl(url);
    }


    /**
     * 设置webview属性
     */
    private void initWebSetting() {
        WebSettings settings = webView.getSettings();
//        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);//设置支持js功能
        settings.setUseWideViewPort(true);// 这个很关键  自适应大小
        settings.setLoadWithOverviewMode(true);

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

    }

    private class MyWebViewClient extends WebViewClient {
        //开始加载网页
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mWaitDialog.show();
        }

        //所有链接跳转会走此方法
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);//这句话的意思是在跳转view时强制在当前view中加载
            return true;
        }

        //网页加载结束
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
            mWaitDialog.dismiss();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            //进度发生变化 newprogress
            if (newProgress == 100) {
                bar.setVisibility(View.INVISIBLE);
            } else {
                if (View.INVISIBLE == bar.getVisibility()) {
                    bar.setVisibility(View.VISIBLE);
                }
                bar.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            // 网页标题
            System.out.println("网页标题:" + title);
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

    @Override
    public void onBackPressed() {
        webView.loadUrl("about:blank");
        finish();
    }

    /**
     * 重写这个方法就能在toolbar上面出现菜单按钮
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            webView.getSettings().setTextZoom(50);
            return true;
        } else if (item.getItemId() == R.id.action_two) {
            webView.getSettings().setTextZoom(80);
            return true;
        } else if (item.getItemId() == R.id.action_three) {
            webView.getSettings().setTextZoom(100);
            return true;
        } else if (item.getItemId() == R.id.action_four) {
            webView.getSettings().setTextZoom(120);
            return true;
        } else if (item.getItemId() == R.id.action_five) {
            webView.getSettings().setTextZoom(140);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

