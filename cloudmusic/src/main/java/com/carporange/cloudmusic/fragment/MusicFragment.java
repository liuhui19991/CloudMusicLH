package com.carporange.cloudmusic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.widget.WinLoading;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class MusicFragment extends BaseFragment {
    @BindView(R.id.waiting_dialog_windows)
    WinLoading mWinLoading;

    public MusicFragment() {
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }*/

    @Override
    protected void onVisible() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWinLoading.setVisibility(View.GONE);
            }
        }, 2000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music;
    }

}
