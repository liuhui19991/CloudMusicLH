package com.carporange.cloudmusic.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.event.TitleEvent;
import com.carporange.cloudmusic.fragment.MainFragment;
import com.carporange.cloudmusic.fragment.MenuLeftFragment;
import com.carporange.cloudmusic.util.DensityUtil;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.S;
import com.carporange.cloudmusic.util.SpUtil;
import com.carporange.cloudmusic.util.T;
import com.nineoldandroids.view.ViewHelper;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.world.liuhui.utils.SnackbarUtil;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by liuhui on 2016/6/27.
 */
public class MainActivity extends AppCompatActivity implements MenuLeftFragment.OnLeftClickListener, DrawerLayout.DrawerListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fragment_left)
    View mFragmentLeft;
    @BindView(R.id.tv_title)
    TextView mTitle;
    private Fragment mMainFragment;
    private int REQUEST_CODE = 88;
    private AppCompatActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isNightMode = SpUtil.getBoolean("nightMode", false);
        setTheme(isNightMode ? R.style.NightTheme : R.style.DayTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//绑定黄油刀
        mContext = this;
        initWindow();
        initViews();
        mDrawerLayout.addDrawerListener(this);
    }

    /**
     * 这个方法是让状态栏变成透明色,让窗体可以填充,下面的19意思是19版本以上此方法有用,对应的dimens(v19)
     */
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //5.0以上可以直接设置 statusbar颜色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    public void initViews() {
        EventBus.getDefault().register(this);//注册eventbus
        ViewGroup.LayoutParams layoutParams = mFragmentLeft.getLayoutParams();
        layoutParams.width = DensityUtil.getDisplayWidth(this) * 4 / 5;
        FragmentManager fm = getSupportFragmentManager();
        if (mMainFragment == null) {
            mMainFragment = new MainFragment();
        }
        fm.beginTransaction().replace(R.id.framelayout, mMainFragment).commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(TitleEvent event) {
        mTitle.setText(event.getmTitle());
    }

    /*   @Subscribe(threadMode = ThreadMode.MAIN) //使用EventBus进行点击退出应用的事件传递
       public void onEvent(ExitEvent event) {
           mDrawerLayout.closeDrawer(Gravity.LEFT);
           S.show(this, "退出程序点击了");
           T.showShort(this, "退出程序点击了");
       }
   */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 为了个侧边栏回调创建的方法
     *
     * @return
     */
    public DrawerLayout getDrawerLayout() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        return mDrawerLayout;
    }

    /**
     * 开启侧边栏
     */
    @OnClick(R.id.actionbar_home)
    public void openDrawerLayout() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    /**
     * 扫描二维码
     */
    @OnClick(R.id.actionbar_search)
    public void scanMessage() {
        requestLocationPermission();
        /*if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {//没有权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                scannerTwo();
            }
        } else {
            scannerTwo();
        }*/
    }

    private void requestLocationPermission() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA)
                .rationale(rationaleListener)
                .send();
    }

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(mContext)
                    .setTitle("友好提醒")
                    .setMessage("您已拒绝过相机权限，没有相机权限无法为您拍照，请把拍照权限赐给我吧！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };

    private void scannerTwo() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @PermissionYes(100)
    private void getCameraYes() {
        scannerTwo();
        L.e("获取到相机权限");
    }

    @PermissionNo(100)
    private void getCameraNo() {
        L.e("没有获取到相机权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);//如果这个Activity中没有Fragment，这句话可以注释。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

       /* switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scannerTwo();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case BAIDU_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    startActivity(new Intent(mContext, BaiduMapActivity.class));
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent(mContext, WebPageActivity.class);
                    intent.putExtra("url", result);
                    startActivity(intent);
                    T.showShort(mContext, "解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    T.showShort(mContext, "解析二维码失败");
                }
            }
        }
    }

    @Override
    public void onLeftClick(int what) {//此处是将侧边栏按钮事件,在activity里面处理,  也可学习点击设置按钮时候的处理方式
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        switch (what) {
            case 0:
                S.show(mContext, "退出程序点击了");
                T.showShort(mContext, "退出程序点击了");
                break;
            case 1:
                S.show(mContext, "设置");
                T.showShort(mContext, "设置");
                break;
            default:

                break;
        }

    }

/*     long time = 0;
   @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time > 2000) {
            T.showShort(this, "再按一次退出程序");
            time = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }*/

    long time = 0;

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        if (System.currentTimeMillis() - time > 2000) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return;
            }
            time = System.currentTimeMillis();
            T.showShort(this, "小样你还想退出吗?" + "\n" + "再按一次返回确认退出");
            SnackbarUtil.showWithClick(mTitle, "小样你还想退出吗?", "点我也能退出", mOnClickListener);
        } else {
            finish();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.exit(0);
        }
    };

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        View mContent = mDrawerLayout.getChildAt(0);
        View mMenu = drawerView;
        ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * slideOffset);//此处引用nineoldandroids库来完成动画
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
