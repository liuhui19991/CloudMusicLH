package cn.world.liuhui.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * 单位转换
 * Created by liuhui on 2015/7/27.
 */
public class DensityUtil {

    private static boolean isInitialize = false;
    /**
     * 屏幕宽度
     **/
    public static int screenWidth;
    /**
     * 屏幕高度
     **/
    public static int screenHeight;
    /**
     * 屏幕密度
     **/
    public static int screenDpi;
    /**
     * 逻辑密度, 屏幕缩放因子
     */
    public static float density = 1;
    /**
     * 字体缩放因子
     */
    public static float scaledDensity;

    /**
     * 初始化屏幕宽度和高度
     */
    public static void initScreen(Activity activity) {
        if (isInitialize) return;
        isInitialize = true;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metric = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(metric);
        } else {
            display.getMetrics(metric);
        }

        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
        screenDpi = metric.densityDpi;
        density = metric.density;
        scaledDensity = metric.scaledDensity;
    }

    /**
     * 是否是横屏
     */
    public static boolean isHorizontal() {
        return screenWidth > screenHeight;
    }

    /**
     * dp转换px
     */
    public static int dp2px(Context context,float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dp
     */

    public static int px2dp(Context context,int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context,float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context,float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getDisplayWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }
}
