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
public class PersonalRecommendationFragment extends BaseFragment {


    public PersonalRecommendationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_recommendation, container, false);
    }


}
