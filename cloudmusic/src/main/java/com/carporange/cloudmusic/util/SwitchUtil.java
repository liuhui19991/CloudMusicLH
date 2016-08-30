package com.carporange.cloudmusic.util;

import android.app.Activity;

import com.carporange.cloudmusic.R;

/**
 * Created by liuhui on 2016/8/30.
 */
public class SwitchUtil {
    public static void start(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void finish(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
