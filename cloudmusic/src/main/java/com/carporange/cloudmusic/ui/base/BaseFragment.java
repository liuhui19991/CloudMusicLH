package com.carporange.cloudmusic.ui.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.carporange.cloudmusic.ui.WaitDialog;

/**
 * Created by liuhui on 2016/6/27.
 */
public class BaseFragment extends Fragment {
//    private WaitDialog waitDialog;
    protected View mContentView;

    protected <T> T findView(int viewId) {
        View view = mContentView.findViewById(viewId);
        if (view != null) {
            return ((T) view);
        }
        return null;
    }
   /* @Override
    public void onResume() {
        super.onResume();
        waitDialog = new WaitDialog(getActivity());
        waitDialog.show();
    }*/
}
