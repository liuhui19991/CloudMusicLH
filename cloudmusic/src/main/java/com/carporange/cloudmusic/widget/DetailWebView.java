package com.carporange.cloudmusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/1/19
 */

public class DetailWebView extends WebView {

    private static final String TAG="CustomWebView";
    public DetailWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //setOverScrollMode(OVER_SCROLL_NEVER);
    }

}
