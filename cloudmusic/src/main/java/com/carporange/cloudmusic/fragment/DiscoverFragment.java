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
public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager_discovery)
    ViewPager mViewPager;

    public DiscoverFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    public void initViews() {
        CarpFragmentPagerAdapter fpa = new CarpFragmentPagerAdapter(getChildFragmentManager());
        fpa.addFragment(new PersonalRecommendationFragment(), "个性推荐");
        fpa.addFragment(new SongMenuFragment(), "歌单");
        fpa.addFragment(new AnchorRadioFragment(), "主播电台");
        fpa.addFragment(new RankingListFragment(), "排行榜");
        mViewPager.setAdapter(fpa);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setSelectedTabIndicatorColor(0xff324567);//设置tablayout的指示颜色
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(0xff0ff0FF, 0xfff000FF);//字体标准颜色和选中颜色
    }

}
