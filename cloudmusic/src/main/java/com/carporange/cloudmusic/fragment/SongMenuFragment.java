package com.carporange.cloudmusic.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.activity.BeautfulActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.widget.CircleTextProgressbar;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_song_menu;
    }

    @OnClick(R.id.go)
    public void click() {
        startActivity(new Intent(getContext(),BeautfulActivity.class));
    }
}
