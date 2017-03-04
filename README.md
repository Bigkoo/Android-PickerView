##（原作者[saiwu-bigkoo](https://github.com/saiwu-bigkoo)吴哥已经转行了，他已把项目转交给我维护，所以我会继续更新优化，不能白白荒废了，欢迎Pull Request,提issue。有兴趣的小伙伴可以加入QQ群：387051294 讨论交流）


## 介绍

一款仿iOS的PickerView控件，有时间选择和选项选择，并支持一二三级联动，支持自定义样式
 
* 有时间和选项这两种选择器
* 选项选择器支持三级联动
* 时间选择器支持选择年份范围
* 支持“年，月，日，时，分，秒”，“省，市，区”等选项的单位（label）显示、隐藏和自定义。
* 支持自定义文字、颜色、文字大小等属性
* 支持背景颜色更换，有夜间模式需求的问题可以解决了
* Item的文字长度过长时，文字会自适应缩放到Item的长度，避免显示不完全的问题

——TimePickerView  时间选择器，支持年月日时分，年月日，年月，时分等格式   
——OptionsPickerView  选项选择器，支持一，二，三级选项选择，并且可以设置是否联动 

![TimePicker.gif](http://img.my.csdn.net/uploads/201702/27/1488177272_3347.gif)
![OptionsPicker.gif](http://img.my.csdn.net/uploads/201702/27/1488177483_4301.gif)

## 有兴趣研究3D滚轮效果的实现机制，希望把源码研究透彻的可以看看这篇
 [Android-PickerView系列之源码解析篇（二）](http://blog.csdn.net/qq_22393017/article/details/59488906)

## 说明文档 Documentation（还在持续更新中）
### [English Documentation](https://github.com/Bigkoo/Android-PickerView/wiki/English-Documentation)

### [中文说明文档（3.x版）](https://github.com/Bigkoo/Android-PickerView/wiki/%E4%B8%AD%E6%96%87%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%EF%BC%883.x%E7%89%88%EF%BC%89)

### [旧版本（2.x/1.x版）中文说明文档](https://github.com/Bigkoo/Android-PickerView/wiki/%E6%97%A7%E9%A1%B9%E7%9B%AE%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%EF%BC%88old-version-1.x-2.x%E7%89%88%E6%9C%AC%EF%BC%89)

## V3.0.7版本更新说明（2017-3-4）
* 优化 起始终止年月日范围设置
* 新增自定义布局

## V3.0.6版本更新说明（2017-3-3）
* 新增对话框模式
* 新增timePicker “年月日时分”显示类型
* 新增分隔线的显示类型选择（FILL、WARP）
* 新增options默认选中项的调用方法（可重复设置）；
* 优化选中项显示位置有微小偏差问题

### 历史版本更新说明请查看Wiki


# How to use


### Gradle
```java
compile 'com.contrarywind:Android-PickerView:3.0.7'
//compile 'com.bigkoo:pickerview:lunar.1.0'  Lunar Branch 农历分支
```
OR
### Maven
```java
<dependency> 
<groupId>com.contrarywind</groupId> 
<artifactId>Android-PickerView</artifactId>
<version>3.0.7</version>
<type>pom</type>
</dependency>
```


## Sample usage：
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
## [请戳我查看例示代码](https://github.com/Bigkoo/Android-PickerView/blob/master/app/src/main/java/com/bigkoo/pickerviewdemo/MainActivity.java)


## 效果图（招行信用卡的“掌上生活”里面条件选择器他们用的就是我这个库，大家可以当实际项目参考）
![](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/preview/pickerdemo_zhangshangshenghuo.gif)


## Thanks

- [WheelView](https://github.com/venshine/WheelView)
- [androidWheelView](https://github.com/weidongjian/androidWheelView/)
