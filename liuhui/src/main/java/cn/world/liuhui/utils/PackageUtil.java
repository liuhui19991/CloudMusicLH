package cn.world.liuhui.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by liuhui on 2015/11/15.
 */

public class PackageUtil {
    /**
     * 从包信息中获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "-1";
    }
}
