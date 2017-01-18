package com.carporange.cloudmusic.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.DynamicAdapter;
import com.carporange.cloudmusic.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuhui on 2017/1/16.
 */
public class DetailActivity extends BaseActivity{
    @BindView(R.id.dynamic_rc)
    RecyclerView mRecyclerView;
    List<String> mList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void initViews() {
        String[] stringArray = getResources().getStringArray(R.array.detail);
        for (int i = 0; i < stringArray.length; i++) mList.add(stringArray[i]);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DynamicAdapter dynamicAdapter = new DynamicAdapter(R.layout.item, mList);
        View view = getLayoutInflater().inflate(R.layout.item, null);
        dynamicAdapter.addHeaderView(view);//此处添加头布局上下滚动的跑马灯
        mRecyclerView.setAdapter(dynamicAdapter);

    }

    @Override
    public void initActionBar() {
    }
}
