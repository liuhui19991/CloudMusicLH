package com.carporange.cloudmusic.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.domain.ViewBanner;
import com.carporange.cloudmusic.util.GlideUtil;

import java.util.List;

/**
 * Created by liuhui on 2016/6/15.
 */
public class ViewPagerCycle extends LinearLayout {
    private MyImageCyclePageAdapter mPageAdapter;
    private int count = 10000;//数字不能太大,太大会ANR
    private Context mContext;
    private ImageView mRedPoint;
    private ViewPager mViewPager;
    private List<ViewBanner.BannersBean> mList;
    private LinearLayout mLinearLayout;
    /**
     * 前一个被选中的position的位置
     */
    private int previousposition = 0;
    private Handler mHandler;
    /**
     * 两点之间的距离
     */
    private int mPointDis;
    private ViewpagerCycleListener mViewpagerCycleListener;

    public ViewPagerCycle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.viewpagercycle, this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_point);
        mRedPoint = (ImageView) findViewById(R.id.iv_red_point);
    }


    public void setImageResource(List list, ViewpagerCycleListener listener) {
        mList = list;
        mViewpagerCycleListener = listener;
        mViewPager.setPageTransformer(true,new DepthPageTransformer());//设置ViewPager的切换动画
        initView();
    }

    /**
     * 停止轮播—用于节省资源
     */
    public void stopImageCycle() {
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 开始轮播
     */
    public void startImageCycle() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    private void initView() {
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    mHandler.sendEmptyMessageDelayed(0, 3000);//发送延时3秒的消息
                }
            };
//            mHandler.sendEmptyMessageDelayed(0, 3000);//发送延时3秒的消息
        }
        if (mList.size() > 1) {
            mRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    mRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mPointDis = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                }
            });
        }
        initData();//初始化数据
        mPageAdapter = new MyImageCyclePageAdapter();
        mViewPager.setAdapter(mPageAdapter);
        //设置默认选中的点和图片
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 这个方法中的代码全部注掉后可以实现,指示点不移动
             * @param position             当前位置
             * @param positionOffset       移动偏移百分比
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int leftMargin;
                leftMargin = (int) (mPointDis * (position % mList.size() + positionOffset));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
                mRedPoint.setImageResource(R.drawable.shape_point_red);
                if (position % mList.size() == (mList.size() - 1) && (position + 1) % mList.size() == 0) {//当移动到最后一个点的时候就不让小红点移动了
                    params.leftMargin = mPointDis * (position % mList.size());
                } else {
                    params.leftMargin = leftMargin;
                }
                mRedPoint.setLayoutParams(params);
            }

            /**
             * 当viewpager被选中时候调用的方法
             *让指示点不移动
             * @param position 移动到的位置
             */
            @Override
            public void onPageSelected(int position) {
                int newposition = position % mList.size();
                int leftMargin = newposition * mPointDis;
                previousposition = newposition;//用完之后把position赋值给previousposition就是上一个viewpageer所在的地方了
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
                mRedPoint.setImageResource(R.drawable.shape_point_red);
                params.leftMargin = leftMargin;
                mRedPoint.setLayoutParams(params);

            }

            /**
             * 当viewpage状态改变时候调用的方法
             *
             * @param state 滑动状态
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 0://滑动结束

                        break;
                    case 1://手势滑动

                        break;
                    case 2://界面切换

                        break;
                }
            }
        });
        int m = count / 2 % mList.size();
        int currentpager = count / 2 - m;
        //设置当前显示的条目
        mViewPager.setCurrentItem(currentpager);
    }

    private void initData() {
        LayoutParams params;
        if (mLinearLayout != null) {
            mLinearLayout.removeAllViews();
        }
        for (int i = 0; i < mList.size(); i++) {
            //每循环一次要向linearlayout里面添加一个点的view对象
            ImageView grePoint = new ImageView(mContext);
            grePoint.setImageResource(R.drawable.shape_point_gre);
            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0) {//当前不是第一个点,需要设置边距
                params.leftMargin = 8;
            }
            grePoint.setLayoutParams(params);
            if (mLinearLayout != null) {
                mLinearLayout.addView(grePoint);
            }
        }
    }


    class MyImageCyclePageAdapter extends PagerAdapter {

        /**
         * @return viewpager的长度
         */
        @Override
        public int getCount() {
            return count;
        }

        /**
         * 判断是否使用缓存
         *
         * @param view
         * @param object
         * @return 返回true 表示使用缓存,否则调用instantiateItem()创建一个新的对象
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object; //此处为固定写法
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //下面两句代码作用一样,第一句为原始
//            mViewPager.removeView(mList.get(position % mList.size()));
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(getContext());
//            imageView.setTag("设置TAG");
            GlideUtil.display(imageView,mList.get(position%mList.size()).getBanner());
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewpagerCycleListener.onClick(position, v);
                }
            });

            container.addView(imageView);
            return imageView;
        }
    }

    public interface ViewpagerCycleListener {
        /**
         * 每个viewpager点击时候调用的方法
         */
        void onClick(int position, View imageView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } else if (ev.getAction() == MotionEvent.ACTION_CANCEL) {
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } else {
            mHandler.removeCallbacksAndMessages(null);
        }
        return super.dispatchTouchEvent(ev);
    }
}
