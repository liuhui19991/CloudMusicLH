package com.carporange.cloudmusic.fragment;


import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.event.TitleEvent;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liuhui on 2016/6/27.
 */
public class RankingListFragment extends BaseFragment {

    private LinearLayout ll;
    private Handler handler = new Handler();

    public RankingListFragment() {
        // Required empty public constructor

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ranking_list;
    }

    @Override
    protected void onVisible() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll.setVisibility(View.GONE);
            }
        }, 2000);
        EventBus.getDefault().post(new TitleEvent("排行榜"));
    }

    @Override
    public void initViews() {
        ll = (LinearLayout) mContentView.findViewById(R.id.progress);
    }
}
