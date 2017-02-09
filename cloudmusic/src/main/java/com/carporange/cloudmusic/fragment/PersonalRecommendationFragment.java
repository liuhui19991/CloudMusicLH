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
import com.carporange.cloudmusic.ui.activity.MusicActivity;
import com.carporange.cloudmusic.ui.activity.PhonePersion;
import com.carporange.cloudmusic.ui.activity.RefreshAnimationActivity;
import com.carporange.cloudmusic.ui.activity.UniversalActivity;
import com.carporange.cloudmusic.ui.activity.WebAndListViewActivity;
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
    private final String APK_URL = "http://surveyapp.fy.chaoxing.com/app/LauncherDemo5.apk";
    private Button top, bottom;
    private PopupWindow mPopupWindow;

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

    @OnClick({R.id.refresh, R.id.top, R.id.bottom, R.id.universaladapter, R.id.phone_persion, R.id.update, R.id.music, R.id.wvandlv})
    void click(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                startActivity(new Intent(mContext, RefreshAnimationActivity.class));
                break;
            case R.id.top:
                showPopupWindow(top);
                break;
            case R.id.bottom:
                showPopupWindow(bottom);
                break;
            case R.id.universaladapter:
                startActivity(new Intent(mContext, UniversalActivity.class));
                break;
            case R.id.phone_persion:
                startActivity(new Intent(mContext, PhonePersion.class));
                break;
            case R.id.wvandlv:
                startActivity(new Intent(mContext, WebAndListViewActivity.class));
                break;
            case R.id.update:
                DialogUtil.showAlert(mContext, "更新软件", "这是一个新的版本", "立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(mContext, DownloadService.class);
                        intent.putExtra("url", APK_URL);
                        mContext.startService(intent);
                    }
                }, "下次再说", null);
                break;
            case R.id.music:
//                http://65res.gbxxzyzx.com/audio/songs/2016/7/14/20160714070713096.mp3
                startActivity(new Intent(mContext, MusicActivity.class));
                break;
        }
    }

    /**
     * @param v 显示在这个控件的相对位置
     */
    private void showPopupWindow(View v) {
        L.e("hello, %s", "world");//, %s这样才能打印出分开的逗号
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popwin_view, null);
        view.findViewById(R.id.text_size_big).setOnClickListener(onclick);
        view.findViewById(R.id.text_size_small).setOnClickListener(onclick);
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//要想下面测量出高度必须添加这句话
        int high = view.getMeasuredHeight() + v.getHeight();
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);//默认动画为缩放动画
        mPopupWindow.showAsDropDown(v, 0, -high);// 保证尺寸是根据屏幕像素密度来的,显示的控件上方
//        mPopupWindow.showAsDropDown(v);//显示在控件下方
        // 使其聚集
        mPopupWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        // 刷新状态
        mPopupWindow.update();
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.dismiss();
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
