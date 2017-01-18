package com.carporange.cloudmusic.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.DynamicAdapter;
import com.carporange.cloudmusic.ui.activity.DetailActivity;
import com.carporange.cloudmusic.ui.activity.UseInFragmentActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.world.liuhui.utils.ToastUtil;

/**
 * Created by liuhui on 2016/6/27.
 */
public class DynamicFragment extends BaseFragment {


    @BindView(R.id.dynamic_rc)
    RecyclerView mRecyclerView;
    List<String> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initViews() {
        String[] stringArray = getResources().getStringArray(R.array.detail);
        for (int i = 0; i < stringArray.length; i++) mList.add(stringArray[i]);

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        DynamicAdapter dynamicAdapter = new DynamicAdapter(R.layout.item, mList);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.marq_head, null);
        ViewFlipper vff = (ViewFlipper) view.findViewById(R.id.vf);
        vff.addView(View.inflate(mContext, R.layout.view_advertisement01, null));
        vff.addView(View.inflate(mContext, R.layout.view_advertisement02, null));
        vff.addView(View.inflate(mContext, R.layout.view_advertisement03, null));//如果没有这三行代码,viewflipper这个控件将不占位
        dynamicAdapter.addHeaderView(view);//此处添加头布局上下滚动的跑马灯
        mRecyclerView.setAdapter(dynamicAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(mContext, DetailActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, UseInFragmentActivity.class));
                        break;
                    case 2:
//                        startActivity(new Intent(mContext, .class));
                        break;
                }
                ToastUtil.show(mContext, "dianji " + i);
            }
        });
    }
}
