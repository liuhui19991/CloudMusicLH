package com.carporange.cloudmusic.ui.activity;

import android.content.Intent;
import android.os.SystemClock;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseActivity;

/**
 * Created by liuhui on 2016/6/27.
 */
public class SplashActivity extends BaseActivity {
    @Override
    public void initActionBar() {

    }

    @Override
    public void initViews() {
            setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }).start();
    }
}
