package com.world.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView contentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentWebView = (WebView) findViewById(R.id.webview);
        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        contentWebView.loadUrl("file:///android_asset/web.html");
        //后面这个android是别名,和JS中的onclick后面的别名对应
        contentWebView.addJavascriptInterface(this, "android");


        //Button按钮 无参调用HTML js方法
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 无参数调用 JS的方法
                contentWebView.loadUrl("javascript:javacalljs()");

            }
        });
        //Button按钮 有参调用HTML js方法
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传递参数调用JS的方法
                contentWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");
            }
        });


    }

    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void startFunction() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).show();

            }
        });


    }
}
