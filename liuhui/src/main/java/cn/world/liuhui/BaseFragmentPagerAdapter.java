package cn.world.liuhui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 创建一FragmentViewPager的一个适配器
 * Created by liuhui on 2015/8/8.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm);
        mFragmentList = fragmentList;
        this.mTitles = mTitles;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return isNullOrEmpty(mTitles) ? super.getPageTitle(position) : mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    private boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }
}
