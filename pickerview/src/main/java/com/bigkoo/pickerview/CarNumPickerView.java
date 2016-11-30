package com.bigkoo.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelCarNum;

import java.util.ArrayList;

/**
 * Created by Dell on 2016/11/30.
 */

public class CarNumPickerView<T> extends BasePickerView implements View.OnClickListener {
    WheelCarNum<T> wheelCarNum;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private OnOptionsSelectListener optionsSelectListener;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public CarNumPickerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.pickerview_car_num, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----转轮
        final View carNUmPicker = findViewById(R.id.car_num_optionspicker);
        wheelCarNum = new WheelCarNum(carNUmPicker);
    }

    public void setPicker(ArrayList<T> provinceItems, ArrayList<T> areaItems, ArrayList<T>
            options1Items, ArrayList<T> options2Items, ArrayList<T> options3Items, ArrayList<T>
                                  options4Items, ArrayList<T> options5Items) {
        wheelCarNum.setPicker(provinceItems, areaItems, options1Items, options2Items,
                options3Items, options4Items, options5Items);
    }

    /**
     * 设置选中的item位置
     *
     * @param option 位置
     */
    public void setSelectOptions(int option) {
        wheelCarNum.setCurrentItems(option);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wheelCarNum.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (optionsSelectListener != null) {
                int[] optionsCurrentItems = wheelCarNum.getCurrentItems();
                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0],
                        optionsCurrentItems[1], optionsCurrentItems[2], optionsCurrentItems[3],
                        optionsCurrentItems[4], optionsCurrentItems[5], optionsCurrentItems[6]);
            }
            dismiss();
            return;
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int option2, int options3, int options4, int options5,
                             int options6, int options7);
    }

    public void setOnoptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
