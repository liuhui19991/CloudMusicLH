package com.carporange.cloudmusic.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.widget.DetailListView;
import com.carporange.cloudmusic.widget.DetailScrollView;
import com.carporange.cloudmusic.widget.DetailWebView;

import java.util.ArrayList;
import java.util.HashMap;

public class WebAndListViewActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<HashMap<String, String>> list;
    //private VerticalLayout vertical_layout;
    String[] from = {"name", "id"};              //这里是ListView显示内容每一列的列名
    int[] to = {R.id.user_name, R.id.user_id};   //这里是ListView显示每一列对应的list_item中控件的id
    private DetailScrollView mScrollView;
    private DetailWebView mWebView;
    private DetailListView mListView;
    private SimpleAdapter mAdapter;
    private int mListScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    private int mFirstVisibleItem;
    private int mScreenHeight;
    private int mLastY;
    private TextView tvReply;
    private int mWebViewHeight;

    // WEBVIEW高度:48692
    public void onBtnScroll(View view) {
        try {
            //测试这个高度和mWebViewHeight是一样的
            mWebViewHeight = mWebView.getHeight();
            //在webview区域
            if (mScrollView.getScrollY() < mWebViewHeight) {
                mLastY = mScrollView.getScrollY();
                mScrollView.smoothScrollTo(0, mWebViewHeight);
                Log.e(TAG, mWebView.getHeight() + "按钮点击web区域onBtnScroll: " + mLastY);
                //非webview区域
            } else {
                Log.e(TAG, "按钮点击onBtnScroll: " + mLastY);
                mScrollView.smoothScrollTo(0, mLastY);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webandlistview);
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mScrollView = (DetailScrollView) findViewById(R.id.scrollView);
        mWebView = (DetailWebView) findViewById(R.id.webview);
        mListView = (DetailListView) findViewById(R.id.list_view);
        tvReply = (TextView) findViewById(R.id.tvReply);
        initWebView();
        initListView();
        initScrollView();
    }

    private void initWebView() {
        //webview 高度自动撑开
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        String url = "http://test-news-node.seeyouyima.com/article?news_id=681558";
        url = "https://news-node.seeyouyima.com/article?news_id=1817909&platform=android&v=5.9.1&tbuid=&statinfo=eyJvdCI6IiIsInVhIjoiTGUgWDYyMCIsIm9zIjoiMiIsIm9zdmVyc2lvbiI6IjYuMCIsInNka3ZlcnNpb24iOjIzLCJvcGVudWRpZCI6IjRkZDcwM2VlLTcwNGQtNDZmNy1hNGM1LTU0OTcxYTMyMTI2YiIsImFwbiI6IjQiLCJ1aWQiOjQ5MzQ3NjI4LCJidWlsZHYiOiI1LjkuMSIsIm1hYyI6Ijg2MjM4MDAzOTY0NTExNyIsImFuZHJvaWRpZCI6ImE5NmFmZTJjYzgzMjJhN2QiLCJpbWVpIjoiODYyMzgwMDM5NjQ1MTE3Iiwic291cmNlIjoiU2VleW91QWN0aXZpdHktPk5ld3NEZXRhaWxINUFjdGl2aXR5IiwiY2hhbm5lbGlkIjoiMTExMSIsInYiOiI1LjkuMSJ9&device_id=862380039645117&imei=862380039645117&mode=0&sdkversion=23&bundleid=1111&app_id=1&myclient=0130591111100000&myuid=49347628&auth=3.4BWmwQT1nK5qyKA3W9oyCfPhPihHumJ3l%2BcbuBVupwI%3D";

        //url = "http://test-news-node.seeyouyima.com/article?news_id=681523";        //网页很短测试
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    private void initListView() {

        //创建ArrayList对象；
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HashMap<String, String> map = new HashMap<String, String>();       //为避免产生空指针异常，有几列就创建几个map对象
            map.put("id", "userId:  " + i);
            map.put("name", "userName：" + i);
            list.add(map);
        }
        mAdapter = new SimpleAdapter(this, list, R.layout.list_item, from, to);
        TextView textView = new TextView(this);
        textView.setText("我是头部");
        mListView.addHeaderView(textView);//这块代码得在setAdapter之前执行,这块好像会影响webview的加载

        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mListScrollState = scrollState;
                int lastIndex = mListView.getAdapter().getCount() - 1;
                //滚动到底部
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mListView.getLastVisiblePosition() == lastIndex) {
                    Log.d(TAG, "=======>listview 滚动到底部 加载更多,处理事件");
                    appendListView();
                    handleListViewTouchEvent();
                    return;
                }
                //滚动到顶部
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mFirstVisibleItem == 0 && mListView.getChildAt(0).getTop() == 0) {
                    Log.d(TAG, "=======>listview 滚动到顶部,不处理事件了,scrollview往上滚动5,让scrollview处理事件");
                    if (mScrollView != null)
                        mScrollView.smoothScrollBy(0, -5);//这里是滑动到listview的顶部的时候让上面的webview显示5个高度
                    mListView.setHandleTouchEvent(false);
                    return;
                }
                //停止的时候,进行事件决定
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    handleListViewTouchEvent();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mFirstVisibleItem = firstVisibleItem;
                //Log.d(TAG,"onScroll firstVisibleItem:"+firstVisibleItem+"==>visibleItemCount:"+visibleItemCount+"==totalItemCount:"+totalItemCount);
            }
        });
        int height = getListViewHeight();
        mListView.getLayoutParams().height = height;
        mListView.requestLayout();
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d(TAG, "MyListView onTouch ACTION_DOWN");
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d(TAG, "MyListView onTouch ACTION_MOVE");
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d(TAG, "MyListView onTouch ACTION_UP");
                }
                //触摸的时候,让父控件不要拦截我的所有事件
                if (mScrollView != null && mListView.isHandleTouchEvent()) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                return false;
            }
        });
        mListView.setMoveListener(new DetailListView.onMoveListener() {
            @Override
            public void onMove(float distance) {
                //listview在顶部的时候,往下滑动,此时需要scrollview跟着一起滚动,进行过渡
                if (mScrollView != null && mListView != null && mListView.getChildCount() > 0 && getListViewPositionAtScreen() >= getTopHeight() && distance > 0 && mFirstVisibleItem == 0 && mListView.getChildAt(0).getTop() == 0) {
                    // 滚动
                    mScrollView.smoothScrollBy(0, -(int) distance);
                    // 取消listview的事件消费,会在其onTouchEvent返回false
                    mListView.setHandleTouchEvent(false);
                    isMoving = true;
                }
            }

            //拖动scrollview一起滚动之后,在松开的时候,需要有个惯性滚动
            @Override
            public void onUp(final int velocityY) {
                if (isMoving) {
                    Log.d(TAG, "mListView onUp:" + velocityY);
                    isMoving = false;
                    //延迟一下fling才有效果。
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mScrollView != null)
                                mScrollView.fling(-velocityY);
                        }
                    }, 50);
                }
            }
        });
    }

    private boolean isMoving = false;


    //webview是否超过可见范围,也就是,是否可滚动
    private boolean isWebViewOverScreen() {
        return mWebViewHeight > (mScreenHeight - getTopAndBottomHeight()) ? true : false;
    }

    private void initScrollView() {
        mScrollView.setChildListView(mListView);
        mScrollView.setScanScrollChangedListener(new DetailScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom(int vericalY) {
                //到底的时候,事件交给listview,此时,需要让scrollview惯性滚动一下,没滚动完之前,不运行scrollview拦截
                if (!mListView.isHandleTouchEvent() && vericalY < 0 && !mScrollView.isTouchingScrollView()) {
                    handleListViewTouchEvent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mListView.fling(Math.abs(vericalY / 3));
                    } else {
                        mListView.startFling(Math.abs(vericalY / 3)/*5000*/);
                    }
                    mListView.setHandleTouchEvent(true);
                    Log.d(TAG, "==》onScrolledToBottom 让listview fling了!!");
                    return;
                }
                handleListViewTouchEvent();

            }

            @Override
            public void onScrolledToTop() {

            }
        });
        mScrollView.setMoveListener(new DetailScrollView.onMoveListener() {
            //当按下scrollview的时候,如果listview还在fling,强制重置它的位置,并抢夺事件;
            @Override
            @TargetApi(21)
            public void onDown() {

                if (mListView != null && mListScrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Log.d(TAG, "onDown listView还在fling,重置它的位置");
                    mListView.stopFling();
                    mListView.setSelectionFromTop(0, 1);
                    mListView.setHandleTouchEvent(false);
                }
            }

            //ListView过渡到ScrollView的时候,需要再惯性让ScrollView再滚动一下
            @Override
            public void onUp(int velocityY) {

                if (isMoving) {
                    Log.d(TAG, "mScrollView onUp:" + velocityY);
                    isMoving = false;
                    if (mScrollView != null && isWebViewOverScreen()) {
                        mScrollView.fling(velocityY);
                    }
                }
                Log.d(TAG, "mScrollView onUp");
                handleListViewTouchEvent();
                //防止自带弹性效果的scrollview 计算不准确,再计算一次
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleListViewTouchEvent();
                    }
                }, 1000);

            }

            @Override
            @TargetApi(21)
            public void onMove(float distance) {
                if (mListView != null && mListScrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Log.d(TAG, "onMove listView还在fling,重置它的位置");
                    mListView.stopFling();
                    mListView.setSelectionFromTop(0, 1);
                    mListView.setHandleTouchEvent(false);
                    mListScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
                }
            }
        });
    }

    private void appendListView() {
        Toast.makeText(getApplicationContext(), "正在加载更多", Toast.LENGTH_SHORT).show();
        int count = list.size();
        for (int i = count; i < 20 + count; i++) {
            HashMap<String, String> map = new HashMap<String, String>();       //为避免产生空指针异常，有几列就创建几个map对象
            map.put("id", "userId:  " + i);
            map.put("name", "userName：" + i);
            list.add(map);
        }
        mAdapter.notifyDataSetChanged();
    }


    private int getListViewHeight() {
        int value = getResources().getDisplayMetrics().heightPixels - getTopAndBottomHeight() + getTopHeight();//+ getTopHeight()添加这句后可实现顶部没有webview残留占据顶部栏
        return value;
    }

    private int getTopHeight() {//这块的高度会使listview上面相等高度不能滑动
        return dip2px(getApplicationContext(), 50 + 25);//后面数字原来为50 + 25,改为0或者修改getListViewHeight()解决问题
        //状态栏+标题栏
    }

    private int getTopAndBottomHeight() {
        return getTopHeight() + dip2px(getApplicationContext(), 45);
        //状态栏+标题栏+回复栏
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int getListViewPositionAtScreen() {
        try {
            int[] location = new int[2];
            mListView.getLocationInWindow(location);//这一组是获取相对在它父窗口里的坐标,location[0]为x
            int y = location[1];
            return y;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    //停止后,决定事件是由谁来处理的;
    private void handleListViewTouchEvent() {
        try {
            if (mListView == null)
                return;
            if (mListView.getChildCount() > 0 && getListViewPositionAtScreen() > getTopHeight()) {
                mListView.setHandleTouchEvent(false);
                Log.d(TAG, "==》handleListViewTouchEvent listview 露出屏幕,由外部处理事件");
            } else {
                mListView.setHandleTouchEvent(true);
                Log.d(TAG, "==》handleListViewTouchEvent listview 沾满全屏,自己处理事件");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
