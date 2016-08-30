package com.carporange.cloudmusic.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.event.TitleEvent;
import com.carporange.cloudmusic.ui.activity.JsActivity;
import com.carporange.cloudmusic.ui.activity.RefreshLoadMoreActivity;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.SwitchUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class RankingListFragment extends BaseFragment {

    private LinearLayout ll;
    private Handler handler = new Handler();
    @BindView(R.id.popup)
    TextView popup;
    @BindView(R.id.jsactivity)
    TextView jsactivity;

    public RankingListFragment() {
        // Required empty public constructor

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ranking_list;
    }

    @Override
    protected void onVisible() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll.setVisibility(View.GONE);
            }
        }, 2000);
        EventBus.getDefault().post(new TitleEvent("排行榜"));
    }

    @Override
    public void initViews() {
        ll = (LinearLayout) mContentView.findViewById(R.id.progress);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });
        jsactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JsActivity.class));
            }
        });
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(getContext()).inflate(R.layout.poplayout, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(getActivity().findViewById(R.id.popup),
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        Button first = (Button) view.findViewById(R.id.first);
        first.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (window != null) {
                    window.dismiss();//隐藏popupwindow
                }
                System.out.println("第一个按钮被点击了");
                startActivity(new Intent(getActivity(), RefreshLoadMoreActivity.class));
            }
        });
        Button second = (Button) view.findViewById(R.id.second);
        second.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                System.out.println("第二个按钮被点击了");
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }
}
