package com.carporange.cloudmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    public MusicService() {
    }

    /* final MediaPlayer mediaPlayer = new MediaPlayer();
     LoadingDialogUtil.showLoading(mContext);
     try {
         mediaPlayer.setDataSource(mContext, Uri.parse("http://65res.gbxxzyzx.com/audio/songs/2016/7/14/20160714070713096.mp3"));
         mediaPlayer.prepare();
     } catch (IOException e) {
         e.printStackTrace();
     }
     mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
         @Override
         public void onPrepared(MediaPlayer mp) {
             mediaPlayer.start();
             LoadingDialogUtil.hideDialog();
         }
     });*/
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private MediaPlayer mp;

    @Override
    public void onStart(Intent intent, int startId) {
        // 开始播放音乐
//        mp.start();
        // 音乐播放完毕的事件处理
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                // 循环播放
                try {
                    mp.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });
        // 播放音乐时发生错误的事件处理
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 释放资源
                try {
                    mp.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {

        // 初始化音乐资源
        // 创建MediaPlayer对象
        mp = new MediaPlayer();
        // 将音乐保存在res/raw/xingshu.mp3,R.java中自动生成{public static final int xingshu=0x7f040000;}
//            mp = MediaPlayer.create(this, Uri.parse("http://65res.gbxxzyzx.com/audio/songs/2016/7/14/20160714070713096.mp3"));
        // 在MediaPlayer取得播放资源与stop()之后要准备PlayBack的状态前一定要使用MediaPlayer.prepeare()
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mp.setDataSource(MusicService.this, Uri.parse("http://he.yinyuetai.com/uploads/videos/common/D84601573906DEE126933CBAF8272FA4.flv?sc=a8ac8ce462888aec"));
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // 服务停止时停止播放音乐并释放资源
        mp.stop();
        mp.release();
        super.onDestroy();
    }
}
