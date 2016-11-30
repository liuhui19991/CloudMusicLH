//package cn.world.liuhui;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.format.Formatter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.lzy.okserver.download.DownloadInfo;
//import com.lzy.okserver.download.DownloadManager;
//import com.lzy.okserver.download.DownloadService;
//import com.lzy.okserver.listener.DownloadListener;
//import com.lzy.okserver.task.ExecutorWithListener;
//import com.world.studymobilephone.model.ResourceModel;
//import com.world.studymobilephone.ui.base.BaseBackActivity;
//import com.world.studymobilephone.ui.base.BasePresenter;
//import com.world.studymobilephone.utils.AppCacheUtil;
//import com.world.studymobilephone.utils.DialogUtil;
//import com.world.studymobilephone.utils.ImageLoaderUtil;
//import com.world.studymobilephone.utils.LogUtil;
//import com.world.studymobilephone.utils.ToastUtil;
//import com.world.studymobilephone.utils.Url;
//import com.world.studymobilephone.widget.VideoPlayer;
//
//import java.io.File;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
//import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
//
///**
// * 实现下载管理
// */
//public class DownloadManagerActivity extends BaseBackActivity implements ExecutorWithListener.OnAllTaskEndListener {
//
//    private List<DownloadInfo> allTask;
//    private DownloadManager downloadManager;
//    @BindView(R.id.listView)
//    RecyclerView mRecyclerView;
//    private DownLoadAdapter mDownLoadAdapter;
//    private ResourceModel mResourceModel;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_download_manager;
//    }
//
//    @Override
//    protected void initView() {
//        initToolBar("下载管理", true);
//        downloadManager = DownloadService.getDownloadManager();
//        allTask = downloadManager.getAllTask();
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mDownLoadAdapter = new DownLoadAdapter();
//        mRecyclerView.setAdapter(mDownLoadAdapter);
//        downloadManager.getThreadPool().getExecutor().addOnAllTaskEndListener(this);
//    }
//
//    @Override
//    protected BasePresenter initPresent() {
//        return null;
//    }
//
//    @Override
//    public void onAllTaskEnd() {
//        for (DownloadInfo downloadInfo : allTask) {
//            if (downloadInfo.getState() != DownloadManager.FINISH) {
//                //所有下载线程结束，部分下载未完成
//                return;
//            }
//        }
//        //所有下载任务完成
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //记得移除，否者会回调多次
//        downloadManager.getThreadPool().getExecutor().removeOnAllTaskEndListener(this);
//    }
//
//    @Override
//    protected void onResume() { // TODO: 2016/11/25 还不确定有没有必要
//        super.onResume();
//        mDownLoadAdapter.notifyDataSetChanged();
//    }
//
//    class DownLoadAdapter extends RecyclerView.Adapter<DownLoadAdapter.ViewRHolder> {
//        @Override
//        public ViewRHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_manager, parent, false);
//            return new ViewRHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewRHolder holder, int position) {
//            DownloadInfo downloadInfo = allTask.get(position);
//            LogUtil.e(downloadInfo.getFileName());
//            //对于非进度更新的ui放在这里，对于实时更新的进度ui，放在holder中
//            mResourceModel = (ResourceModel) AppCacheUtil.getInstance(DownloadManagerActivity.this).getObject(downloadInfo.getUrl());
//            if (mResourceModel != null) {
//                ImageLoaderUtil.displayRound(holder.icon, mResourceModel.imageUrl);
//                holder.name.setText(mResourceModel.title);
//            } else {
//                holder.name.setText(downloadInfo.getFileName());
//            }
//
//            holder.refresh(downloadInfo);
//
//            holder.download.setOnClickListener(holder);
//            holder.itemView.setOnClickListener(holder);
//            holder.itemView.setOnLongClickListener(holder);
//            DownloadListener downloadListener = new MyDownloadListener();
//            downloadListener.setUserTag(holder);
//            downloadInfo.setListener(downloadListener);
//        }
//
//        @Override
//        public int getItemCount() {
//            return allTask.size();
//        }
//
//        public class ViewRHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//            private DownloadInfo downloadInfo;
//
//            @BindView(R.id.icon)
//            ImageView icon;
//            @BindView(R.id.name)
//            TextView name;
//            @BindView(R.id.tvProgress)
//            TextView tvProgress;
//            @BindView(R.id.downloadSize)
//            TextView downloadSize;
//            @BindView(R.id.netSpeed)
//            TextView netSpeed;
//            @BindView(R.id.start)
//            TextView download;
//
//            public ViewRHolder(View convertView) {
//                super(convertView);
//                ButterKnife.bind(this, convertView);
//            }
//
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == download.getId()) {
//                    switch (downloadInfo.getState()) {
//                        case DownloadManager.PAUSE:
//                        case DownloadManager.NONE:
//                        case DownloadManager.ERROR:
//                            downloadManager.addTask(downloadInfo.getUrl(), downloadInfo.getRequest(), downloadInfo.getListener());
//                            break;
//                        case DownloadManager.DOWNLOADING:
//                            downloadManager.pauseTask(downloadInfo.getUrl());
//                            break;
//                        case DownloadManager.FINISH:
//                            playResource();
//                            break;
//                    }
//                    refresh();
//                } else {
//                    if (downloadInfo.getState() == DownloadManager.FINISH) {
//                        playResource();
//                    }
//                }
//            }
//
//            private void playResource() {
//                if (downloadInfo.getTargetPath().endsWith("json")) {//知识地图
//                    Intent intent = new Intent(mContext, KnowledgeActivity.class);
//                    intent.putExtra(Url.TYPE, downloadInfo.getTargetPath());
//                    intent.putExtra("down", "down");
//                    startActivity(intent);
//                } else if (downloadInfo.getTargetPath().endsWith("mp4")) {
//                    Uri uri = Uri.fromFile(new File(downloadInfo.getTargetPath()));
//                    String url = uri.toString();
//                    JCVideoPlayerStandard.startFullscreen(mContext, VideoPlayer.class, url, mResourceModel.title);
//                }
//            }
//
//            @Override
//            public boolean onLongClick(View v) {
//                DialogUtil.showAlert(mContext, "友情提示:", "确定删除资源吗?", "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        downloadManager.removeTask(downloadInfo.getUrl(), true);
//                        mDownLoadAdapter.notifyDataSetChanged();
//                    }
//                }, "取消", null);
//                return true;//
//            }
//
//            public void refresh(DownloadInfo downloadInfo) {
//                this.downloadInfo = downloadInfo;
//                refresh();
//            }
//
//            //对于实时更新的进度ui，放在这里，例如进度的显示，而图片加载等，不要放在这，会不停的重复回调
//            //也会导致内存泄漏
//            private void refresh() {
//                String downloadLength = Formatter.formatFileSize(DownloadManagerActivity.this, downloadInfo.getDownloadLength());
//                String totalLength = Formatter.formatFileSize(DownloadManagerActivity.this, downloadInfo.getTotalLength());
//                downloadSize.setText(downloadLength + "/" + totalLength);
//                tvProgress.setText((Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%");
//                if (downloadInfo.getState() == DownloadManager.NONE) {
//                    netSpeed.setText("停止");
//                    download.setText("下载");
//                } else if (downloadInfo.getState() == DownloadManager.PAUSE) {
//                    netSpeed.setText("暂停中");
//                    download.setText("继续");
//                } else if (downloadInfo.getState() == DownloadManager.ERROR) {
//                    netSpeed.setText("下载出错");
//                    download.setText("重试");
//                } else if (downloadInfo.getState() == DownloadManager.WAITING) {
//                    netSpeed.setText("等待中");
//                    download.setText("等待");
//                } else if (downloadInfo.getState() == DownloadManager.FINISH) {
//                    download.setText("观看");
//                    netSpeed.setText("下载完成");
//                } else if (downloadInfo.getState() == DownloadManager.DOWNLOADING) {
//                    String networkSpeed = Formatter.formatFileSize(DownloadManagerActivity.this, downloadInfo.getNetworkSpeed());
//                    netSpeed.setText(networkSpeed + "/s");
//                    download.setText("暂停");
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        JCVideoPlayer.releaseAllVideos();
//    }
//
//    private class MyDownloadListener extends DownloadListener {
//
//        @Override
//        public void onProgress(DownloadInfo downloadInfo) {
//            if (getUserTag() == null) return;
////            ViewHolder holder = (ViewHolder) getUserTag();
//            DownLoadAdapter.ViewRHolder holder = (DownLoadAdapter.ViewRHolder) getUserTag();
//            holder.refresh();  //这里不能使用传递进来的 DownloadInfo，否者会出现条目错乱的问题
//        }
//
//        @Override
//        public void onFinish(DownloadInfo downloadInfo) {
//            LogUtil.e(downloadInfo.getTargetPath());
//        }
//
//        @Override
//        public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
//            if (errorMsg != null) ToastUtil.showLong(mContext, errorMsg);
//        }
//    }
//}
