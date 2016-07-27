package com.carporange.cloudmusic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.carporange.cloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by liuhui on 2016/7/27.
 */
public class VideoPlayer extends AppCompatActivity {
    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mVideoPlayer;
    private String url = "http://resource.gbxx123.com/minivideo/mp4/gq/2016/7/22/1469176714153/1469176714153.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mVideoPlayer.setUp(url,"两面人");
    }
}
