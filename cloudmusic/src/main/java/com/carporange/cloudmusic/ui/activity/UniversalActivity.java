package com.carporange.cloudmusic.ui.activity;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.DividerItemDecoration;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.AnimatorUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

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
    private LinearLayoutManager mLinearLayoutManager;
    private CommonAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<>();
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int refreshTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myrecyclerviewload;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        toolbar.setTitle("universal");
    }

    @Override
    public void initViews() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//设置分割线
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
        mAdapter = new CommonAdapter<String>(this, R.layout.item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
            }
        };
        initHeaderAndFooter();

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.loadmore_footer);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            mDatas.add("Add:" + i);
                        }
                        mLoadMoreWrapper.notifyDataSetChanged();

                    }
                }, 3000);
            }
        });

        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "pos = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(mContext, "=" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addHeaderView(t2);
    }

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
                    mDatas.clear();
                    for (int i = 0; i < 15; i++) {
                        mDatas.add("item" + i + "after " + refreshTime + " times of refresh");
                    }

                    mLoadMoreWrapper.notifyDataSetChanged();//要用mLoadMoreWrapper通知更新
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
