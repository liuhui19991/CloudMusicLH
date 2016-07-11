package com.carporange.cloudmusic.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.adapter.MenuLeftRvAdapter;
import com.carporange.cloudmusic.ui.activity.MainActivity;
import com.carporange.cloudmusic.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuhui on 2016/6/27.
 */
public class MenuLeftFragment extends Fragment {//这个类在布局文件中绑定了类名

    private RecyclerView mRecyclerView;
    private View mContentView;
    public OnLeftClickListener mOnLeftClickListener;

    public MenuLeftFragment() {

    }

    /* @Override
     public void onAttach(Context context) {
         super.onAttach(context);
     mOnHomeClickListener = (OnLeftClickListener) context;  //
     }*/
    @Override
    public void onDetach() {
        super.onDetach();
        mOnLeftClickListener = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnLeftClickListener = (OnLeftClickListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_menu_left, container, false);
            ButterKnife.bind(this, mContentView);//绑定黄油刀
            initViews();
        }
        return mContentView;
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recycler_view);
        List<MenuLeftRvAdapter.BaseMenuItem> menuItemList = new ArrayList<>();
        MenuLeftRvAdapter.AvatarMenuItem avatarMenuItem = MenuLeftRvAdapter.MenuItemFactory.createAvatarMenu();
        avatarMenuItem.setName("李昱辰");
        avatarMenuItem.setAvatarPath("http://www.45dgdfg.com");
        menuItemList.add(avatarMenuItem);
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("我的消息"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("积分商城"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("付费音乐包"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("在线听歌免流量"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createDividerrMenu());
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("听歌识曲"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("主题换肤"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextSwitchMenu("夜间模式", SpUtil.getBoolean("nightMode", false)));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("定时停止播放"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("音乐闹钟"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("驾驶模式"));
        menuItemList.add(MenuLeftRvAdapter.MenuItemFactory.createImageTextMenu("我的音乐云盘"));
        MenuLeftRvAdapter adapter = new MenuLeftRvAdapter(menuItemList);
        adapter.setOnSwitchListener(new MenuLeftRvAdapter.OnSwitchListener() {
            @Override
            public void onSwitch(boolean isChecked) {
                SpUtil.put("nightMode", isChecked);
                getActivity().recreate();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.tv_setting)
    public void mySetting() {//设置按钮的点击事件执行的方法
        MainActivity mainActivity = (MainActivity) getActivity();
        DrawerLayout drawerLayout = mainActivity.getDrawerLayout();
        drawerLayout.closeDrawer(Gravity.LEFT);
        Toast.makeText(mainActivity, "设置", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.exit)
    public void myExit(View v) {//退出按钮的点击事件执行的方法,这里的参数可以不写
        mOnLeftClickListener.onLeftClick();
    }

    public interface OnLeftClickListener {
        void onLeftClick();
    }
}
