package cn.world.liuhui.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by luhui on 2016/10/24.
 */

/***************
 * 使用例子
 *********************/
//1.mvp模式
//public class SampleActivity extends BaseActivity<MainView,MainPresenter> implements MainView {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {
    public P mPresenter;
    public Activity mContext;
    public Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
//        ButterKnife.bind(this);
        initToolBar();
        initWindow();
        initView();
        mPresenter = initPresent();
    }

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

    /*protected void initWindow(boolean transparent, int color) {
        if (transparent) StatusBarCompat.translucentStatusBar(this);// 设置状态栏全透明
        else StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, color));// 沉浸式状态栏
    }*/

    protected void initToolBar() {
//        mToolbar = findView(R.id.toolbar);
//        mToolbar.setTitle("");
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract P initPresent();

    protected <T> T findView(int id) {
        return (T) findViewById(id);
    }

    public void startactivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        startActivity(intent);
    }
}
