package com.carporange.cloudmusic.adapter;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.domain.DataServer;
import com.carporange.cloudmusic.domain.Status;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by liuhui on 2016/10/18.
 */

public class QuickAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    public QuickAdapter() {
        super(R.layout.tweet, DataServer.getSampleData(100));
    }

    public QuickAdapter(int dataSize) {
        super(R.layout.tweet, DataServer.getSampleData(dataSize));
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnClickListener(R.id.tweetAvatar)
                .addOnClickListener(R.id.tweetName)
                .linkify(R.id.tweetText);
    }
}
