package com.carporange.cloudmusic.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.fragment.MainFragment;
import com.carporange.cloudmusic.fragment.MenuLeftFragment;
import com.carporange.cloudmusic.ui.base.BaseActivity;
import com.carporange.cloudmusic.util.DensityUtil;
import com.carporange.cloudmusic.util.L;
import com.carporange.cloudmusic.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuhui on 2016/6/27.
 */
public class MainActivity extends BaseActivity implements MenuLeftFragment.OnLeftClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fragment_left)
    View mFragmentLeft;
    private Fragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ViewGroup.LayoutParams layoutParams = mFragmentLeft.getLayoutParams();
        layoutParams.width = DensityUtil.getDisplayWidth(this) * 4 / 5;
        FragmentManager fm = getSupportFragmentManager();
        if (mMainFragment == null) {
            mMainFragment = new MainFragment();
        }
        fm.beginTransaction().replace(R.id.framelayout, mMainFragment).commit();
    }

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
    public void onLeftClick() {////此处是将侧边栏按钮事件,在activity里面处理,  也可学习点击设置按钮时候的处理方式
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        L.e("侧边栏传来的退出");
        T.showShort(this, "退出程序点击了");
    }

    long time = 0;
/*    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time > 2000) {
            T.showShort(this, "再按一次退出程序");
            time = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }*/

    @Override
    public void onBackPressed() {//此处要把super.onBackPressed()去掉,要不然还是执行父类的退出
        T.showShort(this, "小样你还想退出吗?");
    }
}
