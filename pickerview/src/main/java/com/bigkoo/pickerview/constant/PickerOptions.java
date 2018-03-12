package com.bigkoo.pickerview.constant;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.contrarywind.view.WheelView;

import java.util.Calendar;

/**
 * Created by xiaosongzeem on 2018/3/8.
 */

public class PickerOptions {

    //常量
    public static final int PICKERVIEW_BTN_NORMAL = 0xFF057dff;
    public static final int PICKERVIEW_BTN_PRESS = 0xFFc2daf5;
    public static final int PICKERVIEW_BG_TOPBAR = 0xFFf5f5f5;
    public static final int PICKERVIEW_TOPBAR_TITLE = 0xFF000000;
    public static final int PICKERVIEW_BG_COLOR_DEFAULT = 0xFFFFFFFF;

    public static final int TYPE_PICKER_OPTIONS = 1;
    public static final int TYPE_PICKER_TIME = 2;


    public OptionsPickerView.OnOptionsSelectListener optionsSelectListener;
    public TimePickerView.OnTimeSelectListener timeSelectListener;
    public CustomListener customListener;

    public int layoutRes;
    public ViewGroup decorView;
    public int textGravity = Gravity.CENTER;
    public Context context;


    //options picker
    public String label1;
    public String label2;
    public String label3;

    public boolean cyclic1 = false;//是否循环，默认否
    public boolean cyclic2 = false;
    public boolean cyclic3 = false;

    public int option1;//默认选中项
    public int option2;
    public int option3;

    public int xoffset_one;//x轴偏移量
    public int xoffset_two;
    public int xoffset_three;
    public boolean linkage;


    //time picker
    public boolean[] type = new boolean[]{true, true, true, true, true, true};//显示类型 默认全部显示

    public Calendar date;//当前选中时间
    public Calendar startDate;//开始时间
    public Calendar endDate;//终止时间
    public int startYear;//开始年份
    public int endYear;//结尾年份

    public boolean cyclic = false;//是否循环
    public boolean isLunarCalendar = false;//是否显示农历

    public String label_year, label_month, label_day, label_hours, label_mins, label_seconds;//单位
    public int xoffset_year, xoffset_month, xoffset_day, xoffset_hours, xoffset_mins, xoffset_seconds;//单位



    public PickerOptions(int buildType) {
        if (buildType == TYPE_PICKER_OPTIONS) {
            layoutRes = R.layout.pickerview_options;
        } else {
            layoutRes = R.layout.pickerview_time;
        }
    }

    //******* 公有字段，抽取到BasePickerView里  ******//
    public String textContentConfirm;//确定按钮文字
    public String textContentCancel;//取消按钮文字
    public String textContentTitle;//标题文字

    public int textColorConfirm = PICKERVIEW_BTN_NORMAL;//确定按钮颜色
    public int textColorCancel = PICKERVIEW_BTN_NORMAL;//取消按钮颜色
    public int textColorTitle = PICKERVIEW_TOPBAR_TITLE;//标题颜色

    public int bgColorWheel = PICKERVIEW_BG_COLOR_DEFAULT;//滚轮背景颜色
    public int bgColorTitle = PICKERVIEW_BG_TOPBAR;//标题背景颜色

    public int textSizeSubmitCancel = 17;//确定取消按钮大小
    public int textSizeTitle = 18;//标题文字大小
    public int textSizeContent = 18;//内容文字大小

    public int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    public int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public int backgroundId = -1; //显示时的外部背景色颜色,默认是灰色

    // 条目间距倍数 默认1.6
    public float lineSpacingMultiplier = 1.6f;
    public boolean isDialog;//是否是对话框模式

    public boolean cancelable = true;//是否能取消
    public boolean isCenterLabel = true;//是否只显示中间的label
    public Typeface font = Typeface.MONOSPACE;//字体样式
    public WheelView.DividerType dividerType = WheelView.DividerType.FILL;//分隔线类型


}
