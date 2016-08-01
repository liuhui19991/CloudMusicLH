package com.carporange.cloudmusic.ui.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.util.SpUtil;

import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/6/27.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isNightMode = SpUtil.getBoolean("nightMode", false);
        setTheme(isNightMode ? R.style.NightTheme : R.style.DayTheme);
        setContentView(getLayoutId());
        ButterKnife.bind(this);//绑定黄油刀
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 这个方法是让状态栏变成透明色,让窗体可以填充,下面的19意思是19版本以上此方法有用,对应的dimens(v19)
     */
    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
//            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
