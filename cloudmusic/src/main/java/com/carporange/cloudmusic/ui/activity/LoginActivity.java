package com.carporange.cloudmusic.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.SpUtil;

/**
 * Created by liuhui on 2016/6/15.
 */
public class LoginActivity extends BaseActivity implements OnClickListener, View.OnLayoutChangeListener {

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
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    //屏幕高度
    private int screenHeight = 0;
    private LinearLayout linearLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        toolbar.setTitle("登录");
    }

    @Override
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            //5.0以上可以直接设置 statusbar颜色
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void initViews() {
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 4;
        mScrollView = findView(R.id.login_scrollview);
        mForgetLayout = findView(R.id.forget_linear);
        linearLayout = findView(R.id.root_layout);
        username = findView(R.id.login_account);
        //动态设置帐号名可见
        username.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        password =  findView(R.id.login_pwd);
        passVisiable = findView(R.id.pass_visiable);
        forgetpassword = findView(R.id.login_find_pwd);
        login = findView(R.id.login_submit_btn);
        checkBox = findView(R.id.remember_checkbox);
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
                        username.setFocusable(true);
                        username.requestFocus();
                        username.setFocusableInTouchMode(true);
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
                    startActivity(new Intent(mContext, MainActivity.class));
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
    protected void onResume() {
        super.onResume();
        linearLayout.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Message msg = Message.obtain();
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            msg.what = KEYBOARD_SHOW;
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            msg.what = KEYBOARD_HIDDEN;
        }
        mHandler.sendMessage(msg);
    }
}
