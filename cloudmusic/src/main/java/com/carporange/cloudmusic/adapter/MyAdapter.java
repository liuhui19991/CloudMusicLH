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
import com.carporange.cloudmusic.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/7/21.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> datas;
    ItemClickListener mListener;
    List<Integer> list = new ArrayList<>();
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
        if (list == null) {
            holder.checkBox.setChecked(false);
        } else {
            holder.checkBox.setChecked(list.contains(position) ? true : false);
        }
        holder.textView.setText(datas.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!list.contains(holder.checkBox.getTag())) {
                        list.add(position);
                        L.e("添加"+position+"--list--"+list);
                    }
                } else {
                    if (list.contains(holder.checkBox.getTag())){
                        list.remove(position);
                        L.e("移除");
                    }
                }
            }
        });
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
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.cb);
            textView = (TextView) view.findViewById(R.id.text);
        }
    }
}
