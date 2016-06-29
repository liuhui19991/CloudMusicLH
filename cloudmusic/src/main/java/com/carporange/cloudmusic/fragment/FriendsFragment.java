package com.carporange.cloudmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhui on 2016/6/27.
 */
public class FriendsFragment extends BaseFragment {
    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_friends, container, false);
            initViews();
        }
        return mContentView;
    }

    private void initViews() {
        mTabLayout = findView(R.id.tabLayout);
        mViewPager = findView(R.id.viewPager_discovery);
        FriendsFragmentPagerAdapter fpa = new FriendsFragmentPagerAdapter(getChildFragmentManager());
        fpa.addFragment(new DynamicFragment(), "动态");
        fpa.addFragment(new NearbyFragment(), "附近");
        fpa.addFragment(new FriendFragment(), "朋友");
        mViewPager.setAdapter(fpa);
//        mTabLayout.setSelectedTabIndicatorColor(Color.YELLOW);//设置tablayout的指示颜色
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(0xff0ff0FF, 0xfff000FF);//字体标准颜色和选中颜色
    }

    class FriendsFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mList = new ArrayList<>();
        private List<String> mTitleList = new ArrayList<>();

        public FriendsFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String string) {
            mList.add(fragment);
            mTitleList.add(string);
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
