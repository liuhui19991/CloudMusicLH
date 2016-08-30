package com.carporange.cloudmusic.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.DividerItemDecoration;
import com.carporange.cloudmusic.adapter.MyAdapter;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.AnimatorUtil;
import com.carporange.cloudmusic.util.T;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/7/20.
 */
public class BeautfulActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton FAB;
    private ArrayList<String> mListData;
    private MyAdapter mAdapter;
    private int refreshTime, times;
    private View mEmptyView;
    String resourceUrl = "http://resource.gbxx123.com/mindmap/2016/5/30/1464610110060/1464610110060.json";
    String url = "http://resource.gbxx123.com/mindmap/2016/7/1/1467336284092/1467336284092.json";
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_beautful;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        toolbar.setTitle("RecyclerView");
    }

    @Override
    public void initViews() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//默认垂直排列,可以不用写
        mXRecyclerView.setLayoutManager(linearLayoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
//        mXRecyclerView.setItemAnimator(new );//TODO item动画
//        mXRecyclerView.setPullRefreshEnabled(false);//不允许下拉刷新,这句话会使请求数据为空时候不显示空的View
//        mXRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);//更换刷新箭头
        mEmptyView = findViewById(R.id.text_empty);
        mXRecyclerView.setEmptyView(mEmptyView);//当没有数据时候显示eMptyView;
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListData.clear();
                        for (int i = 0; i < 15; i++) {
                            mListData.add("item" + i + "after " + refreshTime + " times of refresh");
                        }
                        mXRecyclerView.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 800);
            }

            @Override
            public void onLoadMore() {
                final int size = mListData.size();
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 15; i++) {
                                mListData.add("item" + (i + size));
                            }
                            mXRecyclerView.loadMoreComplete();
                            mAdapter.notifyDataSetChanged();
                        }
                    }, 800);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 8; i++) {
                                mListData.add("lastData" + (i + size));
                            }
                            mXRecyclerView.setIsnomore(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    }, 800);
                }
                times++;
            }
        });
        mListData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mListData.add("RecyclerView" + i);
        }
//        View header =   LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
//        View header1 =   LayoutInflater.from(this).inflate(R.layout.recyclerview_header1, (ViewGroup)findViewById(android.R.id.content),false);
//        mXRecyclerView.addHeaderView(header1);  //先添加的在最上边
//        mXRecyclerView.addHeaderView(header);
        mAdapter = new MyAdapter(mListData);
        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//设置分割线
        mAdapter.setOnItemClickListener(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemCclick(View v, int position) {
                Intent intent = new Intent(mContext, KnowledgeActivity.class);
                if (position % 2 == 0) {
                    intent.putExtra("map", resourceUrl);
                } else {
                    intent.putExtra("map", url);
                }
                startActivity(intent);
            }
        });
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(mContext, "回到顶部");
                linearLayoutManager.scrollToPosition(0);
                hideFAB();
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
