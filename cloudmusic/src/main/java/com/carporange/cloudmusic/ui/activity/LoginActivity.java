package com.carporange.cloudmusic.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.S;
import com.carporange.cloudmusic.widget.ResizeRelativeLayout;

/**
 * Created by liuhui on 2016/6/15.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private ResizeRelativeLayout mResizeRelativeLayout;
    private ScrollView mScrollView;
    private LinearLayout mForgetLayout;
    private Handler mHandler;

    private final int KEYBOARD_SHOW = 1001;
    private final int KEYBOARD_HIDDEN = 1002;
    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    public void initViews() {
        mResizeRelativeLayout = (ResizeRelativeLayout) findViewById(R.id.login_page);
        mScrollView = (ScrollView) findViewById(R.id.login_scrollview);
        mForgetLayout = (LinearLayout) findViewById(R.id.forget_linear);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = (EditText) findViewById(R.id.login_account);
        password = (EditText) findViewById(R.id.login_pwd);
        login = (Button) findViewById(R.id.login_submit_btn);
        login.setOnClickListener(this);
        initListeners();
    }

    private void initListeners() {
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case KEYBOARD_SHOW:
                        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        mForgetLayout.setVisibility(View.GONE);
                        break;
                    case KEYBOARD_HIDDEN:
                        mForgetLayout.setVisibility(View.VISIBLE);
                        break;

                    default:
                        break;
                }
            }

            ;
        };
        mResizeRelativeLayout.setOnResizeListener(new ResizeRelativeLayout.OnResizeListener() {
            @Override
            public void OnResize(int w, int h, int oldw, int oldh) {
                Message msg = new Message();
                if (h < oldh) {//软键盘弹出
                    L.e("软键盘弹出");
                    msg.what = KEYBOARD_SHOW;
                } else {
                    L.e("软键盘关闭");
                    msg.what = KEYBOARD_HIDDEN;
                }
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_submit_btn://登录
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                //一下两种方式都可以获取资源文件中的值
                String names = getResources().getString(R.string.username);
                String passs = this.getString(R.string.password);
                L.e(names+".."+name);
                L.e(passs+"----"+pass);
                if (name.equals("") && pass.equals("")) {
                    Snackbar.make(username, "请输入用户名或密码", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (name.equals(names) && pass.equals(passs)) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Snackbar.make(username, "密码不正确", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
