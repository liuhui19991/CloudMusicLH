package com.carporange.cloudmusic.adapter;

import com.carporange.cloudmusic.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by liuhui on 2017/1/17.
 */

public class DynamicAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public DynamicAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_title, s);
    }
}
