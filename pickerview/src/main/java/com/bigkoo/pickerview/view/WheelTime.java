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
    private static final int DEFAULT_START_HOUR = 0;
    private static final int DEFAULT_END_HOUR = 23;
    private static final int DEFAULT_START_MINUTE = 0;
    private static final int DEFAULT_END_MINUTE = 59;
    private static final int DEFAULT_START_SECOND = 0;
    private static final int DEFAULT_END_SECOND = 59;

    /**
     * 大月月份
     */
    private static final List<String> LIST_BIG_MONTH = Arrays.asList("1", "3", "5", "7", "8", "10", "12");
    /**
     * 小月月份（2月需单独处理，故不计在内）
     */
    private static final List<String> LIST_LITTLE_MONTH = Arrays.asList("4", "6", "9", "11");

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
    private int currentMonth;
    private int currentDay;
    private int currentHour;

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
                DEFAULT_START_DAY,
                DEFAULT_START_HOUR,
                DEFAULT_START_MINUTE,
                DEFAULT_START_SECOND);
        endDate.set(DEFAULT_END_YEAR,
                DEFAULT_END_MONTH,
                DEFAULT_END_DAY,
                DEFAULT_END_HOUR,
                DEFAULT_END_MINUTE,
                DEFAULT_END_SECOND);
    }

    public boolean isLunarMode() {
        return isLunarCalendar;
    }

    public void setLunarMode(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public void setPicker(Calendar date) {
        if (isLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH) + 1,
                    date.get(Calendar.DAY_OF_MONTH));
            Calendar lunarDate = (Calendar) date.clone();
            lunarDate.set(lunar[0], lunar[1] - 1, lunar[2]);
            setLunar(lunarDate, lunar[3] == 1);
        } else {
            setSolar(date);
        }
    }

    /**
     * 设置农历
     *
     * @param date
     * @param isLeap
     */
    private void setLunar(Calendar date, boolean isLeap) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int h = date.get(Calendar.HOUR_OF_DAY);
        int m = date.get(Calendar.MINUTE);
        int s = date.get(Calendar.SECOND);
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
                handleLunarYearItemSelected(index);
            }
        });

        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                handleLunarMonthItemSelected(index);
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

    private void handleLunarYearItemSelected(int index) {
        int year_num = index + getStartYear();
        int currentMonthItem;
        // 判断是不是闰年,来确定月和日的选择
        wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
        if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
            currentMonthItem = wv_month.getCurrentItem() + 1;
            wv_month.setCurrentItem(currentMonthItem);
        } else {
            currentMonthItem = wv_month.getCurrentItem();
            wv_month.setCurrentItem(currentMonthItem);
        }

        //重新设置日
        handleLunarMonthItemSelected(currentMonthItem);
    }

    private void handleLunarMonthItemSelected(int index) {
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

    /**
     * 设置公历
     *
     * @param date
     */
    private void setSolar(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int h = date.get(Calendar.HOUR_OF_DAY);

        currentYear = year;
        currentMonth = month + 1;
        currentDay = day;
        currentHour = h;

        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(getStartYear(), getEndYear()));// 设置"年"的显示数据


        wv_year.setCurrentItem(year - getStartYear());// 初始化时显示的数据
        wv_year.setGravity(gravity);
        // 月
        setSolarMonth(date);
        // 日
        setSolarDay(date);
        // 时
        setSolarHour(date);
        // 分
        setSolarMinute(date);
        // 秒
        setSolarSecond(date);

        // 添加"年"监听
        wv_year.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                handleSolarYearItemSelected(index);
            }
        });

        // 添加"月"监听
        wv_month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                handleSolarMonthItemSelected(index);
            }
        });

        // 添加"日"监听
        wv_day.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                handleSolarDayItemSelected(index);
            }
        });

        // 添加"时"监听
        wv_hours.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                handleSolarHourItemSelected(index);
            }
        });

        // 添加"分"监听
        wv_minutes.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                handleSolarMinuteItemSelected(index);
            }
        });

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

    private void setSolarMonth(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);

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
    }

    private void setSolarDay(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        wv_day = (WheelView) view.findViewById(R.id.day);

        boolean leapYear = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
        if (getStartYear() == getEndYear() && getStartMonth() == getEndMonth()) {
            if (LIST_BIG_MONTH.contains(String.valueOf(month + 1))) {
                if (getEndDay() > 31) {
                    setEndDay(31);
                }
                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), getEndDay()));
            } else if (LIST_LITTLE_MONTH.contains(String.valueOf(month + 1))) {
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
            if (LIST_BIG_MONTH.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), 31));
            } else if (LIST_LITTLE_MONTH.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), 30));
            } else {
                // 闰年 29，平年 28
                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), leapYear ? 29 : 28));
            }
            wv_day.setCurrentItem(day - getStartDay());
        } else if (year == getEndYear() && month + 1 == getEndMonth()) {
            // 终止日期的天数控制
            if (LIST_BIG_MONTH.contains(String.valueOf(month + 1))) {
                if (getEndDay() > 31) {
                    setEndDay(31);
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, getEndDay()));
            } else if (LIST_LITTLE_MONTH.contains(String.valueOf(month + 1))) {
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
            if (LIST_BIG_MONTH.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (LIST_LITTLE_MONTH.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年 29，平年 28
                wv_day.setAdapter(new NumericWheelAdapter(getStartDay(), leapYear ? 29 : 28));
            }
            wv_day.setCurrentItem(day - 1);
        }

        wv_day.setGravity(gravity);
    }

    private void setSolarHour(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int h = date.get(Calendar.HOUR_OF_DAY);

        int startHour = DEFAULT_START_HOUR;
        int endHour = DEFAULT_END_HOUR;
        int currentItem = h;

        if (year == getStartYear()
                && month + 1 == getStartMonth()
                && day == getStartDay()) {
            // 起始日期的小时控制
            startHour = getStartHour();
            currentItem = h - getStartHour();
        }
        if (year == getEndYear()
                && month + 1 == getEndMonth()
                && day == getEndDay()) {
            // 终止日期的小时控制
            endHour = getEndHour();
        }

        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(startHour, endHour));
        wv_hours.setCurrentItem(currentItem);
        wv_hours.setGravity(gravity);
    }

    private void setSolarMinute(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int h = date.get(Calendar.HOUR_OF_DAY);
        int m = date.get(Calendar.MINUTE);

        int startMinute = DEFAULT_START_MINUTE;
        int endMinute = DEFAULT_END_MINUTE;
        int currentItem = m;

        if (year == getStartYear()
                && month + 1 == getStartMonth()
                && day == getStartDay()
                && h == getStartHour()) {
            // 起始日期的分钟控制
            startMinute = getStartMinute();
            currentItem = m - getStartMinute();
        }
        if (year == getEndYear()
                && month + 1 == getEndMonth()
                && day == getEndDay()
                && h == getEndHour()) {
            // 终止日期的分钟控制
            endMinute = getEndMinute();
        }

        wv_minutes = (WheelView) view.findViewById(R.id.min);
        wv_minutes.setAdapter(new NumericWheelAdapter(startMinute, endMinute));
        wv_minutes.setCurrentItem(currentItem);
        wv_minutes.setGravity(gravity);
    }

    private void setSolarSecond(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int h = date.get(Calendar.HOUR_OF_DAY);
        int m = date.get(Calendar.MINUTE);
        int s = date.get(Calendar.SECOND);

        int startSecond = DEFAULT_START_SECOND;
        int endSecond = DEFAULT_END_SECOND;
        int currentItem = s;

        if (year == getStartYear()
                && month + 1 == getStartMonth()
                && day == getStartDay()
                && h == getStartHour()
                && m == getStartMinute()) {
            // 起始日期的秒控制
            startSecond = getStartSecond();
            currentItem = s - getStartSecond();
        }
        if (year == getEndYear()
                && month + 1 == getEndMonth()
                && day == getEndDay()
                && h == getEndHour()
                && m == getEndMinute()) {
            // 终止日期的秒控制
            endSecond = getEndSecond();
        }

        wv_seconds = (WheelView) view.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(startSecond, endSecond));
        wv_seconds.setCurrentItem(currentItem);
        wv_seconds.setGravity(gravity);
    }

    private void handleSolarYearItemSelected(int index) {
        int year = (int) wv_year.getAdapter().getItem(index);
        currentYear = year;
        int startMonth = DEFAULT_START_MONTH + 1;
        int endMonth = DEFAULT_END_MONTH + 1;
        if (year == getStartYear()) {//等于开始的年
            // 重新设置起始月份
            startMonth = getStartMonth();
        }
        if (year == getEndYear()) {
            // 重新设置终止月份
            endMonth = getEndMonth();
        }

        int currentMonthItem = wv_month.getCurrentItem();//记录上一次的item位置
        wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
        // 值域变化可能导致选中项越界
        currentMonthItem = Math.min(currentMonthItem,
                wv_month.getAdapter().getItemsCount() - 1);
        wv_month.setCurrentItem(currentMonthItem);

        // 重新设置日
        handleSolarMonthItemSelected(currentMonthItem);
    }

    private void handleSolarMonthItemSelected(int index) {
        int month = (int) wv_month.getAdapter().getItem(index);
        currentMonth = month;
        int startDay = DEFAULT_START_DAY;
        int endDay = DEFAULT_END_DAY;

        if (currentYear == getStartYear() && month == getStartMonth()) {
            // 重新设置起始日
            startDay = getStartDay();
        }
        if (currentYear == getEndYear() && month == getEndMonth()) {
            // 重新设置终止日
            endDay = getEndDay();
        }

        // 调整终止日为合法值
        if (LIST_BIG_MONTH.contains(String.valueOf(month))) {
            endDay = Math.min(endDay, 31);
        } else if (LIST_LITTLE_MONTH.contains(String.valueOf(month))) {
            endDay = Math.min(endDay, 30);
        } else {
            if ((currentYear % 4 == 0 && currentYear % 100 != 0)
                    || currentYear % 400 == 0) {
                endDay = Math.min(endDay, 29);
            } else {
                endDay = Math.min(endDay, 28);
            }
        }

        int currentDayItem = wv_day.getCurrentItem();
        wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
        // 值域变化可能导致选中项越界
        currentDayItem = Math.min(currentDayItem,
                wv_day.getAdapter().getItemsCount() - 1);
        wv_day.setCurrentItem(currentDayItem);

        handleSolarDayItemSelected(currentDayItem);
    }

    private void handleSolarDayItemSelected(int index) {
        int day = (int) wv_day.getAdapter().getItem(index);
        currentDay = day;
        int startHour = DEFAULT_START_HOUR;
        int endHour = DEFAULT_END_HOUR;
        if (currentYear == getStartYear()
                && currentMonth == getStartMonth()
                && day == getStartDay()) {
            // 重新设置起始小时
            startHour = getStartHour();
        }
        if (currentYear == getEndYear()
                && currentMonth == getEndMonth()
                && day == getEndDay()) {
            // 重新设置终止小时
            endHour = getEndHour();
        }

        // 记录上一次的item位置
        int currentHourItem = wv_hours.getCurrentItem();
        wv_hours.setAdapter(new NumericWheelAdapter(startHour, endHour));
        // 值域变化可能导致选中项越界
        currentHourItem = Math.min(currentHourItem,
                wv_hours.getAdapter().getItemsCount() - 1);
        wv_hours.setCurrentItem(currentHourItem);

        handleSolarHourItemSelected(currentHourItem);
    }

    private void handleSolarHourItemSelected(int index) {
        int hour = (int) wv_hours.getAdapter().getItem(index);
        currentHour = hour;
        int startMinute = DEFAULT_START_MINUTE;
        int endMinute = DEFAULT_END_MINUTE;
        if (currentYear == getStartYear()
                && currentMonth == getStartMonth()
                && currentDay == getStartDay()
                && hour == getStartHour()) {
            // 重新设置起始分钟
            startMinute = getStartMinute();
        }
        if (currentYear == getEndYear()
                && currentMonth == getEndMonth()
                && currentDay == getEndDay()
                && hour == getEndHour()) {
            // 重新设置终止分钟
            endMinute = getEndMinute();
        }

        // 记录上一次的item位置
        int currentMinuteItem = wv_minutes.getCurrentItem();
        wv_minutes.setAdapter(new NumericWheelAdapter(startMinute, endMinute));
        // 值域变化可能导致选中项越界
        currentMinuteItem = Math.min(currentMinuteItem,
                wv_minutes.getAdapter().getItemsCount() - 1);
        wv_minutes.setCurrentItem(currentMinuteItem);

        handleSolarMinuteItemSelected(currentMinuteItem);
    }

    private void handleSolarMinuteItemSelected(int index) {
        int minute = (int) wv_minutes.getAdapter().getItem(index);
        int startSecond = DEFAULT_START_SECOND;
        int endSecond = DEFAULT_END_SECOND;
        if (currentYear == getStartYear()
                && currentMonth == getStartMonth()
                && currentDay == getStartDay()
                && currentHour == getStartHour()
                && minute == getStartMinute()) {
            // 重新设置起始秒
            startSecond = getStartSecond();
        }
        if (currentYear == getEndYear()
                && currentMonth == getEndMonth()
                && currentDay == getEndDay()
                && currentHour == getEndHour()
                && minute == getEndMinute()) {
            // 重新设置终止秒
            endSecond = getEndSecond();
        }

        // 记录上一次的item位置
        int currentSecondItem = wv_seconds.getCurrentItem();
        wv_seconds.setAdapter(new NumericWheelAdapter(startSecond, endSecond));
        // 值域变化可能导致选中项越界
        currentSecondItem = Math.min(currentSecondItem,
                wv_seconds.getAdapter().getItemsCount() - 1);
        wv_seconds.setCurrentItem(currentSecondItem);

        if (mSelectChangeCallback != null) {
            mSelectChangeCallback.onTimeSelectChanged();
        }
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
        sb.append(getCurrentItemValue(wv_year)).append("-")
                .append(getCurrentItemValue(wv_month)).append("-")
                .append(getCurrentItemValue(wv_day)).append(" ")
                .append(getCurrentItemValue(wv_hours)).append(":")
                .append(getCurrentItemValue(wv_minutes)).append(":")
                .append(getCurrentItemValue(wv_seconds));
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
                .append(getCurrentItemValue(wv_hours)).append(":")
                .append(getCurrentItemValue(wv_minutes)).append(":")
                .append(getCurrentItemValue(wv_seconds));
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

    private int getStartHour() {
        return startDate.get(Calendar.HOUR_OF_DAY);
    }

    private int getEndHour() {
        return endDate.get(Calendar.HOUR_OF_DAY);
    }

    private int getStartMinute() {
        return startDate.get(Calendar.MINUTE);
    }

    private int getEndMinute() {
        return endDate.get(Calendar.MINUTE);
    }

    private int getStartSecond() {
        return startDate.get(Calendar.SECOND);
    }

    private int getEndSecond() {
        return endDate.get(Calendar.SECOND);
    }

    private Object getCurrentItemValue(WheelView wv) {
        int currentItem = wv.getCurrentItem();
        return wv.getAdapter().getItem(currentItem);
    }
}
