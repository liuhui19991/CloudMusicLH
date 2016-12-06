package cn.world.liuhui.utils;

import android.content.Context;

import cn.world.liuhui.BaseApplication;

/**
 * SharedPreferences的工具类
 * Created by liuhui on 2015/6/27.
 */
public class SpUtil {
    public static final String SP_NAME = "data";

    public static void put(String key, String value) {
        BaseApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static void put(String key, boolean value) {
        BaseApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return BaseApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return BaseApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getString(key, defValue);
    }


}
