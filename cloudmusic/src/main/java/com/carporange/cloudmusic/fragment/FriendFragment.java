package com.carporange.cloudmusic.fragment;

import android.app.Dialog;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/6/27.
 */
public class FriendFragment extends BaseFragment {
    Dialog mWaitDialog;
    private boolean canCancel = true;
    @BindView(R.id.tv_text)
    TextView mTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_friend, container, false);
            ButterKnife.bind(this, mContentView);
            initWaitDialog();
        }
        return mContentView;
    }

    private void initWaitDialog() {
        mWaitDialog = new Dialog(getActivity(), R.style.TRANSDIALOG);
        mWaitDialog.setContentView(R.layout.trans_dialog);
        mWaitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        mWaitDialog.setCancelable(canCancel);
        if (mWaitDialog != null) {
            mWaitDialog.show();
        }
        mTextView.setText("动态添加");
    }
}
