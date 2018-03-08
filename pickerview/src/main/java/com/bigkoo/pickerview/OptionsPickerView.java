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

import com.bigkoo.pickerview.constant.Options;
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

    private WheelOptions<T> wheelOptions;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private Options mOptions;


    //构造方法
    public OptionsPickerView(Options options) {
        super(options.context);
        mOptions = options;
        initView(options.context);
    }


    //建造器
    public static class Builder {

        // 通用部分, 后续创建一个BaseBuilder
        private int layoutRes = R.layout.pickerview_options;
        private CustomListener customListener;
        private Context context;
        private String Str_Submit;//确定按钮文字
        private String Str_Cancel;//取消按钮文字
        private String Str_Title;//标题文字

        private int Color_Submit;//确定按钮颜色
        private int Color_Cancel;//取消按钮颜色
        private int Color_Title;//标题颜色

        private int Color_Background_Wheel;//滚轮背景颜色
        private int Color_Background_Title;//标题背景颜色

        private int Size_Submit_Cancel = 17;//确定取消按钮大小
        private int Size_Title = 18;//标题文字大小
        private int Size_Content = 18;//内容文字大小
        private boolean cancelable = true;//是否能取消
        private boolean isCenterLabel = true;//是否只显示中间的label

        private int textColorOut; //分割线以外的文字颜色
        private int textColorCenter; //分割线之间的文字颜色
        private int dividerColor; //分割线的颜色
        private int backgroundId = -1; //显示时的外部背景色颜色,默认是灰色
        public ViewGroup decorView;//显示pickerview的根View,默认是activity的根view
        private float lineSpacingMultiplier = 1.6F;// 条目间距倍数 默认1.6
        private boolean isDialog;//是否是对话框模式
        private Typeface font;
        private WheelView.DividerType dividerType;//分隔线类型


        //专用部分
        private OnOptionsSelectListener optionsSelectListener;
        private boolean linkage = true;//是否联动




        //配置类
        private Options mOptions;


        //Required
        public Builder(Context context, OnOptionsSelectListener listener) {
            mOptions = new Options();
            mOptions.context = context;
            mOptions.optionsSelectListener = listener;
        }

        //Option
        public Builder setSubmitText(String textContentCancel) {
            mOptions.textContentCancel = textContentCancel;
            return this;
        }

        public Builder setCancelText(String textContentCancel) {
            mOptions.textContentCancel = textContentCancel;
            return this;
        }

        public Builder setTitleText(String textContentTitle) {
            mOptions.textContentTitle = textContentTitle;
            return this;
        }

        public Builder isDialog(boolean isDialog) {
            mOptions.isDialog = isDialog;
            return this;
        }

        public Builder setSubmitColor(int textColorConfirm) {
            mOptions.textColorConfirm = textColorConfirm;
            return this;
        }

        public Builder setCancelColor(int textColorCancel) {
            mOptions.textColorCancel = textColorCancel;
            return this;
        }

        /**
         * 显示时的外部背景色颜色,默认是灰色
         *
         * @param backgroundId
         * @return
         */
        public Builder setBackgroundId(int backgroundId) {
            mOptions.backgroundId = backgroundId;
            return this;
        }

        /**
         * 必须是viewgroup
         * 设置要将pickerview显示到的容器
         *
         * @param decorView
         * @return
         */
        public Builder setDecorView(ViewGroup decorView) {
            mOptions.decorView = decorView;
            return this;
        }

        public Builder setLayoutRes(int res, CustomListener listener) {
            mOptions.layoutRes = res;
            mOptions.customListener = listener;
            return this;
        }

        public Builder setBgColor(int bgColorWheel) {
            mOptions.bgColorWheel = bgColorWheel;
            return this;
        }

        public Builder setTitleBgColor(int bgColorTitle) {
            mOptions.bgColorTitle = bgColorTitle;
            return this;
        }

        public Builder setTitleColor(int textColorTitle) {
            mOptions.textColorTitle = textColorTitle;
            return this;
        }

        public Builder setSubCalSize(int textSizeSubmitCancel) {
            mOptions.textSizeSubmitCancel = textSizeSubmitCancel;
            return this;
        }

        public Builder setTitleSize(int textSizeTitle) {
            mOptions.textSizeTitle = textSizeTitle;
            return this;
        }

        public Builder setContentTextSize(int textSizeContent) {
            mOptions.textSizeContent = textSizeContent;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable) {
            mOptions.cancelable = cancelable;
            return this;
        }


        public Builder setLabels(String label1, String label2, String label3) {
            mOptions.label1 = label1;
            mOptions.label2 = label2;
            mOptions.label3 = label3;
            return this;
        }

        /**
         * 设置间距倍数,但是只能在1.2-2.0f之间
         *
         * @param lineSpacingMultiplier
         */
        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier) {
            mOptions.lineSpacingMultiplier = lineSpacingMultiplier;
            return this;
        }

        /**
         * 设置分割线的颜色
         *
         * @param dividerColor
         */
        public Builder setDividerColor(int dividerColor) {
            mOptions.dividerColor = dividerColor;
            return this;
        }

        /**
         * 设置分割线的类型
         *
         * @param dividerType
         */
        public Builder setDividerType(WheelView.DividerType dividerType) {
            mOptions.dividerType = dividerType;
            return this;
        }

        /**
         * 设置分割线之间的文字的颜色
         *
         * @param textColorCenter
         */
        public Builder setTextColorCenter(int textColorCenter) {
            mOptions.textColorCenter = textColorCenter;
            return this;
        }

        /**
         * 设置分割线以外文字的颜色
         *
         * @param textColorOut
         */
        public Builder setTextColorOut(int textColorOut) {
            mOptions.textColorOut = textColorOut;
            return this;
        }

        public Builder setTypeface(Typeface font) {
            mOptions.font = font;
            return this;
        }

        public Builder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
            mOptions.cyclic1 = cyclic1;
            mOptions.cyclic2 = cyclic2;
            mOptions.cyclic3 = cyclic3;
            return this;
        }

        public Builder setSelectOptions(int option1) {
            mOptions.option1 = option1;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2) {
            mOptions.option1 = option1;
            mOptions.option2 = option2;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2, int option3) {
            mOptions.option1 = option1;
            mOptions.option2 = option2;
            mOptions.option3 = option3;
            return this;
        }

        public Builder setTextXOffset(int xoffset_one, int xoffset_two, int xoffset_three) {
            mOptions.xoffset_one = xoffset_one;
            mOptions.xoffset_two = xoffset_two;
            mOptions.xoffset_three = xoffset_three;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            mOptions.isCenterLabel = isCenterLabel;
            return this;
        }

        public OptionsPickerView build() {
            return new OptionsPickerView(mOptions);
        }
    }


    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews(mOptions.backgroundId);
        init();
        initEvents();
        if (mOptions.customListener == null) {
            LayoutInflater.from(context).inflate(mOptions.layoutRes, contentContainer);

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
            btnSubmit.setText(TextUtils.isEmpty(mOptions.textContentConfirm) ? context.getResources().getString(R.string.pickerview_submit) : mOptions.textContentConfirm);
            btnCancel.setText(TextUtils.isEmpty(mOptions.textContentCancel) ? context.getResources().getString(R.string.pickerview_cancel) : mOptions.textContentCancel);
            tvTitle.setText(TextUtils.isEmpty(mOptions.textContentTitle) ? "" : mOptions.textContentTitle);//默认为空

            //设置color
            btnSubmit.setTextColor(mOptions.textColorConfirm == 0 ? pickerview_timebtn_nor : mOptions.textColorConfirm);
            btnCancel.setTextColor(mOptions.textColorCancel == 0 ? pickerview_timebtn_nor : mOptions.textColorCancel);
            tvTitle.setTextColor(mOptions.textColorTitle == 0 ? pickerview_topbar_title : mOptions.textColorTitle);
            rv_top_bar.setBackgroundColor(mOptions.bgColorTitle == 0 ? pickerview_bg_topbar : mOptions.bgColorTitle);

            //设置文字大小
            btnSubmit.setTextSize(mOptions.textSizeSubmitCancel);
            btnCancel.setTextSize(mOptions.textSizeSubmitCancel);
            tvTitle.setTextSize(mOptions.textSizeTitle);
            tvTitle.setText(mOptions.textContentTitle);
        } else {
            mOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mOptions.layoutRes, contentContainer));
        }

        // ----滚轮布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.optionspicker);
        optionsPicker.setBackgroundColor(mOptions.bgColorWheel == 0 ? bgColor_default : mOptions.bgColorWheel);

        wheelOptions = new WheelOptions(optionsPicker, mOptions.linkage);
        wheelOptions.setTextContentSize(mOptions.textSizeContent);
        wheelOptions.setLabels(mOptions.label1, mOptions.label2, mOptions.label3);
        wheelOptions.setTextXOffset(mOptions.xoffset_one, mOptions.xoffset_two, mOptions.xoffset_three);

        wheelOptions.setCyclic(mOptions.cyclic1, mOptions.cyclic2, mOptions.cyclic3);
        wheelOptions.setTypeface(mOptions.font);

        setOutSideCancelable(mOptions.cancelable);


        wheelOptions.setDividerColor(mOptions.dividerColor);
        wheelOptions.setDividerType(mOptions.dividerType);
        wheelOptions.setLineSpacingMultiplier(mOptions.lineSpacingMultiplier);
        wheelOptions.setTextColorOut(mOptions.textColorOut);
        wheelOptions.setTextColorCenter(mOptions.textColorCenter);
        wheelOptions.isCenterLabel(mOptions.isCenterLabel);
    }


    /**
     * 设置默认选中项
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        mOptions.option1 = option1;
        reSetCurrentItems();
    }


    public void setSelectOptions(int option1, int option2) {
        mOptions.option1 = option1;
        mOptions.option2 = option2;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        mOptions.option1 = option1;
        mOptions.option2 = option2;
        mOptions.option3 = option3;
        reSetCurrentItems();
    }

    private void reSetCurrentItems() {
        if (wheelOptions != null) {
            wheelOptions.setCurrentItems(mOptions.option1, mOptions.option2, mOptions.option3);
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
        if (mOptions.optionsSelectListener != null) {
            int[] optionsCurrentItems = wheelOptions.getCurrentItems();
            mOptions.optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], clickView);
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int options2, int options3, View v);
    }

    @Override
    public boolean isDialog() {
        return mOptions.isDialog;
    }
}
