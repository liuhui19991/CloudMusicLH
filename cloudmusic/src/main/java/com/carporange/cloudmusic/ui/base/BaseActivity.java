package com.carporange.cloudmusic.ui.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.util.SpUtil;

/**
 * Created by liuhui on 2016/6/27.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isNightMode = SpUtil.getBoolean("nightMode", false);
        setTheme(isNightMode ? R.style.NightTheme : R.style.DayTheme);
        initActionBar();
        initViews();
        initWindow();
    }

    public abstract void initActionBar();

    public abstract void initViews();

    public void getData() {

    }

    public void setListeners() {

    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

}
