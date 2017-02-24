##（原作者已经转行了，他已把项目转交给我维护，所以我会继续更新优化，不能白白荒废了，欢迎Pull Request,提issue。有兴趣的小伙伴可以加入QQ群：387051294 讨论交流）

## V3.0.3版本更新说明（2017-2-24）
* 新增标题栏和滚轮的背景color区分设置
* 修正选项选择器设置默认选中项失效的问题
### Dependencies：
```java
compile 'com.contrarywind:Android-PickerView:3.0.3'
```
OR
### Maven：
```java
<dependency> 
<groupId>com.contrarywind</groupId> 
<artifactId>Android-PickerView</artifactId>
<version>3.0.3</version>
<type>pom</type>
</dependency>
```

==========

仿iOS的PickerView控件，有时间选择和选项选择并支持一二三级联动效果   
 
* 有时间和选项这两种选择器
* 选项选择器支持三级联动
* 时间选择器支持选择年份范围
* 支持“年，月，日，时，分，秒”，“省，市，区”等选项的单位（label）显示、隐藏和自定义。
* 新版本已经支持自定义文字、颜色、文字大小等属性
* 新版本支持背景颜色更换，有夜间模式需求的问题可以解决了

——TimePickerView  时间选择器，支持年月日时分，年月日，年月，时分等格式   
——OptionsPickerView  选项选择器，支持一，二，三级选项选择，并且可以设置是否联动 


#Sample usage：
```java
//TimePicker
 pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                tvTime.setText(getTime(date));
            }
        })
             .build();
 pvTime.show();


//OptionsPicker
 pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvOptions.setText(tx);
            }
        }).build();
 pvOptions.setPicker(options1Items, options2Items, options3Items);
 pvOptions.show(); 
```

# 详细使用请参考这里
[请戳我查看例示代码](https://github.com/Contrarywind/Android-PickerView/blob/master/app/src/main/java/com/bigkoo/pickerviewdemo/MainActivity.java)


# 说明文档 Documentation（还在持续更新中）
[English Documentation](https://github.com/Bigkoo/Android-PickerView/wiki/English-Documentation)

[中文说明文档（3.x版）](https://github.com/Bigkoo/Android-PickerView/wiki/%E4%B8%AD%E6%96%87%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3)

[原作者saiwu-bigkoo开发的（2.x/1.x版）中文说明文档](https://github.com/Bigkoo/Android-PickerView/wiki/%E6%97%A7%E9%A1%B9%E7%9B%AE%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%EF%BC%88old-version-1.x-2.x%E7%89%88%E6%9C%AC%EF%BC%89)


## Demo 图片（招行信用卡的“掌上生活”里面条件选择器他们用的就是我这个库，大家可以当实际项目参考）
![](https://github.com/saiwu-bigkoo/PickerView/blob/master/preview/pickerdemo1x.gif)
![](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/preview/pickerdemo_zhangshangshenghuo.gif)


## Thanks

- [WheelView](https://github.com/venshine/WheelView)
- [androidWheelView](https://github.com/weidongjian/androidWheelView/)
