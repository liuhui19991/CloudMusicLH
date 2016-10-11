package com.carporange.cloudmusic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.carporange.cloudmusic.CarpApplication;
import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.util.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.world.liuhui.GlideCircleTransfromUtil;
import cn.world.liuhui.GlideRoundTransformUtil;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by liuhui on 2016/7/27.
 */
public class VideoPlayerActivity extends AppCompatActivity {
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        ButterKnife.bind(this);
        Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransformUtil(this)).into(iv1);
        Glide.with(this).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideCircleTransfromUtil(this)).into(iv2);
        Glide.with(this).load(R.mipmap.circlepicture).into(iv3);
    }

    @OnClick(R.id.videoplayer)
    public void initView() {
        ++CarpApplication.getInstance().mCount;
        L.e(CarpApplication.getInstance().mCount + "");
        JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class,
                "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", "慢动作");
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
