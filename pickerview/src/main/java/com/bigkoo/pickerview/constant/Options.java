package com.bigkoo.pickerview.constant;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.listener.CustomListener;
import com.contrarywind.view.WheelView;

/**
 * Created by xiaosong on 2018/3/8.
 */

public class Options {


    public Context context;
    public OptionsPickerView.OnOptionsSelectListener optionsSelectListener;
    public ViewGroup decorView;
    
    
    public CustomListener customListener;

    //options picker
    public String label1;
    public String label2;
    public String label3;
    
    public int layoutRes = R.layout.pickerview_options;
    
    public boolean cyclic1 = false;//是否循环，默认否
    public boolean cyclic2 = false;
    public boolean cyclic3 = false;

    public int option1;//默认选中项
    public int option2;
    public int option3;

    public int xoffset_one;//x轴偏移量
    public int xoffset_two;
    public int xoffset_three;
    public Boolean linkage;


    public Options() {
    }

    //******* 公有字段，抽取到BasePickerView里  ******//
    public String textContentConfirm;//确定按钮文字
    public String textContentCancel;//取消按钮文字
    public String textContentTitle;//标题文字

    public int textColorConfirm;//确定按钮颜色
    public int textColorCancel;//取消按钮颜色
    public int textColorTitle;//标题颜色

    public int bgColorWheel;//滚轮背景颜色
    public int bgColorTitle;//标题背景颜色

    public int textSizeSubmitCancel;//确定取消按钮大小
    public int textSizeTitle;//标题文字大小
    public int textSizeContent;//内容文字大小

    public int textColorOut; //分割线以外的文字颜色
    public int textColorCenter; //分割线之间的文字颜色
    public int dividerColor; //分割线的颜色
    public int backgroundId; //显示时的外部背景色颜色,默认是灰色

    // 条目间距倍数 默认1.6
    public float lineSpacingMultiplier;
    public boolean isDialog;//是否是对话框模式

    public boolean cancelable;//是否能取消
    public boolean isCenterLabel;//是否只显示中间的label
    public Typeface font;//字体样式
    public WheelView.DividerType dividerType;//分隔线类型

}
