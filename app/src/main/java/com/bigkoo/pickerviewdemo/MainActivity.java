package com.bigkoo.pickerviewdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bigkoo.pickerview.CarNumPickerView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.bigkoo.pickerviewdemo.bean.PickerViewData;
import com.bigkoo.pickerviewdemo.bean.ProvinceBean;


public class MainActivity extends Activity {

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    private ArrayList<String> listProvince, listArea, listRandom;
    private TextView tvTime, tvOptions, tvCarNum;
    TimePickerView pvTime;
    OptionsPickerView pvOptions;
    CarNumPickerView pvCar;
    View vMasker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vMasker = findViewById(R.id.vMasker);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvOptions = (TextView) findViewById(R.id.tvOptions);
        tvCarNum = (TextView) findViewById(R.id.tvCarNum);
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        //        Calendar calendar = Calendar.getInstance();
        //        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        // 要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvTime.setText(getTime(date));
            }
        });
        //弹出时间选择器
        tvTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });

        //车牌号码选择器
        listProvince = createList(1);
        listArea = createList(2);
        listRandom = createList(3);
        pvCar = new CarNumPickerView(this);
        pvCar.setPicker(listProvince, listArea, listRandom, listRandom, listRandom, listRandom, listRandom);
        pvCar.setCyclic(true);
        pvCar.setSelectOptions(0);
        pvCar.setTitle("车牌号码");
        pvCar.setOnoptionsSelectListener(new CarNumPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, int options4,
                                        int options5, int options6, int options7) {
                tvCarNum.setText(listProvince.get(options1) + listArea.get(option2) + listRandom.get
                        (options3) + listRandom.get(options4) + listRandom.get(options5) + listRandom.get
                        (options6) + listRandom.get(options7));
            }
        });
        tvCarNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pvCar.show();
            }
        });

        //选项选择器
        pvOptions = new OptionsPickerView(this);


        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "广东省，以岭南东道、广南东路得名", "其他数据"));
        options1Items.add(new ProvinceBean(1, "湖南", "湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南", "芒果TV"));
        options1Items.add(new ProvinceBean(3, "广西", "嗯～～", ""));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_03 = new ArrayList<>();
        ArrayList<IPickerViewData> options3Items_01_01 = new ArrayList<>();
        options3Items_01_01.add(new PickerViewData("天河"));
        options3Items_01_01.add(new PickerViewData("黄埔"));
        options3Items_01_01.add(new PickerViewData("海珠"));
        options3Items_01_01.add(new PickerViewData("越秀"));
        options3Items_01.add(options3Items_01_01);
        ArrayList<IPickerViewData> options3Items_01_02 = new ArrayList<>();
        options3Items_01_02.add(new PickerViewData("南海"));
        options3Items_01_02.add(new PickerViewData("高明"));
        options3Items_01_02.add(new PickerViewData("禅城"));
        options3Items_01_02.add(new PickerViewData("桂城"));
        options3Items_01.add(options3Items_01_02);
        ArrayList<IPickerViewData> options3Items_01_03 = new ArrayList<>();
        options3Items_01_03.add(new PickerViewData("其他"));
        options3Items_01_03.add(new PickerViewData("常平"));
        options3Items_01_03.add(new PickerViewData("虎门"));
        options3Items_01.add(options3Items_01_03);
        ArrayList<IPickerViewData> options3Items_01_04 = new ArrayList<>();
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01.add(options3Items_01_04);
        ArrayList<IPickerViewData> options3Items_01_05 = new ArrayList<>();

        options3Items_01_05.add(new PickerViewData("其他1"));
        options3Items_01_05.add(new PickerViewData("其他2"));
        options3Items_01.add(options3Items_01_05);

        ArrayList<IPickerViewData> options3Items_02_01 = new ArrayList<>();

        options3Items_02_01.add(new PickerViewData("长沙1"));
        options3Items_02_01.add(new PickerViewData("长沙2"));
        options3Items_02_01.add(new PickerViewData("长沙3"));
        options3Items_02_01.add(new PickerViewData("长沙4"));
        options3Items_02_01.add(new PickerViewData("长沙5"));


        options3Items_02.add(options3Items_02_01);
        ArrayList<IPickerViewData> options3Items_02_02 = new ArrayList<>();

        options3Items_02_02.add(new PickerViewData("岳阳"));
        options3Items_02_02.add(new PickerViewData("岳阳1"));
        options3Items_02_02.add(new PickerViewData("岳阳2"));
        options3Items_02_02.add(new PickerViewData("岳阳3"));
        options3Items_02_02.add(new PickerViewData("岳阳4"));
        options3Items_02_02.add(new PickerViewData("岳阳5"));

        options3Items_02.add(options3Items_02_02);
        ArrayList<IPickerViewData> options3Items_03_01 = new ArrayList<>();
        options3Items_03_01.add(new PickerViewData("好山水"));
        options3Items_03.add(options3Items_03_01);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        //        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3)
                        .getPickerViewText();
                tvOptions.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });
        //点击弹出选项选择器
        tvOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
    }

    private ArrayList<String> createList(int type) {
        ArrayList list = new ArrayList<>();
        if (1 == type) {
            //京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼渝川黔滇藏陕甘青宁新台港澳
            List l = Arrays.asList(new String[]{"京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪",
                    "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝", "川",
                    "黔", "滇", "藏", "陕", "甘", "青", "宁", "新", "台", "港", "澳"});
            for (int i = 0; i < l.size(); i++) {
                list.add((String) l.get(i));
            }
        } else if (2 == type) {
            for (char i = 'A'; i <= 'Z'; i++) {
                list.add(i + "");
            }
        } else if (3 == type) {
            for (int i = 0; i < 10; i++) {
                list.add(i + "");
            }
            for (char i = 'A'; i <= 'Z'; i++) {
                list.add(i + "");
            }
        }
        return list;
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pvOptions.isShowing() || pvTime.isShowing()) {
                pvOptions.dismiss();
                pvTime.dismiss();
                return true;
            }
            if (pvTime.isShowing()) {
                pvTime.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
