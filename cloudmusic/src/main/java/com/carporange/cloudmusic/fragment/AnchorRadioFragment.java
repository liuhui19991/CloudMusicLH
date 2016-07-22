package com.carporange.cloudmusic.fragment;


import android.inputmethodservice.Keyboard;
import android.widget.EditText;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseFragment;
import com.carporange.cloudmusic.util.KeyBoardUtils;

import butterknife.BindView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class AnchorRadioFragment extends BaseFragment {
    @BindView(R.id.et)
    EditText mEditText;

    public AnchorRadioFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onVisible() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ancor_radio;
    }


}
