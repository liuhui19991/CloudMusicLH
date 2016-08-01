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
        String s = "{\"banners\":[{\"url\":\"http://www.sj88.com/attachments/bd-aldimg/1204/123/5.jpg\",\"banner\":\"http://pic10.nipic.com/20101103/5063545_000227976000_2.jpg\"}," +
                "{\"url\":\"http://www.hao123.com\",\"banner\":\"http://imgstore.cdn.sogou.com/app/a/100540002/714860.jpg\"}," +
                "{\"url\":\"http://www.sj88.com/attachments/bd-aldimg/1204/123/11.jpg\",\"banner\":\"http://www.deskcar.com/desktop/fengjing/201081215337/19.jpg\"}," +
                "{\"url\":\"http://img5.duitang.com/uploads/item/201411/18/20141118221443_iCX3X.jpeg\",\"banner\":\"http://pic3.nipic.com/20090715/2919184_104743067_2.jpg\"}]}\n";
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
