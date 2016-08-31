package com.carporange.cloudmusic.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/8/31.
 */
public class MyRecyclerViewLoadActivity extends BaseActivity {
    @BindView(R.id.recyclermore)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myrecyclerviewload;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        toolbar.setTitle("自己封装的上拉加载下拉刷新");
    }

    @Override
    public void initViews() {

    }
}
