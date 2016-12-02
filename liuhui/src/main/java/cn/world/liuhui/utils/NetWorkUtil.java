package cn.world.liuhui.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by liuhui on 2016/11/2.
 */

public class NetWorkUtil {
    /**
     * 判断当前网络是否已经断开
     *
     * @param context 上下文
     * @return 当前网络是否已经链接
     */
    public static boolean isConnectedByState(Context context) {
        return getCurrentNetworkState(context) ==
                NetworkInfo.State.CONNECTED;
    }

    /**
     * 获取当前网络的状态
     *
     * @param context 上下文
     * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
     */
    public static NetworkInfo.State getCurrentNetworkState(Context context) {
        NetworkInfo networkInfo
                = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getState() : null;
    }

    /**
     * 跳转到打开网络的界面
     */
    public static void goConnection(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setFlags(0x10200000);
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings"));
        activity.startActivity(intent);
    }
}
