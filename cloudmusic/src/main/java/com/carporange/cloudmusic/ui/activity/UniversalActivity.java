package com.carporange.cloudmusic.ui.activity;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.QuickAdapter;
import com.carporange.cloudmusic.domain.CustomAnimation;
import com.carporange.cloudmusic.domain.DataServer;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.AnimatorUtil;
import com.carporange.cloudmusic.util.T;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/9/13.
 */
public class UniversalActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recyclermore)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton FAB;
    private LinearLayoutManager mLinearLayoutManager;
    private View notLoadingView;
    private QuickAdapter mQuickAdapter;
    private static final int TOTAL_COUNTER = 18;
    private static final int PAGE_SIZE = 6;
    private int delayMillis = 1000;
    private int mCurrentCounter = 0;
    private boolean isErr;

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
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//设置分割线

        initAdapter();
        initListener();
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter(PAGE_SIZE);
        mQuickAdapter.openLoadAnimation(new CustomAnimation());
        mQuickAdapter.isFirstOnly(false);
        mQuickAdapter.openLoadMore(PAGE_SIZE);
        //这句话可以设置自定义的加载进度条
        mQuickAdapter.setLoadingView(getLayoutInflater().inflate(R.layout.loadmore_footer, (ViewGroup) mRecyclerView.getParent(), false));
        mRecyclerView.setAdapter(mQuickAdapter);
        mCurrentCounter = mQuickAdapter.getData().size();
        mQuickAdapter.setOnLoadMoreListener(this);
    }

    private void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.tweetAvatar:
                        Toast.makeText(mContext, "The " + Integer.toString(position) + " tweetAvatar  is clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tweetName:
                        Toast.makeText(mContext, "The " + Integer.toString(position) + " tweetName  is clicked", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }


            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemLongClick(adapter, view, position);
                Toast.makeText(mContext, "The " + Integer.toString(position) + " Item is LongClick ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildLongClick(adapter, view, position);
                Toast.makeText(mContext, "The " + Integer.toString(position) + "  view itemchild " + "is LongClick " +
                        Integer.toString(position), Toast.LENGTH_SHORT).show();

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
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mQuickAdapter.setNewData(DataServer.getSampleData(8));
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    mQuickAdapter.loadComplete();
                    if (notLoadingView == null) {
                        notLoadingView = getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                    }
                    mQuickAdapter.addFooterView(notLoadingView);
                } else {
                    if (isErr) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mQuickAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                                mCurrentCounter = mQuickAdapter.getData().size();
                            }
                        }, delayMillis);
                    } else {
                        isErr = true;
                        mQuickAdapter.showLoadMoreFailedView();
                    }
                }
            }
        });
    }

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
