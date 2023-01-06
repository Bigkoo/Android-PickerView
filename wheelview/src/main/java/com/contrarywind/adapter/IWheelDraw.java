package com.contrarywind.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.contrarywind.view.WheelView;

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @since 2022/12/30
 */
public interface IWheelDraw {

    /**
     * 绘制文本时, 额外需要绘制的东西
     */
    void onDrawOnText(WheelView wheelView, Canvas canvas, String text, float textDrawX, float textDrawY, Paint textDrawPaint, int index, Rect textBounds);

}
