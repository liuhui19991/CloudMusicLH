package cn.world.liuhui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

import cn.world.liuhui.R;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by liuhui on 2016/11/11.
 */

public abstract class MyStringCallback extends StringCallback {
    private Dialog mLoadingDialog;
    private Activity mActivity;

    public MyStringCallback(Activity activity) {
        mActivity = activity;
        initLoading();
    }

    private void initLoading() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText("加载中...");
        if (mLoadingDialog != null) return;
        mLoadingDialog = new Dialog(mActivity, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        //网络请求结束后关闭对话框
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        ToastUtil.showToastWithImg(mActivity, e.toString(), 0);
    }
}
