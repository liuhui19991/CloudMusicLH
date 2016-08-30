package com.carporange.cloudmusic.ui.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.activity.SwipeBackActivity;
import com.carporange.cloudmusic.util.SpUtil;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SlideSwipeBackTransformer;

import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/6/27.
 */
public abstract class BaseActivity extends SwipeBackActivity {
    public Activity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isNightMode = SpUtil.getBoolean("nightMode", false);
        setTheme(isNightMode ? R.style.NightTheme : R.style.DayTheme);
        // Init the swipe back mechanism
        SwipeBack.attach(this, Position.LEFT).setDrawOverlay(true)
                .setDividerEnabled(true)
                .setSwipeBackTransformer(new SlideSwipeBackTransformer())
                .setContentView(getLayoutId())
                .setSwipeBackView(R.layout.swipeback_default);
        ButterKnife.bind(this);//绑定黄油刀
        mContext = this;
        initActionBar();
        initViews();
        initWindow();
    }

    /**
     * @return 填充的布局资源
     */
    protected abstract int getLayoutId();

    public abstract void initActionBar();

    public abstract void initViews();

    /**
     * 这个方法是让状态栏变成透明色,让窗体可以填充,下面的19意思是19版本以上此方法有用,对应的dimens(v19)
     */
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //5.0以上可以直接设置 statusbar颜色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 页面开启的切换动画
     *//*
    protected void start() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    */

    /**
     * 页面关闭的切换动画
     *//*
    protected void finishActivity() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/
    protected <V extends View> V findView(int id) {
        return (V) findViewById(id);
    }

}
