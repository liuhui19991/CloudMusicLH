package com.carporange.cloudmusic.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.carporange.cloudmusic.R;

import cn.world.liuhui.utils.ToastUtil;

/**
 * Created by liuhui on 2017/4/24.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context mContext;

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.poplayout, null);
        setContentView(view);//这个应该先设置要不然下面设置宽度将不起作用
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
//        <item name="android:windowAnimationStyle">@style/mypopwindow_anim_style</item>
        window.setWindowAnimations(R.style.mypopwindow_anim_style); // 添加动画,此处也可以在style文件中设置

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {//dialog获取的params为null,应该是将布局文件宽高均按照wrap_content处理
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            params.width = window.getWindowManager().getDefaultDisplay().getWidth();//可以在此动态设置dialog的宽度
        }
        view.setLayoutParams(params);
        view.findViewById(R.id.third).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ToastUtil.show(mContext, "dialog被点击了");
        dismiss();
    }
}
