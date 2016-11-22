package cn.world.liuhui.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import java.util.Calendar;

import cn.world.liuhui.calendar.SignPickerDialog;

/**
 * 弹框工具类
 * Created by liuhui on 2015/7/27.
 */
public class DialogUtil {
    /**
     * 显示一个对话框
     *
     * @param context                    上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title                      标题
     * @param message                    消息
     * @param confirmButton              确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param centerButton               中间按钮
     * @param centerButtonClickListener  中间按钮点击监听器
     * @param cancelButton               取消按钮
     * @param cancelButtonClickListener  取消按钮点击监听器
     * @param onShowListener             显示监听器
     * @param cancelable                 是否允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     * @param onCancelListener           取消监听器
     * @param onDismissListener          销毁监听器
     * @return 对话框
     */
    public static AlertDialog showAlert(Context context, String title, String message, String confirmButton, DialogInterface.OnClickListener confirmButtonClickListener, String centerButton, DialogInterface.OnClickListener centerButtonClickListener, String cancelButton, DialogInterface.OnClickListener cancelButtonClickListener, DialogInterface.OnShowListener onShowListener, boolean cancelable, DialogInterface.OnCancelListener onCancelListener, DialogInterface.OnDismissListener onDismissListener) {
        AlertDialog.Builder promptBuilder = new AlertDialog.Builder(context);
        if (title != null) {
            promptBuilder.setTitle(title);
        }
        if (message != null) {
            promptBuilder.setMessage(message);
        }
        if (confirmButton != null) {
            promptBuilder.setPositiveButton(confirmButton,
                    confirmButtonClickListener);
        }
        if (centerButton != null) {
            promptBuilder.setNeutralButton(centerButton,
                    centerButtonClickListener);
        }
        if (cancelButton != null) {
            promptBuilder.setNegativeButton(cancelButton,
                    cancelButtonClickListener);
        }
        promptBuilder.setCancelable(true);
        if (cancelable) {
            promptBuilder.setOnCancelListener(onCancelListener);
        }
        AlertDialog alertDialog = promptBuilder.create();
        if (!(context instanceof Activity)) {
            alertDialog.getWindow()
                    .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        alertDialog.setOnDismissListener(onDismissListener);
        alertDialog.setOnShowListener(onShowListener);
        alertDialog.show();
        return alertDialog;
    }


    /**
     * 显示一个对话框
     *
     * @param context                    上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title                      标题
     * @param message                    消息
     * @param confirmButton              确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param cancelButton               取消按钮
     * @param cancelButtonClickListener  取消按钮点击监听器
     * @return 对话框
     */
    public static AlertDialog showAlert(Context context, String title, String message, String confirmButton, DialogInterface.OnClickListener confirmButtonClickListener, String cancelButton, DialogInterface.OnClickListener cancelButtonClickListener) {
        return showAlert(context, title, message, confirmButton,
                confirmButtonClickListener, null, null, cancelButton,
                cancelButtonClickListener, null, true, null, null);
    }


    /**
     * 显示一个提示框
     *
     * @param context       上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message       提示的消息
     * @param confirmButton 确定按钮的名字
     */
    public static AlertDialog showPrompt(Context context, String message, String confirmButton) {
        return showAlert(context, null, message, confirmButton, null, null,
                null, null, null, null, true, null, null);
    }


    /**
     * 显示一个提示框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message 提示的消息
     */
    public static AlertDialog showPrompt(Context context, String message) {
        return showAlert(context, null, message, "OK", null, null, null, null,
                null, null, true, null, null);
    }

    /**
     * 展示日历的弹窗
     *
     * @param context         上下文
     * @param dateSetListener 获取选择时间的弹窗
     */
    public static void showDateDialog(Context context, DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.getDatePicker().setCalendarViewShown(false);//这句代码好像没有效果
        datePickerDialog.show();
    }

    public static void showDateDialogSign(Activity context, cn.world.liuhui.calendar.DatePickerDialog.OnDatePickedListener listener) {
        Calendar calendar = Calendar.getInstance();
        cn.world.liuhui.calendar.DatePickerDialog pickerDialog = new cn.world.liuhui.calendar.DatePickerDialog.Builder(context, listener)
                .minYear(2014) //min year in loop
                .maxYear(2017 + 1) // max year in loop
                .dateChose(calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.DAY_OF_MONTH)) // date chose when init popwindow
                .build();
        pickerDialog.showDialog(context);
    }

    /**
     * 展示单选的弹窗
     *
     * @param title     标题
     * @param activity  上下文
     * @param signtrans 单选中间存储
     * @param positive  确定监听
     */
    public static void showSignSelectDialog(Activity activity, String title, final String[] strings, DialogInterface.OnClickListener signtrans, DialogInterface.OnClickListener positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setSingleChoiceItems(strings, 0, signtrans)
                .setPositiveButton("确定", positive)
                .setNegativeButton("取消", null)
                .show();
    }

    public static void showSignSlecter(Activity context, SignPickerDialog.OnDatePickedListener listener) {
        SignPickerDialog pickerDialog = new SignPickerDialog.Builder(context, listener)

                .dateChose("2.5.2")
                .build();
        pickerDialog.showDialog(context);
    }

    public static void showSettingPermission(final Activity activity, String content) {
        Dialog deleteDialog = new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(content)
                .setPositiveButton("去设置",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startSettingIntent(activity);
                            }
                        }).create();
        deleteDialog.show();
    }

    /**
     * 启动app设置授权界面
     *
     * @param context
     */
    public static void startSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
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
