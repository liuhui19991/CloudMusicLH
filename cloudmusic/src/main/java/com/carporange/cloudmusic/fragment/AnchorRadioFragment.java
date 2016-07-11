package com.carporange.cloudmusic.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;

/**
 * Created by liuhui on 2016/6/27.
 */
public class AnchorRadioFragment extends BaseFragment {


    public AnchorRadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_ancor_radio, container, false);
        }
        return mContentView;
    }


}
