package com.carporange.cloudmusic.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/6/27.
 */
public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager_discovery)
    ViewPager mViewPager;

    public DiscoverFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_discover, container, false);
            ButterKnife.bind(this, mContentView);
            initViews();
        }
        return mContentView;
    }

    private void initViews() {
        CarpFragmentPagerAdapter fpa = new CarpFragmentPagerAdapter(getChildFragmentManager());
        fpa.addFragment(new PersonalRecommendationFragment(), "个性推荐");
        fpa.addFragment(new SongMenuFragment(), "歌单");
        fpa.addFragment(new AnchorRadioFragment(), "主播电台");
        fpa.addFragment(new RankingListFragment(), "排行榜");
        mViewPager.setAdapter(fpa);
        mTabLayout.setSelectedTabIndicatorColor(0xff324567);//设置tablayout的指示颜色
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(0xff0ff0FF, 0xfff000FF);//字体标准颜色和选中颜色
    }

    public class CarpFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList = new ArrayList<>();
        private List<String> mTitleList = new ArrayList<>();

        public CarpFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mList.add(fragment);
            mTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }


}
