package com.carporange.cloudmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liyuchen on 2016/6/15.
 * email:987424501@qq.com
 * phone:18298376275
 */
public class CarpFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public CarpFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CarpFragmentPagerAdapter(List<Fragment> list, FragmentManager fm) {
        super(fm);
        mList = list;
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
