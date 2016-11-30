package com.bigkoo.pickerview.view;

import android.view.View;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;

import java.util.ArrayList;

/**
 * Created by Dell on 2016/11/30.
 */

public class WheelCarNum<T> {
    private View view;
    private WheelView wv_province;
    private WheelView wv_area;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;
    private WheelView wv_option4;
    private WheelView wv_option5;

    private ArrayList<T> mProvinceItems;
    private ArrayList<T> mAreaItems;
    private ArrayList<T> mOptions1Items;
    private ArrayList<T> mOptions2Items;
    private ArrayList<T> mOptions3Items;
    private ArrayList<T> mOptions4Items;
    private ArrayList<T> mOptions5Items;

    private OnItemSelectedListener wheelListener_option1;
    private OnItemSelectedListener wheelListener_option2;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelCarNum(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public void setPicker(ArrayList<T> provinceItems, ArrayList<T> areaItems, ArrayList<T>
            options1Items, ArrayList<T> options2Items, ArrayList<T> options3Items, ArrayList<T>
            options4Items, ArrayList<T> options5Items) {
        this.mProvinceItems = provinceItems;
        this.mAreaItems = areaItems;
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;
        this.mOptions4Items = options4Items;
        this.mOptions5Items = options5Items;
        int len = ArrayWheelAdapter.DEFAULT_LENGTH;
        // 选项省
        wv_province = (WheelView) view.findViewById(R.id.car_num_province);
        wv_province.setAdapter(new ArrayWheelAdapter(mProvinceItems));// 设置显示数据
        wv_province.setCurrentItem(0);// 初始化时显示的数据
        // 选项区
        wv_area = (WheelView) view.findViewById(R.id.car_num_area);
        wv_area.setAdapter(new ArrayWheelAdapter(mAreaItems));// 设置显示数据
        wv_area.setCurrentItem(0);// 初始化时显示的数据
        // 选项1
        wv_option1 = (WheelView) view.findViewById(R.id.car_num_options1);
        wv_option1.setAdapter(new ArrayWheelAdapter(mOptions1Items));// 设置显示数据
        wv_option1.setCurrentItem(0);// 初始化时显示的数据
        // 选项2
        wv_option2 = (WheelView) view.findViewById(R.id.car_num_options2);
        wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items));// 设置显示数据
        wv_option2.setCurrentItem(0);// 初始化时显示的数据
        // 选项3
        wv_option3 = (WheelView) view.findViewById(R.id.car_num_options3);
        wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items));// 设置显示数据
        wv_option3.setCurrentItem(0);// 初始化时显示的数据
        // 选项4
        wv_option4 = (WheelView) view.findViewById(R.id.car_num_options4);
        wv_option4.setAdapter(new ArrayWheelAdapter(mOptions4Items));// 设置显示数据
        wv_option4.setCurrentItem(0);// 初始化时显示的数据
        // 选项5
        wv_option5 = (WheelView) view.findViewById(R.id.car_num_options5);
        wv_option5.setAdapter(new ArrayWheelAdapter(mOptions5Items));// 设置显示数据
        wv_option5.setCurrentItem(0);// 初始化时显示的数据
        int textSize = 18;

        wv_option1.setTextSize(textSize);
        wv_option2.setTextSize(textSize);
        wv_option3.setTextSize(textSize);

        //        // 联动监听器
        //        wheelListener_option1 = new OnItemSelectedListener() {
        //
        //            @Override
        //            public void onItemSelected(int index) {
        //
        //            }
        //        };
        //        wheelListener_option2 = new OnItemSelectedListener() {
        //
        //            @Override
        //            public void onItemSelected(int index) {
        //                if (mOptions3Items != null) {
        //                }
        //            }
        //        };
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wv_province.setCyclic(cyclic);
        wv_area.setCyclic(cyclic);
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
        wv_option3.setCyclic(cyclic);
        wv_option4.setCyclic(cyclic);
        wv_option5.setCyclic(cyclic);
    }

    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
     *
     * @return 索引数组
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[7];
        currentItems[0] = wv_province.getCurrentItem();
        currentItems[1] = wv_area.getCurrentItem();
        currentItems[2] = wv_option1.getCurrentItem();
        currentItems[3] = wv_option2.getCurrentItem();
        currentItems[4] = wv_option3.getCurrentItem();
        currentItems[5] = wv_option4.getCurrentItem();
        currentItems[6] = wv_option5.getCurrentItem();
        return currentItems;
    }

    public void setCurrentItems(int option) {
        wv_province.setCurrentItem(option);
        wv_area.setCurrentItem(option);
        wv_option1.setCurrentItem(option);
        wv_option2.setCurrentItem(option);
        wv_option3.setCurrentItem(option);
        wv_option4.setCurrentItem(option);
        wv_option5.setCurrentItem(option);
    }

}
