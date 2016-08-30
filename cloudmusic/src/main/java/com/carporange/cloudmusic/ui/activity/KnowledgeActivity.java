package com.carporange.cloudmusic.ui.activity;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.knowledgemap.MapService;
import com.carporange.cloudmusic.knowledgemap.MapView;
import com.carporange.cloudmusic.knowledgemap.NodeService;
import com.carporange.cloudmusic.knowledgemap.UIUtils;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.SwitchUtil;

/**
 * 知识地图阅读页
 * Created by liuhui on 2016/7/25.
 */
public class KnowledgeActivity extends BaseActivity {
    private String resourceUrl;
    private Handler mHandler = new Handler();
    private LinearLayout ll;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge;
    }

    @Override
    public void initActionBar() {
        Toolbar toolbar = findView(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initViews() {
        ll = (LinearLayout) findViewById(R.id.ll);
        resourceUrl = mContext.getIntent().getStringExtra("map");
        // 知识地图相关
        final MapView commonMapView = (MapView) findViewById(R.id.map);

        Resources res = getResources();
        NodeService.close = BitmapFactory.decodeResource(res,
                R.mipmap.common_node_close);
        NodeService.open = BitmapFactory.decodeResource(res,
                R.mipmap.common_node_open);

        DisplayMetrics dm = UIUtils.getDisplayMetrics(mContext);
        final MapService service = new MapService(commonMapView, null, dm);
        service.startEngineFromUrl(resourceUrl);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll.setVisibility(View.GONE);
                commonMapView.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }

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
}
