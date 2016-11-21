DialogPickerView 
==========

在[原有基础](https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/README.md)添加支持dialog样式。

## Demo 图片
![Demo图片](https://github.com/sunxu3074/Android-Dialog-PickerView/blob/master/preview/pick_city.gif)

## 说明
- 城市列表资源是个json文件，存在assets下,可能不是最新的。不在服务器取得好处是你可以改字段来适应需求，例如：在省下面添加 '只选择省',这样就能单选省这个单位了。
- dialog的样式可以自定义，城市列表这个滚轮也可以自定义。例如更改字体大小，居中还是左右，进入的字体颜色，划出的字体颜色，中间分割线的颜色等等。
- json实体类需要继承IPickerViewData接口，实现的方法返回的是滚轮上的对应的城市名字。
## 感谢
- [material-dialogs](https://github.com/afollestad/material-dialogs)
