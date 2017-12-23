package com.contrarywind.view;

import java.util.TimerTask;

/**
 * @author 小嵩
 * @describe <平滑滚动的实现>
 */
final class SmoothScrollTimerTask extends TimerTask {

    private int realTotalOffset;
    private int realOffset;
    private int offset;
    private final WheelView wheelView;

    SmoothScrollTimerTask(WheelView wheelView, int offset) {
        this.wheelView = wheelView;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        //把要滚动的范围细分成10小份，按10小份单位来重绘
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }

        if (Math.abs(realTotalOffset) <= 1) {
            wheelView.cancelFuture();
            wheelView.handler.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
        } else {
            wheelView.totalScrollY = wheelView.totalScrollY + realOffset;

            //这里如果不是循环模式，则点击空白位置需要回滚，不然就会出现选到－1 item的 情况
            if (!wheelView.isLoop()) {
                float itemHeight = wheelView.itemHeight;
                float top = (float) (-wheelView.initPosition) * itemHeight;
                float bottom = (float) (wheelView.getItemsCount() - 1 - wheelView.initPosition) * itemHeight;
                if (wheelView.totalScrollY <= top || wheelView.totalScrollY >= bottom) {
                    wheelView.totalScrollY = wheelView.totalScrollY - realOffset;
                    wheelView.cancelFuture();
                    wheelView.handler.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                    return;
                }
            }
            wheelView.handler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
