package com.carporange.cloudmusic.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
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
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import cn.world.liuhui.utils.AppCacheUtil;
import cn.world.liuhui.utils.DialogUtil;

/**
 * Created by liuhui on 2016/6/15.
 */
//这个页面的输入内容必须使用相同类型,然后在页面创建的时候再动态设置,要不然会出现屏幕上下闪动,顶部栏不能使用透明状态栏,
// 要不然会无法监听到键盘的弹起或者隐藏
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
    private final int CALL_PHONE = 88;

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
        keyHeight = screenHeight / 3;
        mScrollView = findView(R.id.login_scrollview);
        mForgetLayout = findView(R.id.forget_linear);
        linearLayout = findView(R.id.root_layout);
        username = findView(R.id.login_account);
        //动态设置帐号名可见
        username.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        password = findView(R.id.login_pwd);
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
                    AppCacheUtil.getInstance(mContext).put("liuhui", "LIUHU");//设置缓存数据
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
                //下面显示字体为绿色
                /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("您的密码!");
                builder.setMessage("帐号:" + names + "\n" + "密码:" + passs);
                builder.setNegativeButton("电话联系", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AndPermission.with(mContext)
                                .requestCode(98)
                                .permission(Manifest.permission.CAMERA)
                                .send();
                    }
                });
                builder.setPositiveButton("确定", null);
                builder.show();*/
                DialogUtil.showAlert(this, "您的密码", "帐号:" + names + "\n" + "密码:" + passs, "ok",
                        null, "中间", null, "Call", callListener, null, false, null, null);
                break;
        }
    }

    DialogInterface.OnClickListener callListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AndPermission.with(mContext)
                    .requestCode(98)
                    .permission(Manifest.permission.CAMERA)
                    .send();
        }
    };

    @PermissionYes(98)
    private void getCallPhoneYes() {
        String number = "1871081";
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @PermissionNo(98)
    private void getCallPhoneNo() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearLayout.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                               int oldRight, int oldBottom) {
        Message msg = Message.obtain();
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            msg.what = KEYBOARD_SHOW;//此处必须通过handler来更新ui
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            msg.what = KEYBOARD_HIDDEN;
        }
        mHandler.sendMessage(msg);
    }
}
