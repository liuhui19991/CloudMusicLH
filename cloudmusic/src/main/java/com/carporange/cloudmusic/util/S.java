/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.carporange.cloudmusic.util;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

/**
 * 展示Snackbar的工具类
 * Created by liuhui on 2016/7/11.
 */
public class S {

    public static void show(Activity context, CharSequence msg) {
        Snackbar.make(context.getWindow().getDecorView(), msg, Snackbar.LENGTH_LONG).show();
    }

    public static void show(Activity context, @StringRes int stringId) {
        Snackbar.make(context.getWindow().getDecorView(), stringId, Snackbar.LENGTH_LONG).show();
    }

}
