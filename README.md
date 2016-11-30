抱歉各位，本人已离开编程界，不能继续给大家填坑了。

PickerView (2.x系列)
==========

精仿iOS的PickerView控件，有时间选择和选项选择并支持一二三级联动效果   
——TimePickerView  时间选择器，支持年月日时分，年月日，年月，时分等格式   
——OptionsPickerView  选项选择器，支持一，二，三级选项选择，并且可以设置是否联动    

2.x是全新的3D效果，比1.x版本更加贴近iOS的效果，从外观细节上也得到了改善。api兼容1.x版本，只需要把依赖的版本号升级即可，几乎不用修改代码即可完成升级。

####使用gradle 依赖:
```java
   compile 'com.bigkoo:pickerview:2.1.1'
  //这个是支持农历的分支 compile 'com.bigkoo:pickerview:lunar.1.0'
```

## Demo 图片
![](https://github.com/saiwu-bigkoo/PickerView/blob/master/preview/pickerdemo.gif)

- [demo代码请看戳这里](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/app/src/main/java/com/bigkoo/pickerviewdemo/MainActivity.java)


>## 更新说明

>v2.1.1
 - 修复dismiss时候点击背景会重复dismiss动画问题。<br />

>v2.1.0
 - 去掉反射获取字符串，改为通过继承IPickerViewData。<br />
 - 解决多个PickerView同时存在时取消不了弹窗问题。<br />

>v2.0.9
 - 解决属性和其他第三方库冲突问题。<br />

>v2.0.8
 - 修复＃41 未选中项有错乱数据问题。<br />
 - 加入pickerview_customTextSize 和 pickerview_textsize 到 xml 中 来控制自定义文字大小<br />

>v2.0.7
 - 修复设置初始化position ，第三级数据不对的BUG。 <br />
 
>v2.0.6
 - 修复不循环模式下点击超出范围问题，修复后点击空白的地方，只能滚到最顶或最底，不会滚出数据范围。 <br />

>v2.0.5
 - 修复不循环模式下底部超出范围问题 <br />
 
>v2.0.4
 - 修复不循环模式下顶部超出范围问题 <br />
 - wheel view文字颜色通过xml配置 <br />
 
>v2.0.3
 - 修复时间选择的时候部分数字选不到直接跳到下一个数字的问题 <br />

>v2.0.2
 - 修复不循环模式下点击空白item处出现数组越界问题 <br />
 - 修复循环模式下只有一条数据的时候只显示三条而不是填充满高度问题  <br />
 
>v2.0.1
 - 去掉popupWindow，改用View，类名也对应修改为TimePickerView和 OptionsPickerView <br />
 - 加入遮罩效果  <br />

>v2.0.0 不需修改任何代码就可以兼容1.x
 - 外观大整改  <br />
 - 支持反射获取getPickerViewText()来获取要展示数据，以前只能传String的对象，现在可以传任意对象只要有getPickerViewText()函数即可显示对应的字符串，如果没有getPickerViewText()函数则使用对象toString作为显示  <br />
 - 加入setTitle  <br />

-－－－－－－－－－－－－－－－－－－－－华丽丽的分割线－－－－－－－－－－－－－－－－－－－－－－－－－－

PickerView1.x (我已经把1.0.3版本分到v1.x的分支去了，停止维护1.x的分支)
==========

####使用gradle 依赖:
```java
   compile 'com.bigkoo:pickerview:1.0.3'
```

## Demo 图片（招行信用卡的“掌上生活”里面条件选择器他们用的就是我这个库，大家可以当实际项目参考）
![](https://github.com/saiwu-bigkoo/PickerView/blob/master/preview/pickerdemo1x.gif)
![](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/preview/pickerdemo_zhangshangshenghuo.gif)


## Thanks

- WheelView
- [androidWheelView](https://github.com/weidongjian/androidWheelView/)
