package com.bigkoo.pickerview.lib;

import java.util.TimerTask;

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
        WheelView loopview = loopView;
        loopview.totalScrollY = loopview.totalScrollY - i;
        if (!loopView.isLoop) {
            float itemHeight = loopView.lineSpacingMultiplier * loopView.maxTextHeight;
            if (loopView.totalScrollY <= (int) ((float) (-loopView.initPosition) * itemHeight)) {
                a = 40F;
                loopView.totalScrollY = (int) ((float) (-loopView.initPosition) * itemHeight);
            } else if (loopView.totalScrollY >= (int) ((float) (loopView.getItemsCount() - 1 - loopView.initPosition) * itemHeight)) {
                loopView.totalScrollY = (int) ((float) (loopView.getItemsCount() - 1 - loopView.initPosition) * itemHeight);
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
