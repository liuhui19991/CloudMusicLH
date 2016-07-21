package com.carporange.cloudmusic.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.MyAdapter;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.S;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/7/20.
 */
public class BeautfulActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    XRecyclerView mXRecyclerView;
    private ArrayList<String> mListData;
    private MyAdapter mAdapter;
    private int refreshTime, times;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautful);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//默认垂直排列,可以不用写
        mXRecyclerView.setLayoutManager(linearLayoutManager);

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
//        mXRecyclerView.setPullRefreshEnabled(false);//不允许下拉刷新
//        mXRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);//更换刷新箭头
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
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 15; i++) {
                                mListData.add("item" + (i + mListData.size()));
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
                                mListData.add("lastData" + (i + mListData.size()));
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
//        mXRecyclerView.addHeaderView(header1);
//        mXRecyclerView.addHeaderView(header);
        mAdapter = new MyAdapter(mListData);
        mXRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemCclick(View v, int position) {
                S.show(BeautfulActivity.this, "点击" + position);
                L.e("我是");
            }
        });

    }

    /**
     * ToolBar中的返回按钮对应事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
