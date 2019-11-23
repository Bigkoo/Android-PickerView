package com.bigkoo.pickerview.view;

import android.view.View;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;
import com.bigkoo.pickerview.utils.ChinaDate;
import com.bigkoo.pickerview.utils.LunarCalendar;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class WheelTime {
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    /**
     * 说明：{@link Calendar} 内部的月份是从0开始的，此处对应的实际是1月份
     */
    private static final int DEFAULT_START_MONTH = 0;
    /**
     * 说明：{@link Calendar} 内部的月份是从0开始的，此处对应的实际是12月份
     */
    private static final int DEFAULT_END_MONTH = 11;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_minutes;
    private WheelView wv_seconds;
    private int gravity;
    private boolean[] type;
    private int currentYear;

    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    private int textSize;

    private boolean isLunarCalendar = false;
    private ISelectTimeCallback mSelectChangeCallback;

    public WheelTime(View view, boolean[] type, int gravity, int textSize) {
        super();
        this.view = view;
        this.type = type;
        this.gravity = gravity;
        this.textSize = textSize;
        initRangeDate();
    }

    private void initRangeDate() {
        startDate.set(DEFAULT_START_YEAR,
                DEFAULT_START_MONTH,
                DEFAULT_START_DAY);
        endDate.set(DEFAULT_END_YEAR,
                DEFAULT_END_MONTH,
                DEFAULT_END_DAY);
    }

    public boolean isLunarMode() {
        return isLunarCalendar;
    }

    public void setLunarMode(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0, 0);
    }

    public void setPicker(int year, final int month, int day, int h, int m, int s) {
        if (isLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
            setLunar(lunar[0], lunar[1] - 1, lunar[2], lunar[3] == 1, h, m, s);
        } else {
            setSolar(year, month, day, h, m, s);
        }
    }

    /**
     * 设置农历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setLunar(int year, final int month, int day, boolean isLeap, int h, int m, int s) {
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new ArrayWheelAdapter(ChinaDate.getYears(getStartYear(), getEndYear())));// 设置"年"的显示数据
        wv_year.setLabel("");// 添加文字
        wv_year.setCurrentItem(year - getStartYear());// 初始化时显示的数据
        wv_year.setGravity(gravity);

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year)));
        wv_month.setLabel("");

        int leapMonth = ChinaDate.leapMonth(year);
        if (leapMonth != 0 && (month > leapMonth - 1 || isLeap)) { //选中月是闰月或大于闰月
            wv_month.setCurrentItem(month + 1);
        } else {
            wv_month.setCurrentItem(month);
        }

        wv_month.setGravity(gravity);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (ChinaDate.leapMonth(year) == 0) {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        wv_day.setLabel("");
        wv_day.setCurrentItem(day - 1);
        wv_day.setGravity(gravity);

        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        //wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);
        wv_hours.setGravity(gravity);

        wv_minutes = (WheelView) view.findViewById(R.id.min);
        wv_minutes.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_minutes.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_minutes.setCurrentItem(m);
        wv_minutes.setGravity(gravity);

        wv_seconds = (WheelView) view.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_seconds.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_seconds.setCurrentItem(m);
        wv_seconds.setGravity(gravity);

        // 添加"年"监听
        wv_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + getStartYear();
                // 判断是不是闰年,来确定月和日的选择
                wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    wv_month.setCurrentItem(wv_month.getCurrentItem() + 1);
                } else {
                    wv_month.setCurrentItem(wv_month.getCurrentItem());
                }

                int currentIndex = wv_day.getCurrentItem();
                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem()))));
                        maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem());
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1))));
                    maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1);
                }

                if (currentIndex > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index;
                int year_num = wv_year.getCurrentItem() + getStartYear();
                int currentIndex = wv_day.getCurrentItem();
                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && month_num > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num))));
                        maxItem = ChinaDate.monthDays(year_num, month_num);
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num + 1))));
                    maxItem = ChinaDate.monthDays(year_num, month_num + 1);
                }

                if (currentIndex > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        setChangedListener(wv_day);
        setChangedListener(wv_hours);
        setChangedListener(wv_minutes);
        setChangedListener(wv_seconds);

        if (type.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_minutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    /**
     * 设置公历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setSolar(int year, final int month, int day, int h, int m, int s) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        currentYear = year;
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(getStartYear(), getEndYear()));// 设置"年"的显示数据


        wv_year.setCurrentItem(year - getStartYear());// 初始化时显示的数据
        wv_year.setGravity(gravity);
        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        if (getStartYear() == getEndYear()) {//开始年等于终止年
            wv_month.setAdapter(new NumericWheelAdapter(getStartMonth(), getEndMonth()));
            wv_month.setCurrentItem(month + 1 - getStartMonth());
        } else if (year == getStartYear()) {
            //起始日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(getStartMonth(), 12));
            wv_month.setCurrentItem(month + 1 - getStartMonth());
        } else if (year == getEndYear()) {
            //终止日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(1, getEndMonth()));
            wv_month.setCurrentItem(month);
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCurrentItem(month);
        }
        wv_month.setGravity(gravity);
        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);

        boolean leapYear = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
        if (getStartYear() == getEndYear() && getStartMonth() == getEndMonth()) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (getEndDay() > 31) {
                    setEndDay(31);
                }
                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), getEndDay()));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (getEndDay() > 30) {
                    setEndDay(30);
                }
                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), getEndDay()));
            } else {
                // 闰年
                if (leapYear) {
                    if (getEndDay() > 29) {
                        setEndDay(29);
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), getEndDay()));
                } else {
                    if (getEndDay() > 28) {
                        setEndDay(28);
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), getEndDay()));
                }
            }
            wv_day.setCurrentItem(day - getStartDay());
        } else if (year == getStartYear() && month + 1 == getStartMonth()) {
            // 起始日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), 30));
            } else {
                // 闰年 29，平年 28
                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), leapYear ? 29 : 28));
            }
            wv_day.setCurrentItem(day - getStartDay());
        } else if (year == getEndYear() && month + 1 == getEndMonth()) {
            // 终止日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {
                if (getEndDay() > 31) {
                    setEndDay(31);
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, getEndDay()));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (getEndDay() > 30) {
                    setEndDay(30);
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, getEndDay()));
            } else {
                // 闰年
                if (leapYear) {
                    if (getEndDay() > 29) {
                        setEndDay(29);
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, getEndDay()));
                } else {
                    if (getEndDay() > 28) {
                        setEndDay(28);
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, getEndDay()));
                }
            }
            wv_day.setCurrentItem(day - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年 29，平年 28
                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), leapYear ? 29 : 28));
            }
            wv_day.setCurrentItem(day - 1);
        }

        wv_day.setGravity(gravity);
        //时
        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));

        wv_hours.setCurrentItem(h);
        wv_hours.setGravity(gravity);
        //分
        wv_minutes = (WheelView) view.findViewById(R.id.min);
        wv_minutes.setAdapter(new NumericWheelAdapter(0, 59));

        wv_minutes.setCurrentItem(m);
        wv_minutes.setGravity(gravity);
        //秒
        wv_seconds = (WheelView) view.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));

        wv_seconds.setCurrentItem(s);
        wv_seconds.setGravity(gravity);

        // 添加"年"监听
        wv_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + getStartYear();
                currentYear = year_num;
                int currentMonthItem = wv_month.getCurrentItem();//记录上一次的item位置
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (getStartYear() == getEndYear()) {
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(getStartMonth(), getEndMonth()));

                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    int monthNum = currentMonthItem + getStartMonth();

                    if (getStartMonth() == getEndMonth()) {
                        //重新设置日
                        setReDay(year_num, monthNum, getStartDay(), getEndDay(), list_big, list_little);
                    } else if (monthNum == getStartMonth()) {
                        //重新设置日
                        setReDay(year_num, monthNum, getStartDay(), 31, list_big, list_little);
                    } else if (monthNum == getEndMonth()) {
                        setReDay(year_num, monthNum, 1, getEndDay(), list_big, list_little);
                    } else {//重新设置日
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == getStartYear()) {//等于开始的年
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(getStartMonth(), 12));

                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    int month = currentMonthItem + getStartMonth();
                    if (month == getStartMonth()) {
                        //重新设置日
                        setReDay(year_num, month, getStartDay(), 31, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(year_num, month, 1, 31, list_big, list_little);
                    }

                } else if (year_num == getEndYear()) {
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(1, getEndMonth()));
                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + 1;

                    if (monthNum == getEndMonth()) {
                        //重新设置日
                        setReDay(year_num, monthNum, 1, getEndDay(), list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }

                } else {
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(1, 12));
                    //重新设置日
                    setReDay(year_num, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });


        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;

                if (getStartYear() == getEndYear()) {
                    month_num = month_num + getStartMonth() - 1;
                    if (getStartMonth() == getEndMonth()) {
                        //重新设置日
                        setReDay(currentYear, month_num, getStartDay(), getEndDay(), list_big, list_little);
                    } else if (getStartMonth() == month_num) {

                        //重新设置日
                        setReDay(currentYear, month_num, getStartDay(), 31, list_big, list_little);
                    } else if (getEndMonth() == month_num) {
                        setReDay(currentYear, month_num, 1, getEndDay(), list_big, list_little);
                    } else {
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                    }
                } else if (currentYear == getStartYear()) {
                    month_num = month_num + getStartMonth() - 1;
                    if (month_num == getStartMonth()) {
                        //重新设置日
                        setReDay(currentYear, month_num, getStartDay(), 31, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                    }

                } else if (currentYear == getEndYear()) {
                    if (month_num == getEndMonth()) {
                        //重新设置日
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, getEndDay(), list_big, list_little);
                    } else {
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                    }

                } else {
                    //重新设置日
                    setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                }

                if (mSelectChangeCallback != null) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });

        setChangedListener(wv_day);
        setChangedListener(wv_hours);
        setChangedListener(wv_minutes);
        setChangedListener(wv_seconds);

        if (type.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_minutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    private void setChangedListener(WheelView wheelView) {
        if (mSelectChangeCallback != null) {
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mSelectChangeCallback.onTimeSelectChanged();
                }
            });
        }

    }


    private void setReDay(int year_num, int monthNum, int startD, int endD, List<String> list_big, List<String> list_little) {
        int currentItem = wv_day.getCurrentItem();

//        int maxItem;
        if (list_big.contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//            maxItem = endD;
        } else if (list_little.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//            maxItem = endD;
        } else {
            if ((year_num % 4 == 0 && year_num % 100 != 0)
                    || year_num % 400 == 0) {
                if (endD > 29) {
                    endD = 29;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//                maxItem = endD;
            } else {
                if (endD > 28) {
                    endD = 28;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//                maxItem = endD;
            }
        }

        if (currentItem > wv_day.getAdapter().getItemsCount() - 1) {
            currentItem = wv_day.getAdapter().getItemsCount() - 1;
            wv_day.setCurrentItem(currentItem);
        }
    }


    private void setContentTextSize() {
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_minutes.setTextSize(textSize);
        wv_seconds.setTextSize(textSize);
    }


    public void setLabels(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        if (isLunarCalendar) {
            return;
        }

        if (label_year != null) {
            wv_year.setLabel(label_year);
        } else {
            wv_year.setLabel(view.getContext().getString(R.string.pickerview_year));
        }
        if (label_month != null) {
            wv_month.setLabel(label_month);
        } else {
            wv_month.setLabel(view.getContext().getString(R.string.pickerview_month));
        }
        if (label_day != null) {
            wv_day.setLabel(label_day);
        } else {
            wv_day.setLabel(view.getContext().getString(R.string.pickerview_day));
        }
        if (label_hours != null) {
            wv_hours.setLabel(label_hours);
        } else {
            wv_hours.setLabel(view.getContext().getString(R.string.pickerview_hours));
        }
        if (label_mins != null) {
            wv_minutes.setLabel(label_mins);
        } else {
            wv_minutes.setLabel(view.getContext().getString(R.string.pickerview_minutes));
        }
        if (label_seconds != null) {
            wv_seconds.setLabel(label_seconds);
        } else {
            wv_seconds.setLabel(view.getContext().getString(R.string.pickerview_seconds));
        }

    }

    public void setTextXOffset(int x_offset_year, int x_offset_month, int x_offset_day,
                               int x_offset_hours, int x_offset_minutes, int x_offset_seconds) {
        wv_year.setTextXOffset(x_offset_year);
        wv_month.setTextXOffset(x_offset_month);
        wv_day.setTextXOffset(x_offset_day);
        wv_hours.setTextXOffset(x_offset_hours);
        wv_minutes.setTextXOffset(x_offset_minutes);
        wv_seconds.setTextXOffset(x_offset_seconds);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_minutes.setCyclic(cyclic);
        wv_seconds.setCyclic(cyclic);
    }

    public String getTime() {
        if (isLunarCalendar) {
            //如果是农历 返回对应的公历时间
            return getLunarTime();
        }
        StringBuilder sb = new StringBuilder();
        if (currentYear == getStartYear()) {
           /* int i = wv_month.getCurrentItem() + startMonth;
            System.out.println("i:" + i);*/
            if ((wv_month.getCurrentItem() + getStartMonth()) == getStartMonth()) {
                sb.append((wv_year.getCurrentItem() + getStartYear())).append("-")
                        .append((wv_month.getCurrentItem() + getStartMonth())).append("-")
                        .append((wv_day.getCurrentItem() + getStartDay())).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_minutes.getCurrentItem()).append(":")
                        .append(wv_seconds.getCurrentItem());
            } else {
                sb.append((wv_year.getCurrentItem() + getStartYear())).append("-")
                        .append((wv_month.getCurrentItem() + getStartMonth())).append("-")
                        .append((wv_day.getCurrentItem() + 1)).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_minutes.getCurrentItem()).append(":")
                        .append(wv_seconds.getCurrentItem());
            }

        } else {
            sb.append((wv_year.getCurrentItem() + getStartYear())).append("-")
                    .append((wv_month.getCurrentItem() + 1)).append("-")
                    .append((wv_day.getCurrentItem() + 1)).append(" ")
                    .append(wv_hours.getCurrentItem()).append(":")
                    .append(wv_minutes.getCurrentItem()).append(":")
                    .append(wv_seconds.getCurrentItem());
        }

        return sb.toString();
    }


    /**
     * 农历返回对应的公历时间
     *
     * @return
     */
    private String getLunarTime() {
        StringBuilder sb = new StringBuilder();
        int year = wv_year.getCurrentItem() + getStartYear();
        int month = 1;
        boolean isLeapMonth = false;
        if (ChinaDate.leapMonth(year) == 0) {
            month = wv_month.getCurrentItem() + 1;
        } else {
            if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
                month = wv_month.getCurrentItem() + 1;
            } else if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
                month = wv_month.getCurrentItem();
                isLeapMonth = true;
            } else {
                month = wv_month.getCurrentItem();
            }
        }
        int day = wv_day.getCurrentItem() + 1;
        int[] solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth);

        sb.append(solar[0]).append("-")
                .append(solar[1]).append("-")
                .append(solar[2]).append(" ")
                .append(wv_hours.getCurrentItem()).append(":")
                .append(wv_minutes.getCurrentItem()).append(":")
                .append(wv_seconds.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setRangDate(Calendar startDate, Calendar endDate) {
        if (startDate != null && startDate.compareTo(this.endDate) <= 0) {
            this.startDate = startDate;
        }
        if (endDate != null && endDate.compareTo(this.startDate) > 0) {
            this.endDate = endDate;
        }
    }

    /**
     * 设置间距倍数,但是只能在1.0-4.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        wv_day.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_month.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_year.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_hours.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_minutes.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_seconds.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        wv_day.setDividerColor(dividerColor);
        wv_month.setDividerColor(dividerColor);
        wv_year.setDividerColor(dividerColor);
        wv_hours.setDividerColor(dividerColor);
        wv_minutes.setDividerColor(dividerColor);
        wv_seconds.setDividerColor(dividerColor);
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        wv_day.setDividerType(dividerType);
        wv_month.setDividerType(dividerType);
        wv_year.setDividerType(dividerType);
        wv_hours.setDividerType(dividerType);
        wv_minutes.setDividerType(dividerType);
        wv_seconds.setDividerType(dividerType);
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        wv_day.setTextColorCenter(textColorCenter);
        wv_month.setTextColorCenter(textColorCenter);
        wv_year.setTextColorCenter(textColorCenter);
        wv_hours.setTextColorCenter(textColorCenter);
        wv_minutes.setTextColorCenter(textColorCenter);
        wv_seconds.setTextColorCenter(textColorCenter);
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        wv_day.setTextColorOut(textColorOut);
        wv_month.setTextColorOut(textColorOut);
        wv_year.setTextColorOut(textColorOut);
        wv_hours.setTextColorOut(textColorOut);
        wv_minutes.setTextColorOut(textColorOut);
        wv_seconds.setTextColorOut(textColorOut);
    }

    /**
     * @param isCenterLabel 是否只显示中间选中项的
     */
    public void isCenterLabel(boolean isCenterLabel) {
        wv_day.isCenterLabel(isCenterLabel);
        wv_month.isCenterLabel(isCenterLabel);
        wv_year.isCenterLabel(isCenterLabel);
        wv_hours.isCenterLabel(isCenterLabel);
        wv_minutes.isCenterLabel(isCenterLabel);
        wv_seconds.isCenterLabel(isCenterLabel);
    }

    public void isNeedFormatInt(boolean isNeedFormatInt) {
        wv_day.isNeedFormatInt(isNeedFormatInt);
        wv_month.isNeedFormatInt(isNeedFormatInt);
        wv_year.isNeedFormatInt(isNeedFormatInt);
        wv_hours.isNeedFormatInt(isNeedFormatInt);
        wv_minutes.isNeedFormatInt(isNeedFormatInt);
        wv_seconds.isNeedFormatInt(isNeedFormatInt);
    }

    public void setSelectChangeCallback(ISelectTimeCallback mSelectChangeCallback) {
        this.mSelectChangeCallback = mSelectChangeCallback;
    }

    public void setItemsVisible(int itemsVisibleCount) {
        wv_day.setItemsVisibleCount(itemsVisibleCount);
        wv_month.setItemsVisibleCount(itemsVisibleCount);
        wv_year.setItemsVisibleCount(itemsVisibleCount);
        wv_hours.setItemsVisibleCount(itemsVisibleCount);
        wv_minutes.setItemsVisibleCount(itemsVisibleCount);
        wv_seconds.setItemsVisibleCount(itemsVisibleCount);
    }

    public void setAlphaGradient(boolean isAlphaGradient) {
        wv_day.setAlphaGradient(isAlphaGradient);
        wv_month.setAlphaGradient(isAlphaGradient);
        wv_year.setAlphaGradient(isAlphaGradient);
        wv_hours.setAlphaGradient(isAlphaGradient);
        wv_minutes.setAlphaGradient(isAlphaGradient);
        wv_seconds.setAlphaGradient(isAlphaGradient);
    }

    private int getStartYear() {
        return startDate.get(Calendar.YEAR);
    }

    private int getEndYear() {
        return endDate.get(Calendar.YEAR);
    }

    /**
     * 说明：{@link Calendar} 内部的月份是从0开始的，所以此处的返回值在其基础上做了 +1
     */
    private int getStartMonth() {
        return startDate.get(Calendar.MONTH) + 1;
    }

    /**
     * 说明：{@link Calendar} 内部的月份是从0开始的，所以此处的返回值在其基础上做了 +1
     */
    private int getEndMonth() {
        return endDate.get(Calendar.MONTH) + 1;
    }

    private int getStartDay() {
        return startDate.get(Calendar.DAY_OF_MONTH);
    }

    private int getEndDay() {
        return endDate.get(Calendar.DAY_OF_MONTH);
    }

    private void setEndDay(int endDay) {
        endDate.set(Calendar.DAY_OF_MONTH, endDay);
    }
}
