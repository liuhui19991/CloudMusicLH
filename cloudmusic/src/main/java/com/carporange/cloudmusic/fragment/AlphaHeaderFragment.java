package com.carporange.cloudmusic.fragment;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.alphaswip.HeaderViewPagerFragment;
import com.carporange.cloudmusic.alphaswip.RecyclerViewFragment;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.lzy.widget.HeaderViewPager;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static com.carporange.cloudmusic.R.id.scrollableLayout;

/**
 * Created by liuhui on 2017/3/6.
 */

public class AlphaHeaderFragment extends BaseFragment {
    @BindView(scrollableLayout)
    HeaderViewPager mHeaderViewPager;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.banner)
    Banner mViewPagerHeader;
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
    public void initViews() {
        networkImages = Arrays.asList(images);
        //设置图片加载器
        mViewPagerHeader.setImageLoader(new GlideImageLoader())
                //设置图片集合
                .setImages(networkImages)
                //banner设置方法全部调用完毕时最后调用
                .start();

        mAppBarLayout.setAlpha(0.0f);
        fragments = new ArrayList<>();
        fragments.add(RecyclerViewFragment.newInstance());
        mHeaderViewPager.setCurrentScrollableContainer(fragments.get(0));
        mViewPager.setAdapter(new ContentAdapter(getFragmentManager()));
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

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

           /* 注意：
            1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
            2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
            传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
            切记不要胡乱强转！*/


            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

//            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);

//            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }
    }
}
