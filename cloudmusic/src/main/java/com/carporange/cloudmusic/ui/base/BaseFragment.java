package com.carporange.cloudmusic.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by liuhui on 2016/6/27.
 */
public abstract class BaseFragment extends Fragment {
    protected View mContentView;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false);
        }
        if (mContentView.getParent() != null) {//重要
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            parent.removeView(mContentView);
        }
        ButterKnife.bind(this, mContentView);
        initViews();
        setListeners();
        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 遵循ViewPager预加载
     */
    public void onInvisible() {

    }

    /**
     * 当前fragment可见时候加载
     */
    protected abstract void onVisible();

    protected void setListeners() {
    }

    protected void initViews() {

    }

    protected abstract int getLayoutId();

    protected <T> T findView(int viewId) {
        View view = mContentView.findViewById(viewId);
        if (view != null) {
            return ((T) view);
        }
        return null;
    }
}
