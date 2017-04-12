
## Android-PickerView

### [English Document](https://github.com/Bigkoo/Android-PickerView/blob/master/README-en.md)

### 关于项目的更多详请，请查看 [Wiki](https://github.com/Bigkoo/Android-PickerView/wiki)
### 对于使用上有任何疑问或优化建议等，欢迎加入QQ群： 387051294 讨论交流技术问题。

#### [saiwu-bigkoo](https://github.com/saiwu-bigkoo) 吴哥已经转行不干编程了，他已把项目转交给我维护。2.x/1.x/农历已分支出去并停止更新，若有需要请查看旧版本说明文档： [旧版本（2.x/1.x版）中文说明文档](https://github.com/Bigkoo/Android-PickerView/wiki/%E6%97%A7%E9%A1%B9%E7%9B%AE%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%EF%BC%88old-version-1.x-2.x%E7%89%88%E6%9C%AC%EF%BC%89)

</br>

## 介绍

这是一款仿iOS的PickerView控件，有时间选择和选项选择，并支持一二三级联动，支持自定义样式，3.x新版本的详细特性如下：
 
* 有时间和选项这两种选择器
* 选项选择器支持三级联动
* 时间选择器支持起始和终止日期设定
* 支持“年，月，日，时，分，秒”，“省，市，区”等选项的单位（label）显示、隐藏和自定义。
* 支持自定义文字、颜色、文字大小等属性
* 支持背景颜色更换，有夜间模式需求的问题可以解决了
* Item的文字长度过长时，文字会自适应缩放到Item的长度，避免显示不完全的问题

——TimePickerView  时间选择器，支持年月日时分，年月日，年月，时分等格式   
——OptionsPickerView  选项选择器，支持一，二，三级选项选择，并且可以设置是否联动 

![TimePicker.gif](https://github.com/Bigkoo/Android-PickerView/blob/master/preview/timepicker.gif)
![TimePickerNight.gif](https://github.com/Bigkoo/Android-PickerView/blob/master/preview/timepicker_night.gif)

![Province.gif](https://github.com/Bigkoo/Android-PickerView/blob/master/preview/JsonData.gif)
![CustomLayout.gif](https://github.com/Bigkoo/Android-PickerView/blob/master/preview/CustomLayout.gif)


### 有兴趣研究3D滚轮效果的实现机制，希望把源码研究透彻的可以看看这篇博客：
### [Android-PickerView系列之源码解析篇（二）](http://blog.csdn.net/qq_22393017/article/details/59488906)

### V3.2.4版本更新说明（2017-4-7）
* 修复：修复偶尔会出现item滑到第一项或最后一项时滑出边界的情况 。

### V3.2.3版本更新说明（2017-3-31）
* 优化：滚轮边界处理优化，解决滑动到第一项或最后一项时会跳动的情况。
* 优化：Dialog 模式 下PickerView的弹出和关闭添加了缩放动画，优化视觉效果。

### V3.2.2版本更新说明（2017-3-24）
* 新增：isCenterLabel（boolean isCenter）方法，该方法默认为true，只在选中项显示label；填false 则每项item后面都会带有label。

### V3.2.1版本更新说明（2017-3-23）
* 新增：show(View v) 方法，用于绑定所点击弹出 picker 的 View 控件。
* 废弃：optionsPicker 的 setLinkage 方法。
* 新增：optionsPicker 的 setNPicker 方法，用于多级不联动情况下，条件选择器的显示。
* 修复：在某些极端情况下，快速滑动并还未停止时就点击确定按钮，导致数据匹配不当造成应用崩溃的问题。



#### 更多历史版本详情，请查阅：[更新说明（3.x版本）](https://github.com/Bigkoo/Android-PickerView/wiki/%E6%9B%B4%E6%96%B0%E8%AF%B4%E6%98%8E%EF%BC%883.x%E7%89%88%E6%9C%AC%EF%BC%89) 

#### 方法名与参数请查阅：[方法名与参数说明文档](https://github.com/Bigkoo/Android-PickerView/wiki/%E6%96%B9%E6%B3%95%E5%90%8D%E4%B8%8E%E5%8F%82%E6%95%B0%E8%AF%B4%E6%98%8E%EF%BC%883.x%E7%89%88%E6%9C%AC%EF%BC%89)

</br>

### **使用步骤：**

#### 1.添加Jcenter仓库 Gradle依赖：
```java
compile 'com.contrarywind:Android-PickerView:3.2.4'
```
或者

#### Maven
```
<dependency>
<groupId>com.contrarywind</groupId>
<artifactId>Android-PickerView</artifactId>
<version>3.2.4</version>
<type>pom</type>
</dependency>
```
#### 2.在Activity中添加如下代码：

```java
//时间选择器
TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                tvTime.setText(getTime(date));
            }
        })
             .build();
 pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
 pvTime.show();
```

```java
//条件选择器
 OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
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
#### 大功告成~

#### 3.如果默认样式不符合你的口味，可以自定义各种属性：
```java
 Calendar selectedDate = Calendar.getInstance();
 Calendar startDate = Calendar.getInstance();
 startDate.set(2013,1,1);
 Calendar endDate = Calendar.getInstance();
 endDate.set(2020,1,1);

 pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                tvTime.setText(getTime(date));
            }
        })
                .setType(TimePickerView.Type.ALL)//默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("Title")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
```

```java
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
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
```
#### 4.如果需要自定义布局：

```java
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
                        //自定义布局中的控件初始化及事件处理
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
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据
```

#### 5.对使用还有疑问的话，可参考Demo代码
[请戳我查看demo代码](https://github.com/Bigkoo/Android-PickerView/blob/master/app/src/main/java/com/bigkoo/pickerviewdemo/MainActivity.java)



### 效果图（招行信用卡的“掌上生活”里面条件选择器他们用的就是我这个库，大家可以当实际项目参考）
![](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/preview/pickerdemo_zhangshangshenghuo.gif)


### Thanks

- [WheelView](https://github.com/venshine/WheelView)
- [androidWheelView](https://github.com/weidongjian/androidWheelView/)

## License

```
Copyright 2014 Bigkoo
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
