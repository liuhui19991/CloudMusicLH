package com.carporange.cloudmusic.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.ListRecyclerAdapter;
import com.carporange.cloudmusic.adapter.MyAdapter;
import com.carporange.cloudmusic.domain.ViewBanner;
import com.carporange.cloudmusic.event.ProgressVideoPlayer;
import com.carporange.cloudmusic.ui.activity.BlurredViewBasicActivity;
import com.carporange.cloudmusic.ui.activity.JsActivity;
import com.carporange.cloudmusic.ui.activity.RefreshLoadMoreActivity;
import com.carporange.cloudmusic.ui.activity.WeatherActivity;
import com.carporange.cloudmusic.ui.activity.WebPageActivity;
import com.carporange.cloudmusic.ui.activity.WebviewActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.ui.dialog.WaitDialog;
import com.carporange.cloudmusic.util.GsonUtil;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.T;
import com.carporange.cloudmusic.widget.ViewPagerCycle;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.world.liuhui.widget.NumberProgressBar;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by liuhui on 2016/6/27.
 */
public class AnchorRadioFragment extends BaseFragment {
    @BindView(R.id.et)
    EditText mEditText;
    @BindView(R.id.view_pager)
    ViewPagerCycle mViewPagerCycle;
    @BindView(R.id.progressbar)
    TextView mDownTextView;
    @BindView(R.id.down_progress)
    NumberProgressBar mNumberProgressBar;
    private BottomSheetDialog mBottomSheetDialog;
    private String SAVE_URL = Environment.getExternalStorageDirectory() + "/liuhui/";
    private String VIDEO_DOWN_URL = "http://resource.gbxx123.com/video/mp4/dq/2015/3/25/1427219238357/1427219238357.mp4";
    //            "http://resource.gbxx123.com/video/mp4/gq/1328171871228/1328171871228.mp4";
    private ViewBanner mViewBanner = new ViewBanner();
    private List<ViewBanner.BannersBean> mList;
    private DownloadRequest mMDownloadRequest;

    public AnchorRadioFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ancor_radio;
    }

    @Override
    protected void initViews() {//如果使用buttknife当从第一个页面直接点过来的时候获取不到对象
        String s = "{\"banners\":[{\"url\":\"http://ww1.sinaimg.cn/mw1024/532722c9gw1f66czxn4xwj21400qo7oi.jpg\",\"banner\":\"http://pic10.nipic.com/20101103/5063545_000227976000_2.jpg\"}," +
                "{\"url\":\"http://www.hao123.com\",\"banner\":\"http://imgstore.cdn.sogou.com/app/a/100540002/714860.jpg\"}," +
                "{\"url\":\"http://static8.photo.sina.com.cn/orignal/4b3f4b54c456c0af533f7\",\"banner\":\"http://www.deskcar.com/desktop/fengjing/201081215337/19.jpg\"}," +
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
            }
        });
        createBottomSheetDialog();
        File dirFile = new File(SAVE_URL);
        if (!dirFile.exists()) {
            L.e("创建一个文件夹");
            dirFile.mkdir();
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

    @OnClick(R.id.blureed)
    void goBlurredActivity() {
        startActivity(new Intent(getActivity(), BlurredViewBasicActivity.class));
    }

    @OnClick(R.id.blureed1)
    void goBlurredActivity2() {
        startActivity(new Intent(getActivity(), WeatherActivity.class));
    }

    @OnClick(R.id.progressbar)
    void showProgressbar() {//开始下载
        /*ProgressDialog progressDialog = new ProgressDialog(etContext());
        progressDialog.show();*/
        if (mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
        } else {
            mBottomSheetDialog.show();
        }
    }

    private void downLoad() {
        mMDownloadRequest = NoHttp.createDownloadRequest(VIDEO_DOWN_URL, SAVE_URL, "123.mp4", true, true);
        DownloadQueue downloadQueue = NoHttp.newDownloadQueue();
        downloadQueue.add(0, mMDownloadRequest, downloadListener);
    }

    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onDownloadError(int what, Exception exception) {

        }

        @Override
        public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
//            mDownTextView.setText("暂停");
            L.e("开始下载");
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            mNumberProgressBar.setProgress(progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
//            mDownTextView.setText("下载完成");
            L.e("回调下载地址" + filePath);
            JCVideoPlayerStandard.startFullscreen(mContext, JCVideoPlayerStandard.class, filePath);
        }

        @Override
        public void onCancel(int what) {

        }
    };

    @OnClick(R.id.progresswait)
    void showProgresswait() {
        WaitDialog waitDialog = new WaitDialog(getContext());
        waitDialog.show();
    }

    @OnClick(R.id.dicuss)
    void goWebviewActivity() {
        startActivity(new Intent(getContext(), WebviewActivity.class));
    }

    @OnClick(R.id.popup)
    void showPopupWindow() {
        showPopwindow();
    }

    @OnClick(R.id.jsactivity)
    void goJsActivity() {
        startActivity(new Intent(getActivity(), JsActivity.class));
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(getContext()).inflate(R.layout.poplayout, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(getActivity().findViewById(R.id.popup),
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        Button first = (Button) view.findViewById(R.id.first);
        first.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (window != null) {
                    window.dismiss();//隐藏popupwindow
                }
                startActivity(new Intent(getActivity(), RefreshLoadMoreActivity.class));
            }
        });
        Button second = (Button) view.findViewById(R.id.second);
        second.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                T.showShort(mContext, "点");
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }

    private void createBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_bottom_sheet, null, false);
        mBottomSheetDialog.setContentView(view);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        List<String> list = new ArrayList<>();
        /*for (int i = 0; i < 20; i++) {
            list.add("我是第" + i + "个");
        }*/
        list.add("下载视频");
        list.add("打开文件");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListRecyclerAdapter adapter = new ListRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemCclick(View v, int position) {
                switch (position) {
                    case 0:
                        downLoad();
                        break;
                    case 1:
                        L.e("本地地址" + SAVE_URL + File.separator + "video.mp4");
                        JCVideoPlayerStandard.startFullscreen(mContext, ProgressVideoPlayer.class,
                                "/storage/emulated/0/Android/data/com.xcjy.activity/files/Download//53818/1427219238357.mp4");
                        break;
                    default:

                        break;
                }
                if (mBottomSheetDialog != null)
                    mBottomSheetDialog.dismiss();
            }
        });
        setBehaviorCallback();
    }

    private void setBehaviorCallback() {
        View view = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

}

