package com.carporange.cloudmusic.event;

import android.content.Context;
import android.graphics.SurfaceTexture;
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
        setTextAndProgress(5000);
    }

    @Override
    protected int getCurrentPositionWhenPlaying() {
        position = super.getCurrentPositionWhenPlaying();
        L.e(String.valueOf(position));
        return position;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        String oldPosition = SpUtil.getString(mPosition, 0 + "");
//        setTextAndProgress(Integer.parseInt(oldPosition));
//        JCMediaManager.instance().mediaPlayer.seekTo(Integer.parseInt(oldPosition));
//        bottomProgressBar.setProgress(Integer.parseInt(oldPosition));

        L.e("页面可见" + oldPosition);
        onEvent(JCBuriedPoint.ON_TOUCH_SCREEN_SEEK_POSITION);
        JCMediaManager.instance().mediaPlayer.seekTo(Integer.parseInt(oldPosition));
        int duration = getDuration();
        int progress = Integer.parseInt(oldPosition) * 100 / (duration == 0 ? 1 : duration);
        progressBar.setProgress(progress);
        super.onSurfaceTextureAvailable(surface, width, height);
        JCMediaManager.instance().mediaPlayer.seekTo(Integer.parseInt(oldPosition));
    }

    @Override
    protected void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime, int totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        L.e("页面改变" + getCurrentPositionWhenPlaying());
        super.onSurfaceTextureSizeChanged(surface, width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        SpUtil.put(mPosition, position + "");
        L.e("页面销毁" + position);
        return super.onSurfaceTextureDestroyed(surface);
    }

}
