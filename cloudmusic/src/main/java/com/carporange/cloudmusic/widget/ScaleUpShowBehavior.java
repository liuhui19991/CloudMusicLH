package com.carporange.cloudmusic.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.carporange.cloudmusic.event.ExitEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * <p>上拉时显示FAB。</p>
 * Created by liuhui on 2016/6/15.
 *
 * @author liuhui.
 */
public class ScaleUpShowBehavior extends FloatingActionButton.Behavior {

    private boolean isAnimatingOut = false;

    public ScaleUpShowBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        System.out.println("onNestedScroll- dxConsumed:" + dxConsumed + "; dyConsumed: " + dyConsumed + "; dxUnconsumed: " + dxUnconsumed + "; dyUnconsumed: " + dyUnconsumed);

//        if (dyConsumed > 0 && dyUnconsumed == 0) {
//            System.out.println("上滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed > 0) {
//            System.out.println("到边界了还在上滑。。。");
//        }
//        if (dyConsumed < 0 && dyUnconsumed == 0) {
//            System.out.println("下滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed < 0) {
//            System.out.println("到边界了，还在下滑。。。");
//        }

        if (((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed > 0)) && child.getVisibility() == View.VISIBLE) {// 隐藏
            child.hide();
            EventBus.getDefault().post(new ExitEvent(true));
        } else if (((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed < 0)) && child.getVisibility() != View.VISIBLE) {
            child.show();
            EventBus.getDefault().post(new ExitEvent(false));
        }
//        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
////            child.hide();
//            RxBus.getInstance().post(AppConstant.MENU_SHOW_HIDE,false);
//        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
//            RxBus.getInstance().post(AppConstant.MENU_SHOW_HIDE,true);
////            child.show();
//        }
    }
}
