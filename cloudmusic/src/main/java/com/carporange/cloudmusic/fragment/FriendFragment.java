package com.carporange.cloudmusic.fragment;

import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class FriendFragment extends BaseFragment {

    @BindView(R.id.tv_text)
    TextView mTextView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friend;
    }

}
