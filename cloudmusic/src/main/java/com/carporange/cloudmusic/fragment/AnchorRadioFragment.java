package com.carporange.cloudmusic.fragment;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.T;
import com.carporange.cloudmusic.widget.ViewPagerCycle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class AnchorRadioFragment extends BaseFragment {
    @BindView(R.id.et)
    EditText mEditText;
    @BindView(R.id.view_pager)
    ViewPagerCycle mViewPagerCycle;
    private List mList;

    public AnchorRadioFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViews() {//如果使用buttknife当从第一个页面直接点过来的时候获取不到对象
        int[] images = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e,};
        ImageView iv;
        mList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            iv = new ImageView(getActivity());
            iv.setBackgroundResource(images[i]);
            mList.add(iv);
        }

        mViewPagerCycle.setImageResource(mList, new ViewPagerCycle.ViewpagerCycleListener() {
            @Override
            public void onClick(int position, View imageView) {
                String string = (String) imageView.getTag();
                T.showShort(getActivity(), string + "位置" + position);
            }
        });
        if (mViewPagerCycle != null) {
            mViewPagerCycle.startImageCycle();
        }
    }


    @Override
    protected void onVisible() {
        if (mViewPagerCycle != null) {
            mViewPagerCycle.startImageCycle();
        }
    }

    @Override
    public void onInvisible() {
        if (mViewPagerCycle != null) {
            mViewPagerCycle.pauseImageCycle();//暂停轮播
            L.e("停止轮播");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ancor_radio;
    }


}
