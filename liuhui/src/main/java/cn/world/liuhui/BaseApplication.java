package cn.world.liuhui;

import android.app.Application;

/**
 * Created by liuhui on 2016/10/11.
 */

public class BaseApplication extends Application {
    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }

    public static BaseApplication getInstance() {
        return mBaseApplication;
    }
}
