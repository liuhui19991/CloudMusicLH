package cn.world.liuhui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.world.liuhui.R;

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

    /**
     * 显示有image的toast
     *
     * @param tvStr  要展示的文字
     * @param imageResource  为-1时隐藏图片,0显示默认图片
     * @return
     */
    public static Toast showToastWithImg(Context context,String tvStr, int imageResource) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else if (imageResource == -1){
            iv.setVisibility(View.GONE);
        }
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }
}