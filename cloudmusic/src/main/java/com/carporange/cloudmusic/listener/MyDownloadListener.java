package com.carporange.cloudmusic.listener;

import com.carporange.cloudmusic.CarpApplication;
import com.carporange.cloudmusic.util.L;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.download.DownloadListener;

import cn.world.liuhui.utils.ToastUtil;

/**
 * Created by liuhui on 2016/10/21.
 */

public abstract class MyDownloadListener implements DownloadListener {
    @Override
    public void onDownloadError(int what, Exception exception) {
        ToastUtil.show(CarpApplication.getInstance(), "下载错误");
    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
        ToastUtil.show(CarpApplication.getInstance(), "开始下载");
    }

    @Override
    public void onProgress(int what, int progress, long fileCount) {

    }

    @Override
    public void onFinish(int what, String filePath) {
        L.e("文件下载地址:" + filePath);
        ToastUtil.show(CarpApplication.getInstance(), "下载完成");
    }

    @Override
    public void onCancel(int what) {

    }
}
