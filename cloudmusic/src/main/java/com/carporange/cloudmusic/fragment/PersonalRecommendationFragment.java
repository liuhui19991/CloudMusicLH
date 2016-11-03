package com.carporange.cloudmusic.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.service.DownloadService;
import com.carporange.cloudmusic.ui.activity.PhonePersion;
import com.carporange.cloudmusic.ui.activity.UniversalActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.T;

import butterknife.OnClick;
import cn.world.liuhui.utils.DialogUtil;

/**
 * Created by liuhui on 2016/6/27.
 */
public class PersonalRecommendationFragment extends BaseFragment {
    private Dialog mWaitDialog;
    private boolean canCancel = true;
    private Button top, bottom;

    public PersonalRecommendationFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_recommendation;
    }

    private void initWaitDialog() {//展示我自定义的等待对话框
        if (mWaitDialog == null) {
            mWaitDialog = new Dialog(mContext, R.style.TRANSDIALOG);
        }
        mWaitDialog.setContentView(R.layout.trans_dialog);
        mWaitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        mWaitDialog.setCancelable(canCancel);
        if (mWaitDialog != null) {
            mWaitDialog.show();
        }
    }

    @Override
    protected void initViews() {
        top = findView(R.id.top);
        bottom = findView(R.id.bottom);
//        initWaitDialog();  //现在这个方法执行的时候会出现创建Dialog时候空指针
    }


    /**
     * 显示dialog的按钮点击事件
     */
    @OnClick(R.id.btn_bottom_dialog_control)
    void dialog() {

    }

    @OnClick(R.id.top)
    void top() {
        showPopupWindow(top);
    }

    @OnClick(R.id.bottom)
    void bottom() {
        showPopupWindow(bottom);
    }

    @OnClick(R.id.universaladapter)
    void universalAdapter() {
        startActivity(new Intent(mContext, UniversalActivity.class));
    }

    @OnClick(R.id.phone_persion)
    void phonePersion() {
        startActivity(new Intent(mContext, PhonePersion.class));
    }

    @OnClick(R.id.update)
    void updateApp() {
        DialogUtil.showAlert(mContext, "更新软件", "这是一个新的版本", "立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(mContext,DownloadService.class);
                intent.putExtra("url", "http://surveyapp.fy.chaoxing.com/app/LauncherDemo5.apk");
                mContext.startService(intent);
            }
        }, "下次再说", null);
    }
    /**
     * 让PopupWindow显示在控件上方
     *
     * @param v 要显示的控件
     */
    private void showPopupWindow(View v) {
        L.e("hello, %s", "world");//, %s这样才能换行
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popwin_view, null);
        view.findViewById(R.id.text_size_big).setOnClickListener(onclick);
        view.findViewById(R.id.text_size_small).setOnClickListener(onclick);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//要想下面测量出高度必须添加这句话
        int high = view.getMeasuredHeight() + v.getHeight();
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);//默认动画为缩放动画
        popupWindow.showAsDropDown(v,
                0,
                // 保证尺寸是根据屏幕像素密度来的
                -high);

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 刷新状态
        popupWindow.update();
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_size_big:
                    T.showShort(mContext, "大字体");
                    break;
                case R.id.text_size_small:
                    T.showShort(mContext, "小字体");
                    break;
                default:

                    break;
            }
        }
    };
}
