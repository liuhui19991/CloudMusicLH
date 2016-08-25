package com.carporange.cloudmusic.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import com.carporange.cloudmusic.R;

/**
 * Created by liuhui on 2016/6/30.
 */
public class WaitDialog extends ProgressDialog {

    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage(context.getText(R.string.wait_dialog_title));
    }
}
