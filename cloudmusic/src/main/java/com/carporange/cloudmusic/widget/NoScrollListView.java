package com.carporange.cloudmusic.widget;

import android.content.Context;
import android.widget.ListView;


/**
 * 取消滚动的ListView
 * Created by liuhui on 2016/6/15.
 */

public class NoScrollListView extends ListView {

    public NoScrollListView(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}  
