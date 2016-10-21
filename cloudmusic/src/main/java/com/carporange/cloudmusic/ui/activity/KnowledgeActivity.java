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
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.L;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.OnClick;
import cn.world.liuhui.utils.FileUtil;
import cn.world.liuhui.utils.ToastUtil;

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
            service.startEngineFromString(startFromDownload(resourceUrl));
        } else
            service.startEngineFromUrl(resourceUrl);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll.setVisibility(View.GONE);
                commonMapView.setVisibility(View.VISIBLE);
            }
        }, 1500);
        File file = new File(SAVE_URL);
        if (!file.exists()) {//如果没有的话需要创建文件夹
            file.mkdir();
        }
    }

    @OnClick(R.id.tv_down)
    void downLoadKnowledge() {
        L.e("点击下载");
        DownloadRequest mMDownloadRequest = NoHttp.createDownloadRequest(resourceUrl, SAVE_URL, "knowledge.json", true, true);
        DownloadQueue mDownloadQueue = NoHttp.newDownloadQueue();
        mDownloadQueue.add(0, mMDownloadRequest, downloadListener);
    }

    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onDownloadError(int what, Exception exception) {

        }

        @Override
        public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
            ToastUtil.show(mContext, "开始下载");
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {

        }

        @Override
        public void onFinish(int what, String filePath) {
            ToastUtil.show(mContext, "知识地图下载完成");
        }

        @Override
        public void onCancel(int what) {

        }
    };

    @Override
    protected void onDestroy() {

        if (NodeService.close != null) {
            NodeService.close.recycle();
        }

        if (NodeService.open != null) {
            NodeService.open.recycle();
        }
        super.onDestroy();
    }

    private String startFromDownload(String resource) {
        InputStream is = null;
        StringBuffer sb = null;
        try {
            is = new FileInputStream(resource);
            int count = 0;
            while (count == 0) {
                count = is.available();
            }
            byte[] b = new byte[count];
            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount < count) {
                readCount += is.read(b, readCount, count - readCount);
            }
            sb = new StringBuffer();
            sb.append(new String(b, 0, readCount));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
