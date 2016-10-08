package com.carporange.cloudmusic.event;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.util.AttributeSet;

import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.SpUtil;

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
    protected int getCurrentPositionWhenPlaying() {
        position = super.getCurrentPositionWhenPlaying();
        L.e(String.valueOf(position));
        return position;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        fullscreenButton.setVisibility(GONE);//隐藏全屏切换按钮
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        String oldPosition = SpUtil.getString(mPosition, 0 + "");
        L.e("页面可见" + oldPosition);
        onEvent(JCBuriedPoint.ON_TOUCH_SCREEN_SEEK_POSITION);
        JCMediaManager.instance().mediaPlayer.seekTo(Integer.parseInt(oldPosition));
        int duration = getDuration();
        int progress = Integer.parseInt(oldPosition) * 100 / (duration == 0 ? 1 : duration);
        progressBar.setProgress(progress);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        SpUtil.put(mPosition, position + "");
        L.e("页面销毁" + position);
        return super.onSurfaceTextureDestroyed(surface);
    }

}
