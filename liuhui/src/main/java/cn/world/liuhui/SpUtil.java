package cn.world.liuhui;

import android.content.Context;

/**
 * Created by liuhui on 2016/6/27.
 */
public class SpUtil {
    public static void put(String key, String value) {
        BaseApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static void put(String key, boolean value) {
        BaseApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return BaseApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return BaseApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE).getString(key, defValue);
    }


}
