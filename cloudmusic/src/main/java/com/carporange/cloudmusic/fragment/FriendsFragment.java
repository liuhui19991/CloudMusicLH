package com.carporange.cloudmusic.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class FriendsFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager_discovery)
    ViewPager mViewPager;

    @Override
    protected void onVisible() {

    }

    @Override
    public void initViews() {
        FriendsFragmentPagerAdapter fpa = new FriendsFragmentPagerAdapter(getChildFragmentManager());
        fpa.addFragment(new DynamicFragment(), "动态");
        fpa.addFragment(new NearbyFragment(), "附近");
        fpa.addFragment(new FriendFragment(), "朋友");
        mViewPager.setAdapter(fpa);
//        mTabLayout.setSelectedTabIndicatorColor(Color.YELLOW);//设置tablayout的指示颜色
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(0xff0ff0FF, 0xfff000FF);//字体标准颜色和选中颜色
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friends;
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
