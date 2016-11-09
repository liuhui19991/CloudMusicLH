package com.carporange.cloudmusic.fragment;


import android.os.Handler;
import android.widget.LinearLayout;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.event.TitleEvent;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import cn.world.liuhui.utils.LoadingDialogUtil;

/**
 * Created by liuhui on 2016/6/27.
 */
public class RankingListFragment extends BaseFragment {

    private LinearLayout ll;
    private Handler handler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ranking_list;
    }

    @Override
    public void initViews() {
//        ll = (LinearLayout) mContentView.findViewById(R.id.progress);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ll.setVisibility(View.GONE);
//            }
//        }, 2000);
        LoadingDialogUtil.showDialog(mContext);
        EventBus.getDefault().post(new TitleEvent("排行榜"));
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
