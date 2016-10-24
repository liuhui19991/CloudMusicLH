package com.carporange.cloudmusic.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import com.carporange.cloudmusic.event.WriteStorage;
import com.carporange.cloudmusic.listener.MyDownloadListener;
import com.carporange.cloudmusic.ui.activity.BlurredViewBasicActivity;
import com.carporange.cloudmusic.ui.activity.JsActivity;
import com.carporange.cloudmusic.ui.activity.KnowledgeActivity;
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
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.world.liuhui.utils.FileUtil;
import cn.world.liuhui.widget.NumberProgressBar;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by liuhui on 2016/6/27.
 */
public class AnchorRadioFragment extends BaseFragment implements MyAdapter.ItemClickListener {
    @BindView(R.id.et)
    EditText mEditText;
    @BindView(R.id.view_pager)
    ViewPagerCycle mViewPagerCycle;
    @BindView(R.id.progressbar)
    TextView mDownTextView;
    @BindView(R.id.down_progress)
    NumberProgressBar mNumberProgressBar;
    private BottomSheetDialog mBottomSheetDialog;
    private String SAVE_URL = FileUtil.getRootPath() + "/liuhui/";
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
        FileUtil.initDirectory(SAVE_URL);//如果没有的话需要创建文件夹
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
        DownloadQueue mDownloadQueue = NoHttp.newDownloadQueue();
        mDownloadQueue.add(0, mMDownloadRequest, downloadListener);
    }

    private DownloadListener downloadListener = new MyDownloadListener() {
        @Override
        public void onProgress(int what, int progress, long fileCount) {
            mNumberProgressBar.setProgress(progress);
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
        list.add("取消下载");
        list.add("打开知识地图");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListRecyclerAdapter adapter = new ListRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        setBehaviorCallback();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 这个Fragment所在的Activity的onRequestPermissionsResult()如果重写了，不能删除super.onRes...
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionYes(66)
    private void getExteralStorageYes() {
        L.e("拥有写内存卡权限");
        downLoad();
    }

    @PermissionNo(66)
    private void getExteralStorageNo() {
        L.e("没有获取到写内存卡权限");
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //EventBus回调在ui线程执行,这里没有用到
    public void onEvent(WriteStorage event) {
        L.e("EventBus回调");
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

    @Override
    public void onItemCclick(View v, int position) {
        switch (position) {
            case 0:
                AndPermission.with(this)
                        .requestCode(66)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)//当有写权限的时候读权限系统也会授予
                        .send();
                break;
            case 1:
                JCVideoPlayerStandard.startFullscreen(mContext, ProgressVideoPlayer.class,
                        "file://" + SAVE_URL + "123.mp4", "download");//解决弹出提示使用移动网络的问题
                break;
            case 2:
                JCVideoPlayerStandard.startFullscreen(mContext, ProgressVideoPlayer.class,
                        SAVE_URL + "123.mp4", "download");
                if (mMDownloadRequest != null) mMDownloadRequest.cancel();
                break;
            case 3:
                Intent intent = new Intent(mContext, KnowledgeActivity.class);
                intent.putExtra("map", SAVE_URL + "knowledge.json");
                intent.putExtra("down", "down");
                startActivity(intent);
                break;
            default:

                break;
        }
        if (mBottomSheetDialog != null)
            mBottomSheetDialog.dismiss();
    }
}

