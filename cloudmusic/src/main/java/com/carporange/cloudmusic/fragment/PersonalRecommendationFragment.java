package com.carporange.cloudmusic.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.service.DownloadService;
import com.carporange.cloudmusic.ui.activity.MusicActivity;
import com.carporange.cloudmusic.ui.activity.PhonePersion;
import com.carporange.cloudmusic.ui.activity.RefreshAnimationActivity;
import com.carporange.cloudmusic.ui.activity.UniversalActivity;
import com.carporange.cloudmusic.ui.activity.WebAndListViewActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.GlideUtil;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.T;
import com.carporange.cloudmusic.widget.CircleImageView;
import com.carporange.cloudmusic.widget.ProgressPieView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okserver.download.DownloadManager;
import com.lzy.okserver.upload.UploadInfo;
import com.lzy.okserver.upload.UploadManager;
import com.lzy.widget.ExpandGridView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.world.liuhui.utils.DialogUtil;
import cn.world.liuhui.utils.ToastUtil;
import cn.world.liuhui.widget.SelectPicPopupWindow;

/**
 * Created by liuhui on 2016/6/27.
 */
public class PersonalRecommendationFragment extends BaseFragment {
    private Dialog mWaitDialog;
    private boolean canCancel = true;
    private final String APK_URL = "http://surveyapp.fy.chaoxing.com/app/LauncherDemo5.apk";
    private Button top, bottom;
    private PopupWindow mPopupWindow;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images;
    @BindView(R.id.photo)
    CircleImageView mCircleImageView;
    @BindView(R.id.gridView)
    ExpandGridView gridView;
    private SelectPicPopupWindow mSelectPicPopupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_recommendation;
    }

    private void initWaitDialog() {//展示我自定义的等待对话框
        if (mWaitDialog == null) {
            mWaitDialog = new Dialog(mContext, R.style.TRANSDIALOG);
        }
        mWaitDialog.setContentView(R.layout.trans_dialog);
        mWaitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        mWaitDialog.setCancelable(canCancel);
        if (mWaitDialog != null) {
            mWaitDialog.show();
        }
    }

    @Override
    protected void initViews() {
        top = findView(R.id.top);
        bottom = findView(R.id.bottom);
//        initWaitDialog();  //现在这个方法执行的时候会出现创建Dialog时候空指针
    }


    /**
     * 显示dialog的按钮点击事件
     */
    @OnClick(R.id.btn_bottom_dialog_control)
    void dialog() {

    }

    @OnClick({R.id.refresh, R.id.top, R.id.bottom, R.id.universaladapter, R.id.phone_persion, R.id.update,
            R.id.music, R.id.wvandlv, R.id.photo, R.id.photo_album})
    void click(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                startActivity(new Intent(mContext, RefreshAnimationActivity.class));
                break;
            case R.id.top:
                showPopupWindow(top);
                break;
            case R.id.bottom:
                showPopupWindow(bottom);
                break;
            case R.id.universaladapter:
                startActivity(new Intent(mContext, UniversalActivity.class));
                break;
            case R.id.phone_persion:
                startActivity(new Intent(mContext, PhonePersion.class));
                break;
            case R.id.wvandlv:
                startActivity(new Intent(mContext, WebAndListViewActivity.class));
                break;
            case R.id.update:
                DialogUtil.showAlert(mContext, "更新软件", "这是一个新的版本", "立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(mContext, DownloadService.class);
                        intent.putExtra("url", APK_URL);
                        mContext.startService(intent);
                    }
                }, "下次再说", null);
                break;
            case R.id.music:
