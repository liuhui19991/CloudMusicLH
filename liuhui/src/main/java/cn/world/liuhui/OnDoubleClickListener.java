package cn.world.liuhui;

import android.view.View;

/**
 * 双击事件
 * Created by liuhui on 2015/7/11.
 */
public abstract class OnDoubleClickListener implements View.OnClickListener {
    private int count = 0;
    private long firClick = 0;
    private long secClick = 0;

    @Override
    public void onClick(View v) {
        count++;
        if (count == 1) {
            firClick = System.currentTimeMillis();

        } else if (count == 2) {
            secClick = System.currentTimeMillis();
            if (secClick - firClick < 1000) {
                //双击事件
                onDoubleClick(v);
            }
            count = 0;
            firClick = 0;
            secClick = 0;
        }
    }

    protected abstract void onDoubleClick(View v);
}