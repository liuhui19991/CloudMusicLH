package com.carporange.cloudmusic.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.carporange.cloudmusic.CarpApplication;
import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.util.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.world.liuhui.utils.ImageLoaderUtil;
import cn.world.liuhui.OnDoubleClickListener;
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
//        initWindow();
        ImageLoaderUtil.displayRound(iv1, "https://www.baidu.com/img/bdlogo.png");
        ImageLoaderUtil.displayCircle(iv2, "https://www.baidu.com/img/bdlogo.png");
        ImageLoaderUtil.display(iv3, R.mipmap.circlepicture);
        iv2.setOnClickListener(new OnDoubleClickListener() {
            @Override
            protected void onDoubleClick(View v) {
                //下面显示的对话框字体为绿色
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
                builder.setTitle("您的密码!");
                builder.setMessage("帐号:" + "\n" + "密码:");
                builder.setNegativeButton("电话联系", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", null);
                builder.show();
            }
        });
    }

    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //5.0以上可以直接设置 statusbar颜色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
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
