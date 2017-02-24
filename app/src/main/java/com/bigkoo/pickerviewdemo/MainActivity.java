package com.bigkoo.pickerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.bigkoo.pickerviewdemo.bean.PickerViewData;
import com.bigkoo.pickerviewdemo.bean.ProvinceBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity implements View.OnClickListener{

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    private Button tvTime, tvOptions;

    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //最好等数据加载完毕再初始化并显示，以免数据量大的时候，还未加载完毕就显示，造成APP崩溃
        initTimePicker();
        initOptionData();
        initOptionPicker();

        tvTime=(Button) findViewById(R.id.tvTime);
        tvOptions=(Button) findViewById(R.id.tvOptions);
        tvTime.setOnClickListener(this);
        tvOptions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tvTime && pvTime!=null){
            pvTime.show(); //弹出时间选择器
        }
        else if (v.getId()==R.id.tvOptions && pvOptions!=null){
            pvOptions.show(); //弹出时间选择器
        }
    }


    private void initTimePicker() {

        //控制时间范围,setRange方法 要在setDate 之前才有效果(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         /*Calendar calendar = Calendar.getInstance();*/

        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                tvTime.setText(getTime(date));
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setOutSideCancelable(false)// default true
                .isCyclic(true)// default false
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setBackgroundColor(0xFF000000)//夜间模式 Night mode
                .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//default 1900-2100 years *//*
                .setDate(new Date())// default system*/
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setLabel("年","月","日","时","分","秒")
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initOptionData() {

        //选项1
        options1Items.add(new ProvinceBean(0,"广东","描述部分","其他数据"));
        options1Items.add(new ProvinceBean(1,"湖南","描述部分","其他数据"));
        options1Items.add(new ProvinceBean(2,"广西","描述部分","其他数据"));

        //选项2
        ArrayList<String> options2Items_01=new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02=new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03=new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items_03.add("玉林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_03 = new ArrayList<>();

        //广东的地区
        ArrayList<IPickerViewData> options3Items_01_01=new ArrayList<>();
        options3Items_01_01.add(new PickerViewData("天河"));
        options3Items_01_01.add(new PickerViewData("海珠"));
        options3Items_01_01.add(new PickerViewData("越秀"));
        options3Items_01_01.add(new PickerViewData("荔湾"));
        options3Items_01_01.add(new PickerViewData("花都"));
        options3Items_01_01.add(new PickerViewData("番禺"));
        options3Items_01_01.add(new PickerViewData("萝岗"));
        options3Items_01.add(options3Items_01_01);

        ArrayList<IPickerViewData> options3Items_01_02=new ArrayList<>();
        options3Items_01_02.add(new PickerViewData("南海"));
        options3Items_01_02.add(new PickerViewData("高明"));
        options3Items_01_02.add(new PickerViewData("禅城"));
        options3Items_01_02.add(new PickerViewData("桂城"));
        options3Items_01.add(options3Items_01_02);

        ArrayList<IPickerViewData> options3Items_01_03=new ArrayList<>();
        options3Items_01_03.add(new PickerViewData("其他"));
        options3Items_01_03.add(new PickerViewData("常平"));
        options3Items_01_03.add(new PickerViewData("虎门"));
        options3Items_01.add(options3Items_01_03);

        ArrayList<IPickerViewData> options3Items_01_04=new ArrayList<>();
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01.add(options3Items_01_04);
        ArrayList<IPickerViewData> options3Items_01_05=new ArrayList<>();

        options3Items_01_05.add(new PickerViewData("其他1"));
        options3Items_01_05.add(new PickerViewData("其他2"));
        options3Items_01.add(options3Items_01_05);


        //湖南的地区
        ArrayList<IPickerViewData> options3Items_02_01=new ArrayList<>();
        options3Items_02_01.add(new PickerViewData("长沙1"));
        options3Items_02_01.add(new PickerViewData("长沙2"));
        options3Items_02_01.add(new PickerViewData("长沙3"));
        options3Items_02.add(options3Items_02_01);

        ArrayList<IPickerViewData> options3Items_02_02=new ArrayList<>();
        options3Items_02_02.add(new PickerViewData("岳阳1"));
        options3Items_02_02.add(new PickerViewData("岳阳2"));
        options3Items_02_02.add(new PickerViewData("岳阳3"));
        options3Items_02.add(options3Items_02_02);

        ArrayList<IPickerViewData> options3Items_02_03=new ArrayList<>();
        options3Items_02_03.add(new PickerViewData("株洲1"));
        options3Items_02_03.add(new PickerViewData("株洲2"));
        options3Items_02_03.add(new PickerViewData("株洲3"));
        options3Items_02.add(options3Items_02_03);

        ArrayList<IPickerViewData> options3Items_02_04=new ArrayList<>();
        options3Items_02_04.add(new PickerViewData("衡阳1"));
        options3Items_02_04.add(new PickerViewData("衡阳2"));
        options3Items_02_04.add(new PickerViewData("衡阳3"));
        options3Items_02.add(options3Items_02_04);


        //广西的地区
        ArrayList<IPickerViewData> options3Items_03_01=new ArrayList<>();
        options3Items_03_01.add(new PickerViewData("阳朔"));
        options3Items_03.add(options3Items_03_01);

        ArrayList<IPickerViewData> options3Items_03_02=new ArrayList<>();
        options3Items_03_02.add(new PickerViewData("北流"));
        options3Items_03.add(options3Items_03_02);

        //将数据分别添加到一二三项的数组去
        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);
        /*--------数据源添加完毕---------*/
    }



    private void initOptionPicker() {//条件选择器初始化
        pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvOptions.setText(tx);
            }
        })
                /*.setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("城市选择")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setBackgroundColor(Color.WHITE)
                .setContentTextSize(18)
                .setLinkage(false)//default true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与
                .setOutSideCancelable(false)//点击外部dismiss, default true*/
                /*.setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode*/
                .setSelectOptions(0,1,2)  //设置默认选中项
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);

    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pvTime.isShowing()){
                pvTime.dismiss();
                return true;
            }
            if(pvOptions.isShowing()){
                pvOptions.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
