package com.carporange.cloudmusic.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.activity.DetailActivity;
import com.carporange.cloudmusic.ui.activity.UseInFragmentActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        mList.add("详情页");//在这里添加要点击的条目
        mList.add("Fragment带侧边栏");
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        ListAdapter listAdapter = new ListAdapter(mList);
        mRecyclerView.setAdapter(listAdapter);
        listAdapter.setOnClicklistener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext,DetailActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext,UseInFragmentActivity.class));
                        break;
                }
                ToastUtil.show(mContext, "dianji " + position);
            }
        });

    }

    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
        List<String> mList;
        ItemClickListener mItemClickListener;

        public ListAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int vype) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ListViewHolder(view);
        }

        public void setOnClicklistener(ItemClickListener itemClickListener) {
            mItemClickListener = itemClickListener;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, final int position) {
            holder.mTextView.setText(mList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ListViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_title)
            TextView mTextView;

            public ListViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public interface ItemClickListener {
        void onClick(int position);
    }
}
