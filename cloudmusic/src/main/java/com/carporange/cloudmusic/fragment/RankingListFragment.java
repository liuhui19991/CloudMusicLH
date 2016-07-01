package com.carporange.cloudmusic.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.WaitDialog;
import com.carporange.cloudmusic.ui.base.BaseFragment;

/**
 * Created by liuhui on 2016/6/27.
 */
public class RankingListFragment extends BaseFragment {

    private View view;
    private LinearLayout ll;
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public RankingListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_ranking_list, container, false);
            initView();
        }
        return view;
    }

    private void initView() {
        ll = (LinearLayout) view.findViewById(R.id.progress);
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(2000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                ll.setVisibility(View.GONE);
                    }
                });
            }
        }.start();
    }

}
