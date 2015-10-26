PickerView
==========

注：
重要的事放在开头说。   
此控件源于   
https://github.com/saiwu-bigkoo/PickerView   
在原有基础上增加了支持扩展按钮的功能，主要是为了实现自己遇到的   
'至今'   
选项需求。顺带加上了弹窗顶部的灰色横线。   
为了发布到jcenter上区别于原来的控件，整体包名已经修改成了  
com.xuhuan   
版本号也重置成 1.0.1.


仿iOS的PickerView控件，有时间选择和选项选择并支持一二三级联动效果   
——TimePopupWindow  时间选择器，支持年月日时分，年月日，年月，时分等格式，支持N个扩展按钮，建议用于 '至今' 功能即可    
——OptionsPopupWindow  选项选择器，支持一，二，三级选项选择，并且可以设置是否联动    

####使用gradle 依赖:
```java
   compile 'com.xuhuan:pickerview:1.0.1'
```

## Demo 图片（招行信用卡的“掌上生活”里面条件选择器他们用的就是我这个库，大家可以当实际项目参考）
![](https://github.com/saiwu-bigkoo/PickerView/blob/master/preview/pickerdemo.gif)
![](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/preview/pickerdemo_zhangshangshenghuo.gif)
- [demo代码请看戳这里](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/app/src/main/java/com/xuhuan/pickerviewdemo/MainActivity.java)


## Thanks

- WheelView
