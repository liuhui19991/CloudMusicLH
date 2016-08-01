package com.carporange.cloudmusic.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.domain.ViewBanner;
import com.carporange.cloudmusic.ui.activity.WebPageActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.GsonUtil;
import com.carporange.cloudmusic.util.T;
import com.carporange.cloudmusic.widget.ViewPagerCycle;

import java.util.List;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class AnchorRadioFragment extends BaseFragment {
    @BindView(R.id.et)
    EditText mEditText;
    @BindView(R.id.view_pager)
    ViewPagerCycle mViewPagerCycle;
    private ViewBanner mViewBanner = new ViewBanner();
    private List<ViewBanner.BannersBean> mList;

    public AnchorRadioFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViews() {//如果使用buttknife当从第一个页面直接点过来的时候获取不到对象
        String s = "{\"banners\":[{\"url\":\"http://www.sina.com.cn/\",\"banner\":\"http://img3.duitang.com/uploads/blog/201307/31/20130731175439_uYwZk.thumb.700_0.png\"}," +
                "{\"url\":\"http://www.hao123.com\",\"banner\":\"http://h.hiphotos.baidu.com/image/pic/item/6a600c338744ebf8bd33cee3dcf9d72a6159a7a8.jpg\"}," +
                "{\"url\":\"http://www.taobao.com\",\"banner\":\"http://img3.duitang.com/uploads/item/201310/06/20131006150845_sVHkK.jpeg\"}," +
                "{\"url\":\"http://www.qq.com\",\"banner\":\"http://d.ifengimg.com/mw600/p3.ifengimg.com/cmpp/2016/07/27/15/d31a6642-4167-4e71-9d36-f1ab38cea954_size305_w1200_h800.jpg\"}]}\n";
        mViewBanner = GsonUtil.json2Bean(s, ViewBanner.class);
        mList = mViewBanner.getBanners();
        mViewPagerCycle.setImageResource(mList, new ViewPagerCycle.ViewpagerCycleListener() {
            @Override
            public void onClick(int position, View imageView) {
                String string = mList.get(position % mList.size()).getUrl();
//                String string = (String) imageView.getTag();
                Intent intent = new Intent(getActivity(), WebPageActivity.class);
                intent.putExtra("title", "新闻");
                intent.putExtra("url", string);
                startActivity(intent);
                T.showShort(getActivity(), string + "位置" + position);
            }
        });
        if (mViewPagerCycle != null) {
            mViewPagerCycle.startImageCycle();
        }
    }


    @Override
    protected void onVisible() {
        if (mViewPagerCycle != null) {
            mViewPagerCycle.startImageCycle();
        }
    }

    @Override
    public void onInvisible() {
        if (mViewPagerCycle != null) {
            mViewPagerCycle.stopImageCycle();//暂停轮播
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ancor_radio;
    }


}
