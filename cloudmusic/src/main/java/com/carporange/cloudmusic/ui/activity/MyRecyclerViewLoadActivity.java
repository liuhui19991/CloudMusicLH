package com.carporange.cloudmusic.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.DividerItemDecoration;
import com.carporange.cloudmusic.adapter.MyLoadMoreAdapter;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.AnimatorUtil;
import com.carporange.cloudmusic.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/8/31.
 */
public class MyRecyclerViewLoadActivity extends BaseActivity {
    @BindView(R.id.recyclermore)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton FAB;
    private int refreshTime, times;
    private List<String> mStrings;
    private MyLoadMoreAdapter mLoadAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLoadMore;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myrecyclerviewload;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        toolbar.setTitle("小标题");
    }

    @Override
    public void initViews() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mStrings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mStrings.add("my love" + i);
        }
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//设置分割线
        mLoadAdapter = new MyLoadMoreAdapter((ArrayList) mStrings);
        if (mStrings.size() < 20) {
            mLoadAdapter.isShowFooter(false);//如果数据不足20条隐藏脚布局
        }
        mRecyclerView.setAdapter(mLoadAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        /**
         * 点击事件
         */
        mLoadAdapter.setOnItemClickListener(new MyLoadMoreAdapter.ItemClickListener() {
            @Override
            public void onItemCclick(View v, int position) {
                Snackbar.make(mRecyclerView, "" + position, Snackbar.LENGTH_SHORT).show();
            }
        });

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(mContext, "回到顶部");
                mLinearLayoutManager.scrollToPosition(0);
                hideFAB();
            }
        });
    }

    /**
     * 滚动监听,加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mLoadAdapter.getItemCount()
                    && mLoadAdapter.isShowFooter()) {//如果没有数据时候再次滑动到底部时候要提示没有数据了,就把此行删掉
                if (mStrings.size() < 20) {
                    return;
                }
                //加载更多
                if (!isLoadMore) {
                    isLoadMore = true;
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMore();
                        }
                    }, 500);
                }
            }
        }

        private void loadMore() {
            final int size = mStrings.size();
            if (times < 2) {
                for (int i = 0; i < 15; i++) {
                    mStrings.add("item" + (i + size));
                }
                mLoadAdapter.notifyDataSetChanged();
            } else if (times == 2) {
                for (int i = 0; i < 8; i++) {
                    mStrings.add("lastData" + (i + size));
                }
                mLoadAdapter.notifyDataSetChanged();
            } else {
                mLoadAdapter.isShowFooter(false);//隐藏脚布局
                mLoadAdapter.notifyDataSetChanged();
                Snackbar.make(mRecyclerView, "没有更多数据了", Snackbar.LENGTH_SHORT).show();
            }
            times++;
            isLoadMore = false;
        }
    };


    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshTime++;
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mStrings.clear();
                    for (int i = 0; i < 15; i++) {
                        mStrings.add("item" + i + "after " + refreshTime + " times of refresh");
                    }
                    if (mStrings.size() < 20) {
                        mLoadAdapter.isShowFooter(false);//隐藏脚布局
                    }
                    mLoadAdapter.notifyDataSetChanged();
                }
            }, 2000);
        }
    };

    private void hideFAB() {
        FAB.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorUtil.scaleHide(FAB, new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        FAB.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                    }
                });
            }
        }, 500);
    }
}
