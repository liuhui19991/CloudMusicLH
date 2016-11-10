package cn.world.liuhui.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.world.liuhui.R;

/**
 * Created by tiny on 16/3/2.
 */
public class DatePickerDialog implements View.OnClickListener {
    private static final int DEFAULT_MIN_YEAR = 1900;
    public Button cancelBtn;
    public Button confirmBtn;
    public LoopView yearLoopView;
    public LoopView monthLoopView;
    public LoopView dayLoopView;
    public View pickerContainerV;
    public View contentView;//root view

    private int minYear; // min year
    private int maxYear; // max year
    private int yearPos = 0;
    private int monthPos = 0;
    private int dayPos = 0;
    private Context mContext;
    private String textCancel;
    private String textConfirm;
    private int colorCancel;
    private int colorConfirm;
    private int btnTextsize;//text btnTextsize of cancel and confirm button
    private int viewTextSize;

    List<String> yearList = new ArrayList();
    List<String> monthList = new ArrayList();
    List<String> dayList = new ArrayList();

    private boolean isScrolling = false;
    private LinearLayout loopviewParent;
    private RelativeLayout.LayoutParams paramsLoopviewParent;

    public static class Builder {

        //Required
        private Context context;
        private OnDatePickedListener listener;

        public Builder(Context context, OnDatePickedListener listener) {
            this.context = context;
            this.listener = listener;
        }

        //set default value
        private int minYear = DEFAULT_MIN_YEAR;
        private int maxYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
        private String textCancel = "取消";
        private String textConfirm = "确定";
        private String dateChose = getStrDate();
        private int colorCancel = Color.parseColor("#333333");//@color/font_main1
        private int colorConfirm = Color.parseColor("#ff524f");//@color/red_1
        private int btnTextSize = 18;//text btnTextSize of cancel and confirm button
        private int viewTextSize = 16;

        public Builder minYear(int minYear) {
            this.minYear = minYear;
            return this;
        }

        public Builder maxYear(int maxYear) {
            this.maxYear = maxYear;
            return this;
        }

        public Builder textCancel(String textCancel) {
            this.textCancel = textCancel;
            return this;
        }

        public Builder textConfirm(String textConfirm) {
            this.textConfirm = textConfirm;
            return this;
        }

        public Builder dateChose(String dateChose) {
            this.dateChose = dateChose;
            return this;
        }

        public Builder colorCancel(int colorCancel) {
            this.colorCancel = colorCancel;
            return this;
        }

        public Builder colorConfirm(int colorConfirm) {
            this.colorConfirm = colorConfirm;
            return this;
        }

        /**
         * set btn text btnTextSize
         *
         * @param textSize dp
         */
        public Builder btnTextSize(int textSize) {
            this.btnTextSize = textSize;
            return this;
        }

        public Builder viewTextSize(int textSize) {
            this.viewTextSize = textSize;
            return this;
        }

        public DatePickerDialog build() {
            if (minYear > maxYear) {
                throw new IllegalArgumentException();
            }
            return new DatePickerDialog(this);
        }
    }

    public DatePickerDialog(Builder builder) {
        this.minYear = builder.minYear;
        this.maxYear = builder.maxYear;
        this.textCancel = builder.textCancel;
        this.textConfirm = builder.textConfirm;
        this.mContext = builder.context;
        this.mListener = builder.listener;
        this.colorCancel = builder.colorCancel;
        this.colorConfirm = builder.colorConfirm;
        this.btnTextsize = builder.btnTextSize;
        this.viewTextSize = builder.viewTextSize;
        setSelectedDate(builder.dateChose);
        initView();
    }

    private OnDatePickedListener mListener;

    private void initView() {

        contentView = LayoutInflater.from(mContext).inflate(
                R.layout.layout_date_picker, null);
        cancelBtn = (Button) contentView.findViewById(R.id.btn_cancel);
        confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm);
        yearLoopView = (LoopView) contentView.findViewById(R.id.picker_year);
        monthLoopView = (LoopView) contentView.findViewById(R.id.picker_month);
        dayLoopView = (LoopView) contentView.findViewById(R.id.picker_day);
        pickerContainerV = contentView.findViewById(R.id.container_picker);
        loopviewParent = (LinearLayout) contentView.findViewById(R.id.loopview_parent);

