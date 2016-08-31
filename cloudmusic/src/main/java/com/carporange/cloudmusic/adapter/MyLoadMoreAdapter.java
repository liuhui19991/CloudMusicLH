package com.carporange.cloudmusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carporange.cloudmusic.R;

import java.util.ArrayList;

/**
 * Created by liuhui on 2016/8/31.
 */
public class MyLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private ArrayList<String> datas;
    private ItemClickListener mListener;
    private boolean mShowFooter = true;

    public MyLoadMoreAdapter(ArrayList mlist) {
        datas = mlist;
    }

    public interface ItemClickListener {
        void onItemCclick(View v, int position);
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
            return new LoadMoreHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.loadmore_footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreHolder) {
            ((LoadMoreHolder) holder).textView.setText(datas.get(position));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemCclick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (datas == null) {
            return begin;
        }
        return datas.size() + begin;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
