package com.carporange.cloudmusic.fragment;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carporange.cloudmusic.R;

public class SimpleFragment extends Fragment {
    private TextView mTvTitle;
    private View mFakeStatusBar;
    protected View mContentView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false);
        }
        if (mContentView.getParent() != null) {//重要
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            parent.removeView(mContentView);
        }
        return mContentView;
    }

    private int getLayoutId() {
        return R.layout.fragement_simple;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mFakeStatusBar = view.findViewById(R.id.fake_status_bar);
        final DrawerLayout md = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        md.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//设置不可滑动
        TextView tev = (TextView) view.findViewById(R.id.open);
        tev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (md.isDrawerOpen(md)) {
//                    Toast.makeText(getContext(), "yida开", Toast.LENGTH_SHORT).show();
//                }
                md.openDrawer(Gravity.LEFT);
            }
        });
    }

    public void setTvTitleBackgroundColor(@ColorInt int color) {
        mTvTitle.setBackgroundColor(color);
        mFakeStatusBar.setBackgroundColor(color);
    }
}
