package com.bigkoo.pickerviewdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.bigkoo.pickerviewdemo.bean.CardBean;
import com.bigkoo.pickerviewdemo.bean.PickerViewData;
import com.bigkoo.pickerviewdemo.bean.ProvinceBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    private Button btn_Time, btn_Options,btn_CustomOptions,btn_CustomTime;

    private TimePickerView pvTime,pvCustomTime;
    private OptionsPickerView pvOptions,pvCustomOptions;
    private ArrayList<CardBean> cardItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //最好等数据加载完毕再初始化并显示，以免数据量大的时候，还未加载完毕就显示，造成APP崩溃

        initTimePicker();
        initCustomTimePicker();

        initOptionData();
        initOptionPicker();
        initCustomOptionPicker();

        btn_Time = (Button) findViewById(R.id.btn_Time);
        btn_Options = (Button) findViewById(R.id.btn_Options);
        btn_CustomOptions = (Button) findViewById(R.id.btn_CustomOptions);
        btn_CustomTime = (Button) findViewById(R.id.btn_CustomTime);
        btn_Time.setOnClickListener(this);
        btn_Options.setOnClickListener(this);
        btn_CustomOptions.setOnClickListener(this);
        btn_CustomTime.setOnClickListener(this);

        findViewById(R.id.btn_GotoJsonData).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Time && pvTime != null) {
           // pvTime.setDate(Calendar.getInstance());
            pvTime.show(); //弹出时间选择器
        } else if (v.getId() == R.id.btn_Options && pvOptions != null) {
            pvOptions.show(); //弹出条件选择器
        } else if (v.getId() == R.id.btn_CustomOptions && pvCustomOptions != null) {
            pvCustomOptions.show(); //弹出自定义条件选择器
        }else if (v.getId() == R.id.btn_CustomTime && pvCustomTime != null) {
            pvCustomTime.show(); //弹出自定义时间选择器
        }else if (v.getId() == R.id.btn_GotoJsonData){//跳转到 省市区解析示例页面
            startActivity(new Intent(MainActivity.this,JsonDataActivity.class));
        }
    }


    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013,0,23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019,11,28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                btn_Time.setText(getTime(date));
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .isCyclic(true)// default is false
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /* .gravity(Gravity.RIGHT)// default is center*/
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .build();
    }

    private void initCustomTimePicker() {
        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014,1,23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027,2,28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                btn_CustomTime.setText(getTime(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setDividerColor(Color.BLACK)
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initOptionData() {
        getData();
        //选项1
        options1Items.add(new ProvinceBean(0,"广东","描述部分","其他数据"));
        options1Items.add(new ProvinceBean(1,"湖南","描述部分","其他数据"));
        options1Items.add(new ProvinceBean(2,"广西","描述部分","其他数据"));

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
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
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
        ArrayList<IPickerViewData> options3Items_01_01 = new ArrayList<>();
        options3Items_01_01.add(new PickerViewData("天河"));
        options3Items_01_01.add(new PickerViewData("海珠"));
        options3Items_01_01.add(new PickerViewData("越秀"));
        options3Items_01_01.add(new PickerViewData("荔湾"));
        options3Items_01_01.add(new PickerViewData("花都"));
        options3Items_01_01.add(new PickerViewData("番禺"));
        options3Items_01_01.add(new PickerViewData("萝岗"));
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


        //湖南的地区
        ArrayList<IPickerViewData> options3Items_02_01 = new ArrayList<>();
        options3Items_02_01.add(new PickerViewData("长沙1"));
        options3Items_02_01.add(new PickerViewData("长沙2"));
        options3Items_02_01.add(new PickerViewData("长沙3"));
        options3Items_02.add(options3Items_02_01);

        ArrayList<IPickerViewData> options3Items_02_02 = new ArrayList<>();
        options3Items_02_02.add(new PickerViewData("岳阳1"));
        options3Items_02_02.add(new PickerViewData("岳阳2"));
        options3Items_02_02.add(new PickerViewData("岳阳3"));
        options3Items_02.add(options3Items_02_02);

        ArrayList<IPickerViewData> options3Items_02_03 = new ArrayList<>();
        options3Items_02_03.add(new PickerViewData("株洲1"));
        options3Items_02_03.add(new PickerViewData("株洲2"));
        options3Items_02_03.add(new PickerViewData("株洲3"));
        options3Items_02.add(options3Items_02_03);

        ArrayList<IPickerViewData> options3Items_02_04 = new ArrayList<>();
        options3Items_02_04.add(new PickerViewData("衡阳1"));
        options3Items_02_04.add(new PickerViewData("衡阳2"));
        options3Items_02_04.add(new PickerViewData("衡阳3"));
        options3Items_02.add(options3Items_02_04);


        //广西的地区
        ArrayList<IPickerViewData> options3Items_03_01 = new ArrayList<>();
        options3Items_03_01.add(new PickerViewData("阳朔"));
        options3Items_03.add(options3Items_03_01);

        ArrayList<IPickerViewData> options3Items_03_02 = new ArrayList<>();
        options3Items_03_02.add(new PickerViewData("北流"));
        options3Items_03.add(options3Items_03_02);

        //将数据分别添加到一二三项的数组去
        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);
        /*--------数据源添加完毕---------*/
    }

    private void initOptionPicker() {//条件选择器初始化

        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                            options2Items.get(options1).get(options2)+
                            options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                btn_Options.setText(tx);
            }
        })
                /*.setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("城市选择")
                .setTitleSize(20)
                .setSubCalSize(18)//确定取消按钮大小
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setBackgroundColor(Color.WHITE)
                .setLinkage(false)//default is true
                .setCyclic(false, false, false)//循环与否
                .setOutSideCancelable(false)//点击外部dismiss, default true
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setLabels("省", "市", "区")//设置选择的三级单位
                .setLineSpacingMultiplier(2.0f) //设置两横线之间的间隔倍数（范围：1.2 - 2.0倍 文字高度）
                .setDividerColor(Color.RED)//设置分割线的颜色
                .isDialog(false)//是否设置为对话框模式
                .setOutSideCancelable(false)//点击屏幕中控件外部范围，是否可以取消显示
                .setSelectOptions(0, 1, 2)  //设置默认选中项
                .setTypeface(Typeface.SANS_SERIF)//字体样式*/
                .setTitleText("城市选择")
                .setDividerType(WheelView.DividerType.WRAP)
                .setContentTextSize(20)//设置滚轮文字大小
                .isDialog(true)
                .setDividerColor(Color.RED)//设置分割线的颜色
                .build();

        //pvOptions.setSelectOptions();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器

    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局

        // 注意：自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                btn_CustomOptions.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });

                        tvAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                                pvCustomOptions.setPicker(cardItem);
                            }
                        });

                    }
                })
                .isDialog(true)
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据

    }

    public void getData() {
        for (int i = 0; i < 5; i++) {
            cardItem.add(new CardBean(i, "No.ABC12345 " + i));
        }
    }


}
