package com.carporange.cloudmusic.ui.activity;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.alphaswip.HeaderViewPagerFragment;
import com.carporange.cloudmusic.alphaswip.RecyclerViewFragment;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.lzy.widget.HeaderViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.world.liuhui.utils.ImageLoaderUtil;

import static com.carporange.cloudmusic.R.id.scrollableLayout;

/**
 * Created by liuhui on 2017/3/2.
 */

public class AlphaHeaderActivity extends BaseActivity {
    @BindView(scrollableLayout)
    HeaderViewPager mHeaderViewPager;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.vp_header)
    ConvenientBanner mViewPagerHeader;
    public List<HeaderViewPagerFragment> fragments;//必须要用继承headerviewpagerfragment的类
    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

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
        networkImages = Arrays.asList(images);
        mViewPagerHeader.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.shape_point_gre, R.drawable.shape_point_red})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setCanLoop(true);
        mViewPagerHeader.setCanLoop(true);

        mAppBarLayout.setAlpha(0.0f);
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

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageLoaderUtil.display(imageView, data);
        }
    }
}
