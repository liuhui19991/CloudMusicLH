package com.carporange.cloudmusic.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.event.ExitEvent;
import com.carporange.cloudmusic.event.TitleEvent;
import com.carporange.cloudmusic.fragment.MainFragment;
import com.carporange.cloudmusic.fragment.MenuLeftFragment;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.DensityUtil;
import com.carporange.cloudmusic.util.S;
import com.carporange.cloudmusic.util.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

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
    public void onLeftClick() {//此处是将侧边栏按钮事件,在activity里面处理,  也可学习点击设置按钮时候的处理方式
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        S.show(this, "退出程序点击了");
        T.showShort(this, "退出程序点击了");
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
        T.showShort(this, "小样你还想退出吗?");
        if (System.currentTimeMillis() - time > 2000) {
            T.showShort(MainActivity.this, "再按一次返回确认退出");
            time = System.currentTimeMillis();
        } else {
            Snackbar.make(mTitle, "小样你还想退出吗?", Snackbar.LENGTH_LONG).setAction("click", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.exit(0);
                }
            }).show();
        }
    }
}