        cancelBtn.setText(textCancel);
        confirmBtn.setText(textConfirm);
        cancelBtn.setTextColor(colorCancel);
        confirmBtn.setTextColor(colorConfirm);
        cancelBtn.setTextSize(btnTextsize);
        confirmBtn.setTextSize(btnTextsize);

        //do not loop,default can loop
        yearLoopView.setNotLoop();
        monthLoopView.setNotLoop();
        dayLoopView.setNotLoop();

        //set loopview text btnTextsize
        yearLoopView.setTextSize(viewTextSize);
        monthLoopView.setTextSize(viewTextSize);
        dayLoopView.setTextSize(viewTextSize);

        //set checked listener
        yearLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                yearPos = item;
                initDayPickerView();
            }
        });
        monthLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                monthPos = item;
                initDayPickerView();
            }
        });
        dayLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                dayPos = item;
            }
        });

        initPickerViews(); // init year and month loop view
        initDayPickerView(); //init day loop view

        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        contentView.setOnClickListener(this);
        //add by tiny  set loopView height dynamicly
        int loopViewHeight = yearLoopView.getMaxTextHeight();
        paramsLoopviewParent = (RelativeLayout.LayoutParams) loopviewParent.getLayoutParams();
        paramsLoopviewParent.height = loopViewHeight * 8;
        loopviewParent.setLayoutParams(paramsLoopviewParent);

        LinearLayout.LayoutParams paramsYear = (LinearLayout.LayoutParams) yearLoopView.getLayoutParams();
        paramsYear.height = loopViewHeight * 8;
        yearLoopView.setLayoutParams(paramsYear);
        yearLoopView.setPadding(0, dip2px(mContext, 7), 0, dip2px(mContext, 5));
        LinearLayout.LayoutParams paramsMonth = (LinearLayout.LayoutParams) monthLoopView.getLayoutParams();
        paramsMonth.height = loopViewHeight * 8;
        monthLoopView.setLayoutParams(paramsMonth);
        monthLoopView.setPadding(0, dip2px(mContext, 7), 0, dip2px(mContext, 5));
        LinearLayout.LayoutParams paramsDay = (LinearLayout.LayoutParams) dayLoopView.getLayoutParams();
        paramsDay.height = loopViewHeight * 8;
        dayLoopView.setLayoutParams(paramsDay);
        dayLoopView.setPadding(0, dip2px(mContext, 7), 0, dip2px(mContext, 5));
    }

    /**
     * Init year and month loop view,
     * Let the day loop view be handled separately
     * modified by tiny
     * add setMinMonth and setMonthDay.
     */
    private void initPickerViews() {
        int yearCount = maxYear - minYear;

        for (int i = 0; i < yearCount; i++) {
            yearList.add(format2LenStr(minYear + i) + "年");
        }
        for (int j = 0; j < 12; j++) {
            monthList.add(format2LenStr(j + 1) + "月");
        }

        yearLoopView.setArrayList((ArrayList) yearList);
        yearLoopView.setInitPosition(yearPos);

        monthLoopView.setArrayList((ArrayList) monthList);
        monthLoopView.setInitPosition(monthPos);
    }

    /**
     * Init day item
     */
    private void initDayPickerView() {

        int dayMaxInMonth;
        Calendar calendar = Calendar.getInstance();
        dayList = new ArrayList<String>();

        calendar.set(Calendar.YEAR, minYear + yearPos);
        calendar.set(Calendar.MONTH, monthPos);
        if (monthPos == 1) {//2月做特殊处理，这里显示有点问题。
            if ((minYear + yearPos) % 400 == 0) {
                dayMaxInMonth = 29;
            } else if ((minYear + yearPos) % 4 == 0 && ((minYear + yearPos)) % 100 != 0) {
                dayMaxInMonth = 29;
            } else {
                dayMaxInMonth = 28;
            }

        } else {
            dayMaxInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        for (int i = 0; i < dayMaxInMonth; i++) {
            dayList.add(format2LenStr(i + 1) + "日");
        }

        dayLoopView.setArrayList((ArrayList) dayList);
        dayLoopView.setInitPosition(dayPos);
    }

    /**
     * set selected date position value when initView.
     *
     * @param dateStr
     */
    public void setSelectedDate(String dateStr) {
        if (!TextUtils.isEmpty(dateStr)) {

            long milliseconds = getLongFromyyyyMMdd(dateStr);
            Calendar calendar = Calendar.getInstance(Locale.CHINA);

            if (milliseconds != -1) {
                //init calendar data.
                calendar.setTimeInMillis(milliseconds);
                yearPos = calendar.get(Calendar.YEAR) - minYear;
                monthPos = calendar.get(Calendar.MONTH);
                dayPos = calendar.get(Calendar.DAY_OF_MONTH) - 1;
            }
        }
    }

    /**
     * get long from yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static long getLongFromyyyyMMdd(String date) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        Date parse = null;
        try {
            parse = mFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (parse != null) {
            return parse.getTime();
        } else {
            return -1;
        }
    }

    public static String getStrDate() {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return dd.format(new Date());
    }

    /**
     * Transform int to String with prefix "0" if less than 10
     *
     * @param num
     * @return
     */
    public static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    public static int spToPx(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public interface OnDatePickedListener {

        /**
         * Listener when date has been checked
         *
         * @param year
         * @param month
         * @param day
         * @param dateDesc yyyy.MM.dd
         */
        void onDatePickCompleted(int year, int month, int day,
                                 String dateDesc);
    }

    @Override
    public void onClick(View v) {

        if (v == cancelBtn) {
            dismissDialog();
        } else if (v == confirmBtn) {
            if (null != mListener) {
                switch (monthPos + 1) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12://31 at most
                        if (dayPos + 1 > 31) {
                            dayPos = 30;
                        }
                        break;
                    case 2://28 or 29
                        if ((minYear + yearPos) % 400 == 0) {
                            if (dayPos + 1 > 29) {
                                dayPos = 28;
                            }
                        } else if ((minYear + yearPos) % 4 == 0 && ((minYear + yearPos)) % 100 != 0) {
                            if (dayPos + 1 > 29) {
                                dayPos = 28;
                            }
                        } else {
                            if (dayPos + 1 > 28) {
                                dayPos = 27;
                            }
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11://30 at most
                        if (dayPos + 1 > 30) {
                            dayPos = 29;
                        }
                        break;
                    default:
                        break;
                }
                //get current date
                int year = minYear + yearPos;
                int month = monthPos + 1;
                int day = dayPos + 1;
                StringBuffer sb = new StringBuffer();
                sb.append(String.valueOf(year));
                sb.append(".");
                sb.append(format2LenStr(month));
                sb.append(".");
                sb.append(format2LenStr(day));
                mListener.onDatePickCompleted(year, month, day, sb.toString());
            }
            dismissDialog();
        }
    }

    private AlertDialog dialog;

    /**
     * Show date picker popWindow
     *
     * @param activity
     */
    public void showDialog(Activity activity) {

        if (null != activity) {
            //弹出对话框
            if (dialog == null) {
                dialog = new AlertDialog.Builder(mContext).create();
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.show();
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setContentView(contentView);
//            window.setWindowAnimations(R.style.animation_dialog);
            //下方的代码无用，不需要设置宽和高，只需在dialog的布局中设置父窗体为包裹内容即可。
            int width = (int) (getScreenW(mContext) * 5 / 6f);
//            int height = (int) (getScreenH(mContext) * 1 / 2f);
//            int height = dip2px(mContext, px2dip(mContext, paramsLoopviewParent.height) + 45 + 40 + 30);
//            int height = dip2px(mContext, px2dip(mContext, (int) (paramsLoopviewParent.height / 8) * 3));
//            window.setLayout(width, height);
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * Dismiss date picker popWindow
     */
    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 获取屏幕宽度
     */
    public int getScreenW(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenH(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
