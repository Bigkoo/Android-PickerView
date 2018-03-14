package com.bigkoo.pickerview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelTime;
import com.contrarywind.view.WheelView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 * Updated by XiaoSong on 2017-2-22.
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {


    private WheelTime wheelTime; //自定义控件


    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    //构造方法
    public TimePickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }


    //建造器
    public static class Builder {

        //配置类
        private PickerOptions mPickerOptions;

        //Required
        public Builder(Context context, OnTimeSelectListener listener) {
            mPickerOptions = new PickerOptions(PickerOptions.TYPE_PICKER_TIME);
            mPickerOptions.context = context;
            mPickerOptions.timeSelectListener = listener;
        }

        //Option
        public Builder setType(boolean[] type) {
            mPickerOptions.type = type;
            return this;
        }

        public Builder setGravity(int gravity) {
            mPickerOptions.textGravity = gravity;
            return this;
        }

        public Builder setSubmitText(String textContentConfirm) {
            mPickerOptions.textContentConfirm = textContentConfirm;
            return this;
        }

        public Builder isDialog(boolean isDialog) {
            mPickerOptions.isDialog = isDialog;
            return this;
        }

        public Builder setCancelText(String textContentCancel) {
            mPickerOptions.textContentCancel = textContentCancel;
            return this;
        }

        public Builder setTitleText(String textContentTitle) {
            mPickerOptions.textContentTitle = textContentTitle;
            return this;
        }

        public Builder setSubmitColor(int textColorConfirm) {
            mPickerOptions.textColorConfirm = textColorConfirm;
            return this;
        }

        public Builder setCancelColor(int textColorCancel) {
            mPickerOptions.textColorCancel = textColorCancel;
            return this;
        }

        /**
         * ViewGroup 类型的容器
         *
         * @param decorView 选择器会被添加到此容器中
         * @return this
         */
        public Builder setDecorView(ViewGroup decorView) {
            mPickerOptions.decorView = decorView;
            return this;
        }

        public Builder setBgColor(int bgColorWheel) {
            mPickerOptions.bgColorWheel = bgColorWheel;
            return this;
        }

        public Builder setTitleBgColor(int bgColorTitle) {
            mPickerOptions.bgColorTitle = bgColorTitle;
            return this;
        }

        public Builder setTitleColor(int textColorTitle) {
            mPickerOptions.textColorTitle = textColorTitle;
            return this;
        }

        public Builder setSubCalSize(int textSizeSubmitCancel) {
            mPickerOptions.textSizeSubmitCancel = textSizeSubmitCancel;
            return this;
        }

        public Builder setTitleSize(int textSizeTitle) {
            mPickerOptions.textSizeTitle = textSizeTitle;
            return this;
        }

        public Builder setContentTextSize(int textSizeContent) {
            mPickerOptions.textSizeContent = textSizeContent;
            return this;
        }

        /**
         * 因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         *
         * @param date
         * @return
         */
        public Builder setDate(Calendar date) {
            mPickerOptions.date = date;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener customListener) {
            mPickerOptions.layoutRes = res;
            mPickerOptions.customListener = customListener;
            return this;
        }

        /**
         * use the setRangDate method instead.
         *
         * @deprecated Use {@link  #setRangDate()} with two Calendar value.
         */
        @Deprecated
        public Builder setRange(int startYear, int endYear) {
            mPickerOptions.startYear = startYear;
            mPickerOptions.endYear = endYear;
            return this;
        }

        /**
         * 设置起始时间
         * 因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         */

        public Builder setRangDate(Calendar startDate, Calendar endDate) {
            mPickerOptions.startDate = startDate;
            mPickerOptions.endDate = endDate;
            return this;
        }


        /**
         * 设置间距倍数,但是只能在1.0-4.0f之间
         *
         * @param lineSpacingMultiplier
         */
        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier) {
            mPickerOptions.lineSpacingMultiplier = lineSpacingMultiplier;
            return this;
        }

        /**
         * 设置分割线的颜色
         *
         * @param dividerColor
         */
        public Builder setDividerColor(int dividerColor) {
            mPickerOptions.dividerColor = dividerColor;
            return this;
        }

        /**
         * 设置分割线的类型
         *
         * @param dividerType
         */
        public Builder setDividerType(WheelView.DividerType dividerType) {
            mPickerOptions.dividerType = dividerType;
            return this;
        }

        /**
         * //显示时的外部背景色颜色,默认是灰色
         *
         * @param backgroundId
         */

        public Builder setBackgroundId(int backgroundId) {
            mPickerOptions.backgroundId = backgroundId;
            return this;
        }

        /**
         * 设置分割线之间的文字的颜色
         *
         * @param textColorCenter
         */
        public Builder setTextColorCenter(int textColorCenter) {
            mPickerOptions.textColorCenter = textColorCenter;
            return this;
        }

        /**
         * 设置分割线以外文字的颜色
         *
         * @param textColorOut
         */
        public Builder setTextColorOut(int textColorOut) {
            mPickerOptions.textColorOut = textColorOut;
            return this;
        }

        public Builder isCyclic(boolean cyclic) {
            mPickerOptions.cyclic = cyclic;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable) {
            mPickerOptions.cancelable = cancelable;
            return this;
        }

        public Builder setLunarCalendar(boolean lunarCalendar) {
            mPickerOptions.isLunarCalendar = lunarCalendar;
            return this;
        }


        public Builder setLabel(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
            mPickerOptions.label_year = label_year;
            mPickerOptions.label_month = label_month;
            mPickerOptions.label_day = label_day;
            mPickerOptions.label_hours = label_hours;
            mPickerOptions.label_mins = label_mins;
            mPickerOptions.label_seconds = label_seconds;
            return this;
        }

        /**
         * 设置X轴倾斜角度[ -90 , 90°]
         *
         * @param x_offset_year    年
         * @param x_offset_month   月
         * @param x_offset_day     日
         * @param x_offset_hours   时
         * @param x_offset_minutes 分
         * @param x_offset_seconds 秒
         * @return
         */
        public Builder setTextXOffset(int x_offset_year, int x_offset_month, int x_offset_day,
                                      int x_offset_hours, int x_offset_minutes, int x_offset_seconds) {
            mPickerOptions.x_offset_year = x_offset_year;
            mPickerOptions.x_offset_month = x_offset_month;
            mPickerOptions.x_offset_day = x_offset_day;
            mPickerOptions.x_offset_hours = x_offset_hours;
            mPickerOptions.x_offset_minutes = x_offset_minutes;
            mPickerOptions.x_offset_seconds = x_offset_seconds;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            mPickerOptions.isCenterLabel = isCenterLabel;
            return this;
        }


        public TimePickerView build() {
            return new TimePickerView(mPickerOptions);
        }
    }


    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        init();
        initEvents();
        if (mPickerOptions.customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);

            //顶部标题
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);

            //确定和取消按钮
            Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
            Button btnCancel = (Button) findViewById(R.id.btnCancel);

            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);

            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //设置文字
            btnSubmit.setText(TextUtils.isEmpty(mPickerOptions.textContentConfirm) ? context.getResources().getString(R.string.pickerview_submit) : mPickerOptions.textContentConfirm);
            btnCancel.setText(TextUtils.isEmpty(mPickerOptions.textContentCancel) ? context.getResources().getString(R.string.pickerview_cancel) : mPickerOptions.textContentCancel);
            tvTitle.setText(TextUtils.isEmpty(mPickerOptions.textContentTitle) ? "" : mPickerOptions.textContentTitle);//默认为空


            //设置color
            btnSubmit.setTextColor(mPickerOptions.textColorConfirm);
            btnCancel.setTextColor(mPickerOptions.textColorCancel);
            tvTitle.setTextColor(mPickerOptions.textColorTitle);
            rv_top_bar.setBackgroundColor(mPickerOptions.bgColorTitle);


            //设置文字大小
            btnSubmit.setTextSize(mPickerOptions.textSizeSubmitCancel);
            btnCancel.setTextSize(mPickerOptions.textSizeSubmitCancel);
            tvTitle.setTextSize(mPickerOptions.textSizeTitle);

        } else {
            mPickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer));
        }
        // 时间转轮 自定义控件
        LinearLayout timePickerView = (LinearLayout) findViewById(R.id.timepicker);

        timePickerView.setBackgroundColor(mPickerOptions.bgColorWheel);

        wheelTime = new WheelTime(timePickerView, mPickerOptions.type, mPickerOptions.textGravity, mPickerOptions.textSizeContent);
        wheelTime.setLunarCalendar(mPickerOptions.isLunarCalendar);

        if (mPickerOptions.startYear != 0 && mPickerOptions.endYear != 0 && mPickerOptions.startYear <= mPickerOptions.endYear) {
            setRange();
        }

        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            if (mPickerOptions.startDate.getTimeInMillis() <= mPickerOptions.endDate.getTimeInMillis()) {
                setRangDate();
            }
        } else if (mPickerOptions.startDate != null) {
            setRangDate();
        } else if (mPickerOptions.endDate != null) {
            setRangDate();
        }

        setTime();
        wheelTime.setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day
                , mPickerOptions.label_hours, mPickerOptions.label_mins, mPickerOptions.label_seconds);
        wheelTime.setTextXOffset(mPickerOptions.x_offset_year, mPickerOptions.x_offset_month, mPickerOptions.x_offset_day,
                mPickerOptions.x_offset_hours, mPickerOptions.x_offset_minutes, mPickerOptions.x_offset_seconds);

        setOutSideCancelable(mPickerOptions.cancelable);
        wheelTime.setCyclic(mPickerOptions.cyclic);
        wheelTime.setDividerColor(mPickerOptions.dividerColor);
        wheelTime.setDividerType(mPickerOptions.dividerType);
        wheelTime.setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        wheelTime.setTextColorOut(mPickerOptions.textColorOut);
        wheelTime.setTextColorCenter(mPickerOptions.textColorCenter);
        wheelTime.isCenterLabel(mPickerOptions.isCenterLabel);
    }


    /**
     * 设置默认时间
     */
    public void setDate(Calendar date) {
        mPickerOptions.date = date;
        setTime();
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRange() {
        wheelTime.setStartYear(mPickerOptions.startYear);
        wheelTime.setEndYear(mPickerOptions.endYear);

    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRangDate() {
        wheelTime.setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
        //如果设置了时间范围
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            //判断一下默认时间是否设置了，或者是否在起始终止时间范围内
            if (mPickerOptions.date == null || mPickerOptions.date.getTimeInMillis() < mPickerOptions.startDate.getTimeInMillis()
                    || mPickerOptions.date.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                mPickerOptions.date = mPickerOptions.startDate;
            }
        } else if (mPickerOptions.startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            mPickerOptions.date = mPickerOptions.startDate;
        } else if (mPickerOptions.endDate != null) {
            mPickerOptions.date = mPickerOptions.endDate;
        }
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime() {
        int year, month, day, hours, minute, seconds;

        Calendar calendar = Calendar.getInstance();

        if (mPickerOptions.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);
        } else {
            year = mPickerOptions.date.get(Calendar.YEAR);
            month = mPickerOptions.date.get(Calendar.MONTH);
            day = mPickerOptions.date.get(Calendar.DAY_OF_MONTH);
            hours = mPickerOptions.date.get(Calendar.HOUR_OF_DAY);
            minute = mPickerOptions.date.get(Calendar.MINUTE);
            seconds = mPickerOptions.date.get(Calendar.SECOND);
        }

        wheelTime.setPicker(year, month, day, hours, minute, seconds);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    public void returnData() {
        if (mPickerOptions.timeSelectListener != null) {
            try {
                Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                mPickerOptions.timeSelectListener.onTimeSelect(date, clickView);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *  目前暂时只支持设置1900 - 2100年
     * @param lunar 农历的开关
     */
    public void setLunarCalendar(boolean lunar) {
        try {
            int year, month, day, hours, minute, seconds;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(WheelTime.dateFormat.parse(wheelTime.getTime()));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);

            wheelTime.setLunarCalendar(lunar);
            wheelTime.setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day,
                    mPickerOptions.label_hours, mPickerOptions.label_mins, mPickerOptions.label_seconds);
            wheelTime.setPicker(year, month, day, hours, minute, seconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isLunarCalendar() {
        return wheelTime.isLunarCalendar();
    }


    public interface OnTimeSelectListener {
        void onTimeSelect(Date date, View v);
    }

    @Override
    public boolean isDialog() {
        return mPickerOptions.isDialog;
    }
}
