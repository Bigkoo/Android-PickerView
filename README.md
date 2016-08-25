PickerView (Lunar分支)
==========

- 通过setLunarCalendar(boolean isLunarCalendar)方法,来控制控件在显示时间的时候,显示为阴历还是阳历.默认为为阳历显示.
- 参数:true:阴历格式显示,false或者不设置:阳历格式显示.
- 阳历的设置时间和返回时间,参考原主分支说明.
- 阴历在设置时间的时候,也就是调用setTime方法的时候,需要传入阴历对应的阳历时间,例如:需要设置初始值为农历2016年7月21,那么时间传入的时间是这个日期对应的阳历时间,也就是:2016年8月23的时间.
- 返回数据,阳历返回的是选择的时间,阴历返回的是选择日期对应的阳历时间,全部都转换成阳历,主要是方便用户调用.
- 如果用户需要转换成对应的阴历,或者阴历转换成阳历,直接使用[时间转换工具类](https://github.com/saiwu-bigkoo/Android-PickerView/blob/Lunar/pickerview/src/main/java/com/bigkoo/pickerview/utils/LunarCalendar.java)就好了.

####使用gradle 依赖:
```java
   compile 'com.bigkoo:pickerview:lunar.1.0'
```

## Demo 图片
![](https://github.com/saiwu-bigkoo/Android-PickerView/blob/Lunar/preview/pickerdemo_lunar.JPG)

## Thanks

- Jerry
