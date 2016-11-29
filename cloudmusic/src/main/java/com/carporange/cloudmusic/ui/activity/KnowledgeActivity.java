package com.carporange.cloudmusic.ui.activity;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.knowledgemap.MapService;
import com.carporange.cloudmusic.knowledgemap.MapView;
import com.carporange.cloudmusic.knowledgemap.NodeService;
import com.carporange.cloudmusic.knowledgemap.UIUtils;
import com.carporange.cloudmusic.listener.MyDownloadListener;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.L;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import butterknife.OnClick;
import cn.world.liuhui.utils.AppCacheUtil;
import cn.world.liuhui.utils.FileUtil;

/**
 * 知识地图阅读页
 * Created by liuhui on 2016/7/25.
 */
public class KnowledgeActivity extends BaseActivity {
    private String resourceUrl;
    private String SAVE_URL = FileUtil.getRootPath() + "/liuhui/";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge;
    }

    @Override
    public void initViews() {
        // 知识地图相关
        MapView commonMapView = (MapView) findViewById(R.id.map);

        Resources res = getResources();
        NodeService.close = BitmapFactory.decodeResource(res, R.mipmap.common_node_close);
        NodeService.open = BitmapFactory.decodeResource(res, R.mipmap.common_node_open);

        DisplayMetrics dm = UIUtils.getDisplayMetrics(mContext);
        MapService service = new MapService(commonMapView, null, dm);

        resourceUrl = getIntent().getStringExtra("map");
        if ("down".equals(getIntent().getStringExtra("down"))) {//下载文件打开
            service.startEngineFromString(FileUtil.getFileUTF8(resourceUrl));
        } else service.startEngineFromUrl(resourceUrl);
        L.e("缓存" + AppCacheUtil.getInstance(mContext).getString("liuhui"));//这里的缓存是登录页设置的
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
