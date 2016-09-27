package com.carporange.cloudmusic.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.event.ProgressVideoPlayer;
import com.carporange.cloudmusic.ui.activity.BaiduMapActivity;
import com.carporange.cloudmusic.ui.activity.BeautfulActivity;
import com.carporange.cloudmusic.ui.activity.MyRecyclerViewLoadActivity;
import com.carporange.cloudmusic.ui.activity.RecyclerSwipeActivity;
import com.carporange.cloudmusic.ui.activity.RefreshLoadMoreActivity;
import com.carporange.cloudmusic.ui.activity.VideoPlayerActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.T;
import com.carporange.cloudmusic.widget.CircleTextProgressbar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by liuhui on 2016/6/27.
 */
public class SongMenuFragment extends BaseFragment {
    @BindView(R.id.circle_progress)
    CircleTextProgressbar mCircleProgress;
    @BindView(R.id.circle_progress_opposite)
    CircleTextProgressbar mCircleProgressOpposite;

    public SongMenuFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_song_menu;
    }

    @Override
    protected void initViews() {
        // 和系统普通进度条一样，0-100。顺时进度条
        mCircleProgress.setProgressType(CircleTextProgressbar.ProgressType.COUNT);
        // 改变进度条。
        mCircleProgress.setProgressLineWidth(26);// 进度条宽度。

        // 设置倒计时时间毫秒，默认3000毫秒。
        mCircleProgress.setTimeMillis(2000);

        // 改变进度条颜色。
        mCircleProgress.setProgressColor(Color.parseColor("#AA66C6C6"));

        // 改变外部边框颜色。
        mCircleProgress.setOutLineColor(Color.TRANSPARENT);

        // 改变圆心颜色。
        mCircleProgress.setInCircleColor(Color.TRANSPARENT);
        mCircleProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCircleProgress.setInCircleColor(Color.parseColor("#897654"));//点击时候改变进度圆内圆的颜色
            }
        });
    }


    @Override
    protected void onVisible() {
//        S.show(getActivity(), CarpApplication.getInstance().getResources().getString(R.string.title));//此处的snackbar会出现在华为手机的导航栏上因为获取的view的缘故
        // 如果需要自动倒计时，就会自动走进度。
//        mCircleProgress.start();//只能开启一次
        mCircleProgress.reStart();
        mCircleProgressOpposite.reStart();
        mCircleProgress.setCountdownProgressListener(1, onCountdownProgressListener);
        mCircleProgressOpposite.setCountdownProgressListener(2, onCountdownProgressListener);
    }

    CircleTextProgressbar.OnCountdownProgressListener onCountdownProgressListener = new CircleTextProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                mCircleProgress.setText(progress + "%");
            } else if (what == 2) {
                mCircleProgressOpposite.setText(progress + "%");
            }
        }
    };

    @OnClick(R.id.go)
    public void click() {
        startActivity(new Intent(getContext(), BeautfulActivity.class));
    }

    @OnClick(R.id.tomap)
    void toMap() {
        JCVideoPlayerStandard.startFullscreen(getActivity(), ProgressVideoPlayer.class,
                "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", "摇滚");
    }

    @OnClick(R.id.tomap1)
    void toVideo() {
//        http://resource.gbxx123.com/minivideo/mp4/gq/2016/7/22/1469176714153/1469176714153.mp4
//               JCVideoPlayerStandard.startFullscreen(getContext(), JCVideoPlayerStandard.class,
//                        "http://resource.gbxx123.com/minivideo/mp4/gq/2016/7/22/1469176714153/1469176714153.mp4", "嫂子辛苦了");
//        JCFullScreenActivity.startActivity(getActivity(),
//                "http://resource.gbxx123.com/minivideo/mp4/gq/2016/7/22/1469176714153/1469176714153.mp4",
//                JCVideoPlayerStandard.class, "来个慢动作");
        startActivity(new Intent(getActivity(), VideoPlayerActivity.class));
    }

    @OnClick(R.id.swiperecycler)
    void goSwipeRecyclerView() {
        startActivity(new Intent(getActivity(), RefreshLoadMoreActivity.class));
    }

    @OnClick(R.id.recyclerload)
    void goMyRecyclerViewLoad() {
        startActivity(new Intent(mContext, MyRecyclerViewLoadActivity.class));
    }

    @OnClick(R.id.recycleswipe)
    void goRecyclerSwipe() {
        startActivity(new Intent(mContext, RecyclerSwipeActivity.class));
    }

    @OnClick(R.id.baidumap)
    void goBaiduMap() {
        /*if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//没有权限
                ActivityCompat.requestPermissions(mContext,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        BAIDU_READ_PHONE_STATE);
            }
        } else {
            startActivity(new Intent(mContext, BaiduMapActivity.class));
        }*/
        AndPermission.with(this)
                .requestCode(99)
                .permission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 这个Fragment所在的Activity的onRequestPermissionsResult()如果重写了，不能删除super.onRes...
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionYes(99)
    private void getLocationYes() {
        L.e("获取定位权限");
        startActivity(new Intent(mContext, BaiduMapActivity.class));
    }

    @PermissionNo(99)
    private void getLocationNo() {
        L.e("没有获取到定位权限");
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

}
