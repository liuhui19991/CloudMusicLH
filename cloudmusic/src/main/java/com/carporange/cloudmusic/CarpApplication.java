package com.carporange.cloudmusic;

import android.app.Application;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by Liyuchen on 2016/6/14.
 * email:987424501@qq.com
 * phone:18298376275
 */
public class CarpApplication extends Application {
    private static CarpApplication application;

    public static CarpApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        NoHttp.initialize(application);
        Logger.setTag("LH..");
//        Logger.setDebug(true);  //使网络请求打印log
    }
}
