package com.carporange.cloudmusic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.util.SpUtil;
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
    private final int PASSWORD_INVISIABLE = 1003;
    private EditText username;
    private EditText password;
    private Button login;
    private ImageView passVisiable;
    private Button forgetpassword;
    private String names;
    private String passs;
    private CheckBox checkBox;
    private boolean isRemberPassword;
    private String us = "username";
    private String ps = "password";

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
        //动态设置帐号名可见
        username.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        password = (EditText) findViewById(R.id.login_pwd);
        passVisiable = (ImageView) findViewById(R.id.pass_visiable);
        forgetpassword = (Button) findViewById(R.id.login_find_pwd);
        login = (Button) findViewById(R.id.login_submit_btn);
        checkBox = (CheckBox) findViewById(R.id.remember_checkbox);
        //一下两种方式都可以获取资源文件中的值
        names = getResources().getString(R.string.username);
        passs = this.getString(R.string.password);
        initListeners();
    }

    private void initListeners() {
        passVisiable.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);
        login.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRemberPassword = isChecked;
            }
        });
        boolean isRember = SpUtil.getBoolean("isRemberPassword", false);
        if (isRember) {//上次是记住密码
            checkBox.setChecked(true);
            username.setText(SpUtil.getString(us, ""));
            password.setText(SpUtil.getString(ps, ""));
        }
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
                    case PASSWORD_INVISIABLE:
                        passVisiable.setImageResource(R.mipmap.icon_login_gone);
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    default:
                        break;
                }
            }
        };

        mResizeRelativeLayout.setOnResizeListener(new ResizeRelativeLayout.OnResizeListener() {
            @Override
            public void OnResize(int w, int h, int oldw, int oldh) {
                Message msg = new Message();
                if (h < oldh) {//软键盘弹出
                    msg.what = KEYBOARD_SHOW;
                } else {//软件盘关闭
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
                if (name.equals("") && pass.equals("")) {
                    Snackbar.make(username, "请输入用户名或密码", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (name.equals(names) && pass.equals(passs)) {
                    if (isRemberPassword) {
                        SpUtil.put(us, name);
                        SpUtil.put(ps, pass);
                        SpUtil.put("isRemberPassword", true);
                    } else {
                        SpUtil.put("isRemberPassword", false);
                    }
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Snackbar.make(username, "密码不正确", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.pass_visiable://显示密码
                passVisiable.setImageResource(R.mipmap.icon_login_visible);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                mHandler.sendEmptyMessageDelayed(PASSWORD_INVISIABLE, 3000);
                break;
            case R.id.login_find_pwd://找回密码
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("您的密码!");
                builder.setMessage("帐号:" + names + "\n" + "密码:" + passs);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", null);
                builder.show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
