package cn.world.liuhui.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

/**
 * Created by liuhui on 2016/11/9.
 */

public class DateDialogUtil {
    /**
     * 展示日历的弹窗
     *
     * @param context         上下文
     * @param dateSetListener 获取选择时间的弹窗
     */
    public static void createDateDialog(Context context, DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.getDatePicker().setCalendarViewShown(false);//这句代码好像没有效果
        datePickerDialog.show();
    }

    public static void createDateDialogSign(Activity context, cn.world.liuhui.calendar.DatePickerDialog.OnDatePickedListener listener) {
        Calendar calendar = Calendar.getInstance();
        cn.world.liuhui.calendar.DatePickerDialog pickerDialog = new cn.world.liuhui.calendar.DatePickerDialog.Builder(context, listener)
                .minYear(2014) //min year in loop
                .maxYear(2017 + 1) // max year in loop
                .dateChose(calendar.get(Calendar.YEAR)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.DAY_OF_MONTH)) // date chose when init popwindow
                .build();
        pickerDialog.showDialog(context);

    }
   /* dateOld = dateCurr;
    //调用日期选择器
    if (TextUtils.isEmpty(mTxtDateSelect.getText().toString().trim())) {
        return;
    }
    String dateTime = transTimeStringToDot(mTxtDateSelect.getText().toString().trim());
    if (TextUtils.isEmpty(dateTime)) {
        return;
    }
    if (dateTime.compareTo(minDate) < 1) {
        dateTime = minDate;
    }
    DatePickerDialog pickerDialog = new DatePickerDialog.Builder(getActivity(), new DatePickerDialog.OnDatePickedListener() {

        @Override
        public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
            dateCurr = dateDesc.replace(".", "");
            if (!dateOld.equals(dateCurr)) {
                reqData();
            } else {
                return;
            }
        }
    }).minYear(minYear) //min year in loop
            .maxYear(maxYear + 1) // max year in loop
            .dateChose(dateTime) // date chose when init popwindow
            .build();
    pickerDialog.showDialog(getActivity());*/

}

