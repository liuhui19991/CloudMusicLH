package com.carporange.cloudmusic.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/9/13.
 */
public class UniversalActivity extends BaseActivity {
    @BindView(R.id.recyclermore)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton FAB;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myrecyclerviewload;
    }

    @Override
    public void initViews() {

    }
}
