package com.carporange.cloudmusic.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.ContentAdapter;
import com.carporange.cloudmusic.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.world.liuhui.utils.DensityUtil;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by liuhui on 2016/12/5.
 */
public class RefreshAnimationActivity extends BaseActivity {
    @BindView(R.id.refresh_layout)
    PtrFrameLayout mPtrFrameLayout;
    private ContentAdapter mContentAdapter;
    private List<String> mDataList;
    /**
     * 刷新监听。
     */
    private PtrHandler mPtrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            // 这里延时2000ms，模拟网络请求。
            mPtrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestDataList();
                }
            }, 2000);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_animation;
    }

    @Override
    public void initViews() {
        mPtrFrameLayout.setPtrHandler(mPtrHandler);
        DensityUtil.initScreen(this);//必须初始化才可以获取正确的屏幕长度
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mContentAdapter = new ContentAdapter(this);
        recyclerView.setAdapter(mContentAdapter);

        // 由于PtrFrameLayout的自动刷新需要在onWindowFocusChanged(boolean)之后调用，所以这里延时250ms.
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        }, 250);
    }

    private void requestDataList() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mDataList.add("我是第" + i + "个");
        }
        mContentAdapter.notifyDataSetChanged(mDataList);

        mPtrFrameLayout.refreshComplete();
    }
}
