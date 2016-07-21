package com.carporange.cloudmusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carporange.cloudmusic.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/7/21.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> datas;
    ItemClickListener mListener;

    public interface ItemClickListener {
        void onItemCclick(View v, int position);
    }

    public MyAdapter(ArrayList mlist) {
        datas = mlist;
    }

    /**
     * 创建新View，被LayoutManager所调用
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beautful, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    /**
     * 将数据与界面进行绑定的操作
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemCclick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
    }

    /**
     * @return 获取数据的数量
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }


    }
}
