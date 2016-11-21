package com.bigkoo.pickerviewdemo;

import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

public class MainActivity extends Activity {

    private TextView tvOptions;
    OptionsPickerView pvOptions;

    ArrayList<CityEntity.DataBean.RegionBean> provinceList = new ArrayList<>();
    ArrayList<ArrayList<CityEntity.DataBean.RegionBean.StateBean>> cityList = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<CityEntity.DataBean.RegionBean.StateBean.CityBean>>> districtList = new ArrayList<>();


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initCityList();
    }


    public void selectCity(View view) {

        final MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this).title("选择城市").positiveText(android.R.string.ok).negativeText(android.R.string.cancel)

                                                                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                                                                @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                                                                }
                                                                                            }).onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        int[] optionsCurrentItems = pvOptions.wheelOptions.getCurrentItems();
                        int position1 = optionsCurrentItems[0];
                        int position2 = optionsCurrentItems[1];
                        int position3 = optionsCurrentItems[2];

                        tvOptions.setText(
                                provinceList.get(position1).getName() + cityList.get(position1).get(position2).getName() + districtList.get(position1).get(position2).get(position3).getName());
                    }
                });

        pvOptions.initEvents();
        builder.customView(pvOptions.optionspicker, false);
        builder.build().show();
    }


    private void initData() {
        tvOptions = (TextView) findViewById(R.id.tvOptions);
        //选项选择器
        pvOptions = new OptionsPickerView(this);
    }


    private void initCityList() {
        Gson gson = new Gson();
        String response = getJson();
        if (response != null) {

            CityEntity cityEntity = gson.fromJson(response, CityEntity.class);

            ArrayList<CityEntity.DataBean.RegionBean.StateBean> stateBeanList = null;
            ArrayList<CityEntity.DataBean.RegionBean.StateBean.CityBean> cityBeanList = null;
            ArrayList<ArrayList<CityEntity.DataBean.RegionBean.StateBean.CityBean>> list = null;

            for (int i = 0; i < cityEntity.getData().size(); i++) {
                list = new ArrayList<>();
                for (int j = 0; j < cityEntity.getData().get(i).getRegion().getState().size(); j++) {
                    cityBeanList = (ArrayList<CityEntity.DataBean.RegionBean.StateBean.CityBean>) cityEntity.getData().get(i).getRegion().getState().get(j).getCity();
                    stateBeanList = (ArrayList<CityEntity.DataBean.RegionBean.StateBean>) cityEntity.getData().get(i).getRegion().getState();
                    list.add(cityBeanList);
                }
                districtList.add(list);
                cityList.add(stateBeanList);
                provinceList.add(cityEntity.getData().get(i).getRegion());
            }

            // 设置列表
            pvOptions.setPicker(provinceList, cityList, districtList, true);
            // 设置默认三级滚动都不循环
            pvOptions.setCyclic(false);
            //设置默认选中的三级项目
            pvOptions.setSelectOptions(0, 0, 0);
        }
    }


    private String getJson() {
        String json = null;
        InputStream is = null;
        try {
            is = getAssets().open("citylist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
