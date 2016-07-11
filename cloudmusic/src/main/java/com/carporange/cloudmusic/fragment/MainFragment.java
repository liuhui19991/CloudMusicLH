package com.carporange.cloudmusic.fragment;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.CarpFragmentPagerAdapter;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class MainFragment extends BaseFragment {
    private Activity mActivity;
    @BindView(R.id.viewPager_main)
    ViewPager mViewPager;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;

    public MainFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void initViews() {
        List<Fragment> list = new ArrayList<>();
        list.add(new DiscoverFragment());
        list.add(new MusicFragment());
        list.add(new FriendsFragment());
        FragmentPagerAdapter fpa = new CarpFragmentPagerAdapter(list, getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(fpa);
        mRadioGroup.check(R.id.rb_discover);
    }

    @Override
    protected void onVisible() {

    }

    @Override
    public void setListeners() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioGroup.check(R.id.rb_discover);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.rb_music);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.rb_friends);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_discover:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_music:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_friends:
                        mViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }
}
