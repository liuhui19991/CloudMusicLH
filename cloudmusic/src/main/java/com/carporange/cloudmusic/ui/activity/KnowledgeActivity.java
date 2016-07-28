package com.carporange.cloudmusic.ui.activity;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.knowledgemap.MapService;
import com.carporange.cloudmusic.knowledgemap.MapView;
import com.carporange.cloudmusic.knowledgemap.NodeService;
import com.carporange.cloudmusic.knowledgemap.UIUtils;

/**
 * 知识地图阅读页
 * Created by liuhui on 2016/7/25.
 */
public class KnowledgeActivity extends AppCompatActivity {
    private String resourceUrl;
    private Handler mHandler = new Handler();
    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ll = (LinearLayout) findViewById(R.id.ll);
        resourceUrl = getIntent().getStringExtra("map");
        initViews();
    }

    public void initViews() {
        // 知识地图相关
        final MapView commonMapView = (MapView) findViewById(R.id.map);

        Resources res = getResources();
        NodeService.close = BitmapFactory.decodeResource(res,
                R.mipmap.common_node_close);
        NodeService.open = BitmapFactory.decodeResource(res,
                R.mipmap.common_node_open);

        DisplayMetrics dm = UIUtils.getDisplayMetrics(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
