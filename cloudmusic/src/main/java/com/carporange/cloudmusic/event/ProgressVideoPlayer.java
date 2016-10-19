package com.carporange.cloudmusic.event;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.SpUtil;

import cn.world.liuhui.utils.ToastUtil;
import fm.jiecao.jcvideoplayer_lib.JCBuriedPoint;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by liuhui on 2016/9/23.
 */
public class ProgressVideoPlayer extends JCVideoPlayerStandard {
    private String mPosition = "position";
    private int position;

    public ProgressVideoPlayer(Context context) {
        super(context);
    }

    public ProgressVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getCurrentPositionWhenPlaying() {
        position = super.getCurrentPositionWhenPlaying();
        L.e(String.valueOf(position));
        return position;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        fullscreenButton.setVisibility(GONE);//隐藏全屏切换按钮
        fullscreenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(getContext(), "切换清晰度");
               showPopupWindow(fullscreenButton);
            }
        });
        super.onSurfaceTextureAvailable(surface, width, height);
    }

    OnClickListener onclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_size_big:
                    backPress();
                    JCVideoPlayerStandard.startFullscreen(getContext(), ProgressVideoPlayer.class,
                            "http://resource.gbxx123.com/minivideo/mp4/gq/2016/7/22/1469176714153/1469176714153.mp4", "高清视频");
                    break;
                case R.id.text_size_small:
                    backPress();
                    JCVideoPlayerStandard.startFullscreen(getContext(), ProgressVideoPlayer.class,
                            "http://resource.gbxx123.com/minivideo/mp4/gq/2016/7/22/1469176714153/1469176714153.mp4", "低清视频");
                    break;
            }
        }
    };

    /**
     * 让PopupWindow显示在控件上方
     *
     * @param v 要显示的控件
     */
    private void showPopupWindow(View v) {
        L.e("hello, %s", "world");//, %s这样才能换行
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popwin_view, null);
        TextView tv_big = (TextView) view.findViewById(R.id.text_size_big);
        tv_big.setText("高清");
        tv_big.setOnClickListener(onclick);
        TextView tv_small = (TextView) view.findViewById(R.id.text_size_small);
        tv_small.setText("低清");
        tv_small.setOnClickListener(onclick);
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

    @Override
    public void onPrepared() {
        super.onPrepared();
        String oldPosition = SpUtil.getString(mPosition, 0 + "");
        L.e("页面可见" + oldPosition);
        onEvent(JCBuriedPoint.ON_TOUCH_SCREEN_SEEK_POSITION);
        JCMediaManager.instance().mediaPlayer.seekTo(Integer.parseInt(oldPosition));
//        int duration = getDuration();
//        int progress = Integer.parseInt(oldPosition) * 100 / (duration == 0 ? 1 : duration);
//        progressBar.setProgress(progress);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        SpUtil.put(mPosition, position + "");
        L.e("页面销毁" + position);
        return super.onSurfaceTextureDestroyed(surface);
    }

}
