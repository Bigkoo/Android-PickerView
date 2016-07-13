package com.bigkoo.pickerviewdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.view.IPickData;
import com.bigkoo.pickerviewdemo.bean.PickData;
import com.bigkoo.pickerviewdemo.bean.ProvinceBean;


public class MainActivity extends Activity {

    private ArrayList<IPickData> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<IPickData>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickData>>> options3Items = new ArrayList<>();
    private TextView tvTime, tvOptions;
    TimePickerView pvTime;
    OptionsPickerView pvOptions;
    View vMasker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vMasker=findViewById(R.id.vMasker);
        tvTime=(TextView) findViewById(R.id.tvTime);
        tvOptions=(TextView) findViewById(R.id.tvOptions);
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
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

        //选项选择器
        pvOptions = new OptionsPickerView(this);



        //选项1
        options1Items.add(new ProvinceBean(0,"广东","广东省，以岭南东道、广南东路得名","其他数据"));
        options1Items.add(new ProvinceBean(1,"湖南","湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南","芒果TV"));
        options1Items.add(new ProvinceBean(3,"广西","嗯～～",""));

        //选项2
        ArrayList<IPickData> options2Items_01=new ArrayList<>();
        options2Items_01.add(new PickData("广州"));
        options2Items_01.add(new PickData("佛山"));
        options2Items_01.add(new PickData("东莞"));
        options2Items_01.add(new PickData("阳江"));
        options2Items_01.add(new PickData("珠海"));
        ArrayList<IPickData> options2Items_02=new ArrayList<>();
        options2Items_02.add(new PickData("长沙"));
        options2Items_02.add(new PickData("岳阳"));
        ArrayList<IPickData> options2Items_03=new ArrayList<>();
        options2Items_03.add(new PickData("桂林"));
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<IPickData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickData>> options3Items_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickData>> options3Items_03 = new ArrayList<>();
        ArrayList<IPickData> options3Items_01_01=new ArrayList<>();
        options3Items_01_01.add(new PickData("白云"));
        options3Items_01_01.add(new PickData("蓝海"));
        options3Items_01_01.add(new PickData("海珠"));
        options3Items_01_01.add(new PickData("越秀"));
        options3Items_01.add(options3Items_01_01);
        ArrayList<IPickData> options3Items_01_02=new ArrayList<>();
        options3Items_01_02.add(new PickData("44"));
        options3Items_01_02.add(new PickData("高明"));
        options3Items_01_02.add(new PickData("55"));
        options3Items_01_02.add(new PickData("55"));
        options3Items_01.add(options3Items_01_02);
        ArrayList<IPickData> options3Items_01_03=new ArrayList<>();
        options3Items_01_03.add(new PickData("其他"));
        options3Items_01_03.add(new PickData("常平"));
        options3Items_01_03.add(new PickData("虎门"));
        options3Items_01.add(options3Items_01_03);
        ArrayList<IPickData> options3Items_01_04=new ArrayList<>();
        options3Items_01_04.add(new PickData("其他"));
        options3Items_01_04.add(new PickData("其他"));
        options3Items_01_04.add(new PickData("其他"));
        options3Items_01.add(options3Items_01_04);
        ArrayList<IPickData> options3Items_01_05=new ArrayList<>();

        options3Items_01_05.add(new PickData("其他"));
        options3Items_01_05.add(new PickData("其他"));
        options3Items_01.add(options3Items_01_05);

        ArrayList<IPickData> options3Items_02_01=new ArrayList<>();

        options3Items_02_01.add(new PickData("长沙"));
        options3Items_02_01.add(new PickData("长沙"));
        options3Items_02_01.add(new PickData("长沙"));
        options3Items_02_01.add(new PickData("长沙"));
        options3Items_02_01.add(new PickData("长沙"));




        options3Items_02.add(options3Items_02_01);
        ArrayList<IPickData> options3Items_02_02=new ArrayList<>();

        options3Items_02_02.add(new PickData("岳阳"));
        options3Items_02_02.add(new PickData("岳阳"));
        options3Items_02_02.add(new PickData("岳阳"));
        options3Items_02_02.add(new PickData("岳阳"));
        options3Items_02_02.add(new PickData("岳阳"));
        options3Items_02_02.add(new PickData("岳阳"));

        options3Items_02.add(options3Items_02_02);
        ArrayList<IPickData> options3Items_03_01=new ArrayList<>();
        options3Items_03_01.add(new PickData("好山水"));
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
                String tx = options1Items.get(options1).getContent()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
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

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

}
