package com.carporange.cloudmusic.fragment;

import android.app.Dialog;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class FriendFragment extends BaseFragment {
    Dialog mWaitDialog;
    private boolean canCancel = true;
    @BindView(R.id.tv_text)
    TextView mTextView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    protected void onVisible() {
        mTextView.setText("动态添加");
        initWaitDialog();
    }

    private void initWaitDialog() {//展示我自定义的等待对话框
        if (mWaitDialog == null) {
            mWaitDialog = new Dialog(getActivity(), R.style.TRANSDIALOG);
        }
        mWaitDialog.setContentView(R.layout.trans_dialog);
        mWaitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        mWaitDialog.setCancelable(canCancel);
        if (mWaitDialog != null) {
            mWaitDialog.show();
        }
    }
}
