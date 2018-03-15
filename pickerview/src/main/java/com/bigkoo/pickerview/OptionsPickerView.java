package com.bigkoo.pickerview;

import android.content.Context;
import android.graphics.Typeface;
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
import com.bigkoo.pickerview.view.WheelOptions;
import com.contrarywind.view.WheelView;

import java.util.List;

/**
 * 条件选择器
 * Created by Sai on 15/11/22.
 */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {

    private WheelOptions wheelOptions;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";


    //构造方法
    public OptionsPickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }


    //建造器
    public static class Builder {

        //配置类
        private PickerOptions mPickerOptions;


        //Required
        public Builder(Context context, OnOptionsSelectListener listener) {
            mPickerOptions = new PickerOptions(PickerOptions.TYPE_PICKER_OPTIONS);
            mPickerOptions.context = context;
            mPickerOptions.optionsSelectListener = listener;
        }

        //Option
        public Builder setSubmitText(String textContentCancel) {
            mPickerOptions.textContentCancel = textContentCancel;
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

        public Builder isDialog(boolean isDialog) {
            mPickerOptions.isDialog = isDialog;
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
         * 显示时的外部背景色颜色,默认是灰色
         *
         * @param backgroundId  color resId.
         * @return
         */
        public Builder setBackgroundId(int backgroundId) {
            mPickerOptions.backgroundId = backgroundId;
            return this;
        }

        /**
         * ViewGroup 类型
         * 设置PickerView的显示容器
         *
         * @param decorView  Parent View.
         * @return
         */
        public Builder setDecorView(ViewGroup decorView) {
            mPickerOptions.decorView = decorView;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener listener) {
            mPickerOptions.layoutRes = res;
            mPickerOptions.customListener = listener;
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

        public Builder setOutSideCancelable(boolean cancelable) {
            mPickerOptions.cancelable = cancelable;
            return this;
        }


        public Builder setLabels(String label1, String label2, String label3) {
            mPickerOptions.label1 = label1;
            mPickerOptions.label2 = label2;
            mPickerOptions.label3 = label3;
            return this;
        }

        /**
         * 设置Item 的间距倍数，用于控制 Item 高度间隔
         *
         * @param lineSpacingMultiplier  浮点型，1.2-4.0f 之间有效,超过则取极值。
         */
        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier) {
            mPickerOptions.lineSpacingMultiplier = lineSpacingMultiplier;
            return this;
        }

        /**
         * Set item divider line type color.
         *
         * @param dividerColor color resId.
         */
        public Builder setDividerColor(int dividerColor) {
            mPickerOptions.dividerColor = dividerColor;
            return this;
        }

        /**
         * Set item divider line type.
         *
         * @param dividerType enum Type {@link WheelView.DividerType}
         */
        public Builder setDividerType(WheelView.DividerType dividerType) {
            mPickerOptions.dividerType = dividerType;
            return this;
        }

        /**
         * Set the textColor of selected item.
         *
         * @param textColorCenter color res.
         */
        public Builder setTextColorCenter(int textColorCenter) {
            mPickerOptions.textColorCenter = textColorCenter;
            return this;
        }

        /**
         * Set the textColor of outside item.
         *
         * @param textColorOut color resId.
         */
        public Builder setTextColorOut(int textColorOut) {
            mPickerOptions.textColorOut = textColorOut;
            return this;
        }

        public Builder setTypeface(Typeface font) {
            mPickerOptions.font = font;
            return this;
        }

        public Builder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
            mPickerOptions.cyclic1 = cyclic1;
            mPickerOptions.cyclic2 = cyclic2;
            mPickerOptions.cyclic3 = cyclic3;
            return this;
        }

        public Builder setSelectOptions(int option1) {
            mPickerOptions.option1 = option1;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2) {
            mPickerOptions.option1 = option1;
            mPickerOptions.option2 = option2;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2, int option3) {
            mPickerOptions.option1 = option1;
            mPickerOptions.option2 = option2;
            mPickerOptions.option3 = option3;
            return this;
        }

        public Builder setTextXOffset(int xoffset_one, int xoffset_two, int xoffset_three) {
            mPickerOptions.x_offset_one = xoffset_one;
            mPickerOptions.x_offset_two = xoffset_two;
            mPickerOptions.x_offset_three = xoffset_three;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            mPickerOptions.isCenterLabel = isCenterLabel;
            return this;
        }

        /**
         *  切换选项时，是否还原第一项
         * @param isRestoreItem true：还原； false: 保持上一个选项
         * @return Builder
         */
        public Builder isRestoreItem(boolean isRestoreItem) {
            mPickerOptions.isRestoreItem = isRestoreItem;
            return this;
        }

        public OptionsPickerView build() {
            return new OptionsPickerView(mPickerOptions);
        }
    }


    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        init();
        initEvents();
        if (mPickerOptions.customListener == null) {
            LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer);

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

        // ----滚轮布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.optionspicker);
        optionsPicker.setBackgroundColor(mPickerOptions.bgColorWheel);

        wheelOptions = new WheelOptions(optionsPicker, mPickerOptions.linkage, mPickerOptions.isRestoreItem);
        wheelOptions.setTextContentSize(mPickerOptions.textSizeContent);
        wheelOptions.setLabels(mPickerOptions.label1, mPickerOptions.label2, mPickerOptions.label3);
        wheelOptions.setTextXOffset(mPickerOptions.x_offset_one, mPickerOptions.x_offset_two, mPickerOptions.x_offset_three);

        wheelOptions.setCyclic(mPickerOptions.cyclic1, mPickerOptions.cyclic2, mPickerOptions.cyclic3);
        wheelOptions.setTypeface(mPickerOptions.font);

        setOutSideCancelable(mPickerOptions.cancelable);


        wheelOptions.setDividerColor(mPickerOptions.dividerColor);
        wheelOptions.setDividerType(mPickerOptions.dividerType);
        wheelOptions.setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        wheelOptions.setTextColorOut(mPickerOptions.textColorOut);
        wheelOptions.setTextColorCenter(mPickerOptions.textColorCenter);
        wheelOptions.isCenterLabel(mPickerOptions.isCenterLabel);
    }


    /**
     * 设置默认选中项
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        mPickerOptions.option1 = option1;
        reSetCurrentItems();
    }


    public void setSelectOptions(int option1, int option2) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        mPickerOptions.option1 = option1;
        mPickerOptions.option2 = option2;
        mPickerOptions.option3 = option3;
        reSetCurrentItems();
    }

    private void reSetCurrentItems() {
        if (wheelOptions != null) {
            wheelOptions.setCurrentItems(mPickerOptions.option1, mPickerOptions.option2, mPickerOptions.option3);
        }
    }

    public void setPicker(List<T> optionsItems) {
        this.setPicker(optionsItems, null, null);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items) {
        this.setPicker(options1Items, options2Items, null);
    }

    public void setPicker(List<T> options1Items,
                          List<List<T>> options2Items,
                          List<List<List<T>>> options3Items) {

        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }


    //不联动情况下调用
    public void setNPicker(List<T> options1Items,
                           List<T> options2Items,
                           List<T> options3Items) {

        wheelOptions.setNPicker(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    //抽离接口回调的方法
    public void returnData() {
        if (mPickerOptions.optionsSelectListener != null) {
            int[] optionsCurrentItems = wheelOptions.getCurrentItems();
            mPickerOptions.optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], clickView);
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int options2, int options3, View v);
    }

    @Override
    public boolean isDialog() {
        return mPickerOptions.isDialog;
    }
}
