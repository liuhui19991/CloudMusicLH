package com.carporange.cloudmusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.carporange.cloudmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhui on 2016/7/21.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> datas;
    private ItemClickListener mListener;
    /**
     * 记录checkbox是否选中的list
     */
    List<Integer> list = new ArrayList<>();
    /**
     * 记录小红点的集合
     */
    List<Integer> redList = new ArrayList<>();

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.checkBox.setTag(new Integer(position));//设置tag 否则划回来时选中消失
        holder.imageView.setTag(new Integer(position));
        if (list != null) {
            holder.checkBox.setChecked(list.contains(position) ? true : false);
        } else {
            holder.checkBox.setChecked(false);
        }
        if (redList != null) {
            //此处必须使用三元用算符,要不然还会出现复用的问题,只有当集合包含的时候才设置小红点消失
            holder.imageView.setVisibility(redList.contains(position) ? View.GONE : View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.VISIBLE);
        }
        holder.textView.setText(datas.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!list.contains(holder.checkBox.getTag())) {
                        list.add(new Integer(position));
                    }
                } else {
                    if (list.contains(holder.checkBox.getTag())) {
                        list.remove(new Integer(position));
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    clickCheckbox();
                    clickRed();
                    mListener.onItemCclick(holder.itemView, holder.getLayoutPosition());
                }
            }

            /**
             * 条目点击时候checkbox的选中状态
             */
            private void clickCheckbox() {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setChecked(true);
                }
            }

            /**
             * 条目点击时候小红点的显示隐藏
             */
            private void clickRed() {
                if (holder.imageView.getVisibility() == View.VISIBLE) {
                    holder.imageView.setVisibility(View.GONE);
                    if (!redList.contains(holder.imageView.getTag())) {
                        redList.add(new Integer(position));
                    }
                } else {
                    holder.imageView.setVisibility(View.VISIBLE);
                    if (redList.contains(holder.imageView.getTag())) {
                        redList.remove(new Integer(position));
                    }
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
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.cb);
            textView = (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
}
