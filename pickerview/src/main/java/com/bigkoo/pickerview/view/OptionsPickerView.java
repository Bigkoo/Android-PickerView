package com.bigkoo.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.configure.PickerOptions;

import java.util.List;

/**
 * 条件选择器
 * Created by Sai on 15/11/22.
 */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {

    private WheelOptions wheelOptions;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";


    public OptionsPickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
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

        wheelOptions = new WheelOptions(optionsPicker, mPickerOptions.isRestoreItem);
        if (mPickerOptions.optionsSelectChangeListener != null) {
            wheelOptions.setOptionsSelectChangeListener(mPickerOptions.optionsSelectChangeListener);
        }

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
     * 动态设置标题
     *
     * @param text 标题文本内容
     */
    public void setTitleText(String text) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
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

        wheelOptions.setLinkage(false);
        wheelOptions.setNPicker(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        } else if (tag.equals(TAG_CANCEL)) {
            if (mPickerOptions.cancelListener != null) {
                mPickerOptions.cancelListener.onClick(v);
            }
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


    @Override
    public boolean isDialog() {
        return mPickerOptions.isDialog;
    }
}
