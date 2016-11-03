package com.carporange.cloudmusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.carporange.cloudmusic.R;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;

import cn.world.liuhui.utils.FileUtil;
import cn.world.liuhui.utils.ToastUtil;

/**
 * Created by liuhui on 2016/11/3.
 */
public class DownloadService extends Service {
    private String SAVE_URL = FileUtil.getRootPath() + "/liuhui/";
    private String url;//下载链接
    private long length;//文件长度
    private String fileName = "abc.apk";//文件名
    private Notification notification;
    private RemoteViews contentView;
    private NotificationManager notificationManager;

    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            url = intent.getStringExtra("url");
            if (url != null && !TextUtils.isEmpty(url)) {
                FileUtil.initDirectory(SAVE_URL);//如果没有的话需要创建文件夹
                DownloadQueue downloadQueue = NoHttp.newDownloadQueue(2);
                DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, SAVE_URL, fileName, true, true);
                downloadQueue.add(0, downloadRequest, downloadListener);
            } else {
                ToastUtil.show(this, "下载地址错误");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载监听
     */
    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            length = allCount;
            createNotification();
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            notifyNotification(fileCount, length);
        }

        @Override
        public void onFinish(int what, String filePath) {
            Logger.e("Download finish, file path: " + filePath);
            ToastUtil.show(DownloadService.this, "连接失败，请检查网络设置");
            installApk(DownloadService.this, new File(SAVE_URL, fileName));
        }

        @Override
        public void onCancel(int what) {
        }

    };

    @SuppressWarnings("deprecation")
    public void createNotification() {
        notification = new Notification(
                R.mipmap.ic_launcher,//应用的图标
                "安装包正在下载...",
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        /*** 自定义  Notification 的显示****/
        contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setProgressBar(R.id.progress, 100, 0, false);
        contentView.setTextViewText(R.id.tv_progress, "0%");
        notification.contentView = contentView;

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(R.layout.notification_item, notification);
    }

    private void notifyNotification(long percent, long length) {
        contentView.setTextViewText(R.id.tv_progress, (percent * 100 / length) + "%");
        contentView.setProgressBar(R.id.progress, (int) length, (int) percent, false);
        notification.contentView = contentView;
        notificationManager.notify(R.layout.notification_item, notification);
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
