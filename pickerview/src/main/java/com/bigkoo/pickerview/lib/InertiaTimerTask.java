package com.bigkoo.pickerview.lib;

import java.util.TimerTask;
/**
 * @TODO<滚动惯性的实现>
 * @author 小嵩
 */
final class InertiaTimerTask extends TimerTask {

    float a;
    final float velocityY;
    final WheelView loopView;

    InertiaTimerTask(WheelView loopview, float velocityY) {
        super();
        loopView = loopview;
        this.velocityY = velocityY;
        a = Integer.MAX_VALUE;
    }

    @Override
    public final void run() {
        if (a == Integer.MAX_VALUE) {
            if (Math.abs(velocityY) > 2000F) {
                if (velocityY > 0.0F) {
                    a = 2000F;
                } else {
                    a = -2000F;
                }
            } else {
                a = velocityY;
            }
        }
        if (Math.abs(a) >= 0.0F && Math.abs(a) <= 20F) {
            loopView.cancelFuture();
            loopView.handler.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
            return;
        }
        int i = (int) ((a * 10F) / 1000F);
        loopView.totalScrollY = loopView.totalScrollY - i;
        if (!loopView.isLoop) {
            float itemHeight = loopView.itemHeight;
            float top = (-loopView.initPosition) * itemHeight;
            float bottom = (loopView.getItemsCount() - 1 - loopView.initPosition) * itemHeight;
            if(loopView.totalScrollY - itemHeight*0.25 < top){
                top = loopView.totalScrollY + i;
            }
            else if(loopView.totalScrollY + itemHeight*0.25 > bottom){
                bottom = loopView.totalScrollY + i;
            }

            if (loopView.totalScrollY <= top){
                a = 40F;
                loopView.totalScrollY = (int)top;
            } else if (loopView.totalScrollY >= bottom) {
                loopView.totalScrollY = (int)bottom;
                a = -40F;
            }
        }
        if (a < 0.0F) {
            a = a + 20F;
        } else {
            a = a - 20F;
        }
        loopView.handler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
    }

}