//                http://65res.gbxxzyzx.com/audio/songs/2016/7/14/20160714070713096.mp3
                startActivity(new Intent(mContext, MusicActivity.class));
                break;
            case R.id.photo:
                mSelectPicPopupWindow = new SelectPicPopupWindow(mContext, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btn_pick_photo:
                                gallery();
                                break;
                            case R.id.btn_take_photo:
                                camera();
                                break;
                        }
                    }
                });
                mSelectPicPopupWindow.showAtLocation(mContext.findViewById(R.id.sc), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                /*Intent intent1 = new Intent(
                        Intent.ACTION_PICK);
                intent1.setType("image*//*");
                startActivityForResult(intent1, PHOTO_REQUEST_GALLERY);*/
                break;
            case R.id.photo_album:
                imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideUtil());
                imagePicker.setShowCamera(true);
                imagePicker.setCrop(true);        //允许裁剪（单选才有效）
                imagePicker.setSaveRectangle(true); //是否按矩形区域保存
                imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
                imagePicker.setSelectLimit(9);
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    /**
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 从相机获取
     */
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else return false;
    }

    /**
     * @param v 显示在这个控件的相对位置
     */
    private void showPopupWindow(View v) {
        L.e("hello, %s", "world");//, %s这样才能打印出分开的逗号
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popwin_view, null);
        view.findViewById(R.id.text_size_big).setOnClickListener(onclick);
        view.findViewById(R.id.text_size_small).setOnClickListener(onclick);
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//要想下面测量出高度必须添加这句话
        int high = view.getMeasuredHeight() + v.getHeight();
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);//默认动画为缩放动画
        mPopupWindow.showAsDropDown(v, 0, -high);// 保证尺寸是根据屏幕像素密度来的,显示的控件上方
//        mPopupWindow.showAsDropDown(v);//显示在控件下方
        // 使其聚集
        mPopupWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        // 刷新状态
        mPopupWindow.update();
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.text_size_big:
                    T.showShort(mContext, "大字体");
                    break;
                case R.id.text_size_small:
                    T.showShort(mContext, "小字体");
                    break;
                default:

                    break;
            }
        }
    };
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";//头像名称
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile;
    private Bitmap bitmap;

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 上传头像
     *
     * @param bm
     */
    private void sendImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        InputStream sbs = new ByteArrayInputStream(bytes);//在这里把流上传到服务器
        ToastUtil.show(mContext, "上传头像");
        mCircleImageView.setImageBitmap(bm);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                MyAdapter adapter = new MyAdapter(images);
                gridView.setAdapter(adapter);
            } else Toast.makeText(mContext, "没有数据", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            } else mSelectPicPopupWindow.dismiss();
        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                mSelectPicPopupWindow.dismiss();
                ToastUtil.show(mContext, "未找到存储卡，无法存储照片！");
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                mSelectPicPopupWindow.dismiss();//在此处为选择图片正确使用后关闭
                bitmap = data.getParcelableExtra("data");
                if (null != tempFile) {
                    boolean delete = tempFile.delete();
                }
                sendImage(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class MyAdapter extends BaseAdapter {

        private List<ImageItem> items;

        public MyAdapter(List<ImageItem> items) {
            this.items = items;
        }

        public void setData(List<ImageItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public ImageItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int size = gridView.getWidth() / 3;
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_upload_manager, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imagePicker.getImageLoader().displayImage(mContext, getItem(position).path, holder.imageView, size, size);
            return convertView;
        }
    }

    private class ViewHolder {

        private ImageView imageView;
        private TextView tvProgress;
        private ProgressPieView civ;
        private View mask;

        public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.imageView);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridView.getWidth() / 3);
            imageView.setLayoutParams(params);
            tvProgress = (TextView) convertView.findViewById(R.id.tvProgress);
            mask = convertView.findViewById(R.id.mask);
            civ = (ProgressPieView) convertView.findViewById(R.id.civ);
            tvProgress.setText("请上传");
            civ.setText("请上传");
        }

        public void refresh(UploadInfo uploadInfo) {
            if (uploadInfo.getState() == DownloadManager.NONE) {
                tvProgress.setText("请上传");
                civ.setText("请上传");
            } else if (uploadInfo.getState() == UploadManager.ERROR) {
                tvProgress.setText("上传出错");
                civ.setText("错误");
            } else if (uploadInfo.getState() == UploadManager.WAITING) {
                tvProgress.setText("等待中");
                civ.setText("等待");
            } else if (uploadInfo.getState() == UploadManager.FINISH) {
                tvProgress.setText("上传成功");
                civ.setText("成功");
            } else if (uploadInfo.getState() == UploadManager.UPLOADING) {
                tvProgress.setText("上传中");
                civ.setProgress((int) (uploadInfo.getProgress() * 100));
                civ.setText((Math.round(uploadInfo.getProgress() * 10000) * 1.0f / 100) + "%");
            }
        }

        public void finish() {
            tvProgress.setText("上传成功");
            civ.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);
        }
    }
}
