package com.carporange.cloudmusic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.event.TitleEvent;
import com.carporange.cloudmusic.fragment.MainFragment;
import com.carporange.cloudmusic.fragment.MenuLeftFragment;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.DensityUtil;
import com.carporange.cloudmusic.util.S;
import com.carporange.cloudmusic.util.T;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by liuhui on 2016/6/27.
 */
public class MainActivity extends BaseActivity implements MenuLeftFragment.OnLeftClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fragment_left)
    View mFragmentLeft;
    @BindView(R.id.tv_title)
    TextView mTitle;
    private Fragment mMainFragment;
    private int REQUEST_CODE = 88;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
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
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
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
                    T.showShort(mContext, "解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    T.showShort(mContext, "解析二维码失败");
                }
            }
        }
    }

    @Override
    public void initActionBar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        ab.setHomeAsUpIndicator(R.drawable.actionbar_menu);
//        ab.setDisplayHomeAsUpEnabled(true);
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
    public void onBackPressed() {//此处要把super.onBackPressed()去掉,要不然还是执行父类的退出
        if (JCVideoPlayer.backPress()) {
            return;
        }
        T.showShort(this, "小样你还想退出吗?"+"\n"+"再按一次返回确认退出");
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            Snackbar.make(mTitle, "小样你还想退出吗?", Snackbar.LENGTH_LONG).setAction("click", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.exit(0);
                }
            }).show();
        } else {
            finish();
        }
    }
}
