package com.carporange.cloudmusic.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.fragment.ImageFragment;
import com.carporange.cloudmusic.fragment.SimpleFragment;
import com.carporange.cloudmusic.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Random;

public class UseInFragmentActivity extends BaseActivity {
    private ViewPager mVpHome;
    private BottomNavigationBar mBottomNavigationBar;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_in_fragment;
    }

    @Override
    public void initActionBar() {
    }

    @Override
    public void initViews() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(UseInFragmentActivity.this,0, null);
        mVpHome = (ViewPager) findViewById(R.id.vp_home);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_favorite, "One").setActiveColorResource(R.color.viewfinder_frame))
                .addItem(new BottomNavigationItem(R.drawable.ic_gavel, "Two").setActiveColorResource(R.color.colorAccent))//通过这里的set可以设置底部栏的颜色
                .addItem(new BottomNavigationItem(R.drawable.ic_grade, "Three").setActiveColorResource(R.color.bg_color))
                .addItem(new BottomNavigationItem(R.drawable.ic_group_work, "Four").setActiveColorResource(R.color.divider_color))
                .setMode(BottomNavigationBar.MODE_FIXED)//0不带字,1带字
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mVpHome.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        mFragmentList.add(new ImageFragment());
        mFragmentList.add(new SimpleFragment());
        mFragmentList.add(new SimpleFragment());
        mFragmentList.add(new SimpleFragment());

        mVpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
                switch (position) {
                    case 0:
                        break;
                    default:
                        Random random = new Random();
                        int color = 0xff000000 | random.nextInt(0xffffff);
                        if (mFragmentList.get(position) instanceof SimpleFragment) {
                            ((SimpleFragment) mFragmentList.get(position)).setTvTitleBackgroundColor(color);
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpHome.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
    }

}
