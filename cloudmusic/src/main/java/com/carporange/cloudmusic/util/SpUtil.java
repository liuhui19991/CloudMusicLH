package com.carporange.cloudmusic.util;

import android.content.Context;

import com.carporange.cloudmusic.CarpApplication;

/**
 * Created by liuhui on 2016/6/27.
 */
public class SpUtil {
    public static void put(String key, String value) {
        CarpApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }
    public static void put(String key, boolean value) {
        CarpApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return CarpApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return CarpApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).getString(key, defValue);
    }


}
