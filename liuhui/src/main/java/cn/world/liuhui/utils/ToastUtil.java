package cn.world.liuhui.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 * Created by liuhui on 2015/6/19.
 */
public class ToastUtil {

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;

    public static void showLong(Context context, CharSequence message) {
        if (isDebug)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, CharSequence message) {
        if (isDebug)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}