package com.carporange.cloudmusic.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.CarpFragmentPagerAdapter;
import com.carporange.cloudmusic.ui.base.BaseFragment;

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
    public void initViews() {
        CarpFragmentPagerAdapter fpa = new CarpFragmentPagerAdapter(getChildFragmentManager());
        fpa.addFragment(new DynamicFragment(), "动态");
        fpa.addFragment(new NearbyFragment(), "附近");
        fpa.addFragment(new FriendFragment(), "朋友");
        mViewPager.setAdapter(fpa);
//        mTabLayout.setSelectedTabIndicatorColor(Color.YELLOW);//设置tablayout的指示颜色
        mViewPager.setOffscreenPageLimit(fpa.getCount());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(0xff0ff0FF, 0xfff000FF);//字体标准颜色和选中颜色
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friends;
    }

}
