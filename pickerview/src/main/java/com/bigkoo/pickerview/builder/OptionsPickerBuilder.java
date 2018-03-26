package com.bigkoo.pickerview.builder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.view.WheelView;

/**
 * Created by xiaosongzeem on 2018/3/20.
 */

public class OptionsPickerBuilder {

    //配置类
    private PickerOptions mPickerOptions;


    //Required
    public OptionsPickerBuilder(Context context, OnOptionsSelectListener listener) {
        mPickerOptions = new PickerOptions(PickerOptions.TYPE_PICKER_OPTIONS);
        mPickerOptions.context = context;
        mPickerOptions.optionsSelectListener = listener;
    }

    //Option
    public OptionsPickerBuilder setSubmitText(String textContentConfirm) {
        mPickerOptions.textContentConfirm = textContentConfirm;
        return this;
    }

    public OptionsPickerBuilder setCancelText(String textContentCancel) {
        mPickerOptions.textContentCancel = textContentCancel;
        return this;
    }

    public OptionsPickerBuilder setTitleText(String textContentTitle) {
        mPickerOptions.textContentTitle = textContentTitle;
        return this;
    }

    public OptionsPickerBuilder isDialog(boolean isDialog) {
        mPickerOptions.isDialog = isDialog;
        return this;
    }

    public OptionsPickerBuilder setSubmitColor(int textColorConfirm) {
        mPickerOptions.textColorConfirm = textColorConfirm;
        return this;
    }

    public OptionsPickerBuilder setCancelColor(int textColorCancel) {
        mPickerOptions.textColorCancel = textColorCancel;
        return this;
    }

    /**
     * 显示时的外部背景色颜色,默认是灰色
     *
     * @param backgroundId color resId.
     * @return
     */
    public OptionsPickerBuilder setBackgroundId(int backgroundId) {
        mPickerOptions.backgroundId = backgroundId;
        return this;
    }

    /**
     * ViewGroup 类型
     * 设置PickerView的显示容器
     *
     * @param decorView Parent View.
     * @return
     */
    public OptionsPickerBuilder setDecorView(ViewGroup decorView) {
        mPickerOptions.decorView = decorView;
        return this;
    }

    public OptionsPickerBuilder setLayoutRes(int res, CustomListener listener) {
        mPickerOptions.layoutRes = res;
        mPickerOptions.customListener = listener;
        return this;
    }

    public OptionsPickerBuilder setBgColor(int bgColorWheel) {
        mPickerOptions.bgColorWheel = bgColorWheel;
        return this;
    }

    public OptionsPickerBuilder setTitleBgColor(int bgColorTitle) {
        mPickerOptions.bgColorTitle = bgColorTitle;
        return this;
    }

    public OptionsPickerBuilder setTitleColor(int textColorTitle) {
        mPickerOptions.textColorTitle = textColorTitle;
        return this;
    }

    public OptionsPickerBuilder setSubCalSize(int textSizeSubmitCancel) {
        mPickerOptions.textSizeSubmitCancel = textSizeSubmitCancel;
        return this;
    }

    public OptionsPickerBuilder setTitleSize(int textSizeTitle) {
        mPickerOptions.textSizeTitle = textSizeTitle;
        return this;
    }

    public OptionsPickerBuilder setContentTextSize(int textSizeContent) {
        mPickerOptions.textSizeContent = textSizeContent;
        return this;
    }

    public OptionsPickerBuilder setOutSideCancelable(boolean cancelable) {
        mPickerOptions.cancelable = cancelable;
        return this;
    }


    public OptionsPickerBuilder setLabels(String label1, String label2, String label3) {
        mPickerOptions.label1 = label1;
        mPickerOptions.label2 = label2;
        mPickerOptions.label3 = label3;
        return this;
    }

    /**
     * 设置Item 的间距倍数，用于控制 Item 高度间隔
     *
     * @param lineSpacingMultiplier 浮点型，1.0-4.0f 之间有效,超过则取极值。
     */
    public OptionsPickerBuilder setLineSpacingMultiplier(float lineSpacingMultiplier) {
        mPickerOptions.lineSpacingMultiplier = lineSpacingMultiplier;
        return this;
    }

    /**
     * Set item divider line type color.
     *
     * @param dividerColor color resId.
     */
    public OptionsPickerBuilder setDividerColor(int dividerColor) {
        mPickerOptions.dividerColor = dividerColor;
        return this;
    }

    /**
     * Set item divider line type.
     *
     * @param dividerType enum Type {@link WheelView.DividerType}
     */
    public OptionsPickerBuilder setDividerType(WheelView.DividerType dividerType) {
        mPickerOptions.dividerType = dividerType;
        return this;
    }

    /**
     * Set the textColor of selected item.
     *
     * @param textColorCenter color res.
     */
    public OptionsPickerBuilder setTextColorCenter(int textColorCenter) {
        mPickerOptions.textColorCenter = textColorCenter;
        return this;
    }

    /**
     * Set the textColor of outside item.
     *
     * @param textColorOut color resId.
     */
    public OptionsPickerBuilder setTextColorOut(int textColorOut) {
        mPickerOptions.textColorOut = textColorOut;
        return this;
    }

    public OptionsPickerBuilder setTypeface(Typeface font) {
        mPickerOptions.font = font;
        return this;
    }

    public OptionsPickerBuilder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        mPickerOptions.cyclic1 = cyclic1;
        mPickerOptions.cyclic2 = cyclic2;
        mPickerOptions.cyclic3 = cyclic3;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int option1) {
        mPickerOptions.option1 = option1;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int option1, int option2) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int option1, int option2, int option3) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        mPickerOptions.option3 = option3;
        return this;
    }

    public OptionsPickerBuilder setTextXOffset(int xoffset_one, int xoffset_two, int xoffset_three) {
        mPickerOptions.x_offset_one = xoffset_one;
        mPickerOptions.x_offset_two = xoffset_two;
        mPickerOptions.x_offset_three = xoffset_three;
        return this;
    }

    public OptionsPickerBuilder isCenterLabel(boolean isCenterLabel) {
        mPickerOptions.isCenterLabel = isCenterLabel;
        return this;
    }

    /**
     * 切换选项时，是否还原第一项
     *
     * @param isRestoreItem true：还原； false: 保持上一个选项
     * @return TimePickerBuilder
     */
    public OptionsPickerBuilder isRestoreItem(boolean isRestoreItem) {
        mPickerOptions.isRestoreItem = isRestoreItem;
        return this;
    }

    /**
     * @param listener 切换item项滚动停止时，实时回调监听。
     * @return
     */
    public OptionsPickerBuilder setOptionsSelectChangeListener(OnOptionsSelectChangeListener listener) {
        mPickerOptions.optionsSelectChangeListener = listener;
        return this;
    }


    public <T> OptionsPickerView<T> build() {
        return new OptionsPickerView<>(mPickerOptions);
    }
}
