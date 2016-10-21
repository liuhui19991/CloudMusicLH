package com.carporange.cloudmusic.ui.activity;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.knowledgemap.MapService;
import com.carporange.cloudmusic.knowledgemap.MapView;
import com.carporange.cloudmusic.knowledgemap.NodeService;
import com.carporange.cloudmusic.knowledgemap.UIUtils;
import com.carporange.cloudmusic.listener.MyDownloadListener;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import butterknife.OnClick;
import cn.world.liuhui.utils.FileUtil;

/**
 * 知识地图阅读页
 * Created by liuhui on 2016/7/25.
 */
public class KnowledgeActivity extends BaseActivity {
    private String resourceUrl;
    private Handler mHandler = new Handler();
    private LinearLayout ll;
    private String SAVE_URL = FileUtil.getRootPath() + "/liuhui/";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge;
    }

    @Override
    public void initViews() {
        ll = (LinearLayout) findViewById(R.id.ll);
        // 知识地图相关
        final MapView commonMapView = (MapView) findViewById(R.id.map);

        Resources res = getResources();
        NodeService.close = BitmapFactory.decodeResource(res,
                R.mipmap.common_node_close);
        NodeService.open = BitmapFactory.decodeResource(res,
                R.mipmap.common_node_open);

        DisplayMetrics dm = UIUtils.getDisplayMetrics(mContext);
        final MapService service = new MapService(commonMapView, null, dm);
        resourceUrl = mContext.getIntent().getStringExtra("map");
        if ("down".equals(getIntent().getStringExtra("down"))) {//下载文件打开
            service.startEngineFromString(FileUtil.getFileUTF8(resourceUrl));
        } else service.startEngineFromUrl(resourceUrl);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {//此处为了让效果更好所以让知识地图延时显示
                ll.setVisibility(View.GONE);
                commonMapView.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }

    @OnClick(R.id.tv_down)
    void downLoadKnowledge() {
        DownloadRequest mMDownloadRequest = NoHttp.createDownloadRequest(resourceUrl, SAVE_URL, "knowledge.json", true, true);
        DownloadQueue mDownloadQueue = NoHttp.newDownloadQueue();
        mDownloadQueue.add(0, mMDownloadRequest, downloadListener);
    }

    private DownloadListener downloadListener = new MyDownloadListener() {
    };

    @Override
    protected void onDestroy() {
        if (NodeService.close != null) NodeService.close.recycle();
        if (NodeService.open != null) NodeService.open.recycle();
        super.onDestroy();
    }
}
