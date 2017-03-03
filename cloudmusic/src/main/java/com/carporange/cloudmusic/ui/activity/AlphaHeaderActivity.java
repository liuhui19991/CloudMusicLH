package com.carporange.cloudmusic.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.alphaswip.HeaderViewPagerFragment;
import com.carporange.cloudmusic.alphaswip.RecyclerViewFragment;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.lzy.widget.HeaderViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.carporange.cloudmusic.R.id.scrollableLayout;

/**
 * Created by liuhui on 2017/3/2.
 */

public class AlphaHeaderActivity extends BaseActivity {
    @BindView(R.id.iv_header)
    ImageView mImageView;
    @BindView(scrollableLayout)
    HeaderViewPager mHeaderViewPager;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    public List<HeaderViewPagerFragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alphaheader;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        toolbar.setTitle("滑动渐变");
    }

    @Override
    public void initViews() {
        mAppBarLayout.setAlpha(0.0f);
        // TODO: 2017/3/2  需要做下拉放大
        fragments = new ArrayList<>();
        fragments.add(RecyclerViewFragment.newInstance());
        mHeaderViewPager.setCurrentScrollableContainer(fragments.get(0));
        mViewPager.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        mHeaderViewPager.setOnScrollListener(new HeaderViewPager.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                //动态改变标题栏的透明度,注意转化为浮点型
                float alpha = 1.0f * currentY / maxY;
                mAppBarLayout.setAlpha(alpha);
            }
        });
    }
    /**
     * 内容页的适配器
     */
    private class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        public String[] titles = new String[]{"ScrollView", "ListView", "GridView", "RecyclerView", "WebView"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
