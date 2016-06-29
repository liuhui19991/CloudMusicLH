package com.carporange.cloudmusic.ui.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by liuhui on 2016/6/27.
 */
public class BaseFragment extends Fragment {
    protected View mContentView;

    protected <T> T findView(int viewId) {
        View view = mContentView.findViewById(viewId);
        if (view != null) {
            return ((T) view);
        }
        return null;
    }
}
