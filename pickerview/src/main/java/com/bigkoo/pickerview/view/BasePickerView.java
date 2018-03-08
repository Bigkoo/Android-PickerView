package com.bigkoo.pickerview.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.utils.PickerViewAnimateUtil;
import com.contrarywind.view.WheelView;

/**
 * Created by Sai on 15/11/22.
 * 精仿iOSPickerViewController控件
 */
public class BasePickerView {
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );

    private Context context;
    protected ViewGroup contentContainer;
    public ViewGroup decorView;//显示pickerview的根View,默认是activity的根view
    private ViewGroup rootView;//附加View 的 根View
    private ViewGroup dialogView;//附加Dialog 的 根View

    protected int pickerview_timebtn_nor = 0xFF057dff;
    protected int pickerview_timebtn_pre = 0xFFc2daf5;
    protected int pickerview_bg_topbar = 0xFFf5f5f5;
    protected int pickerview_topbar_title = 0xFF000000;
    protected int bgColor_default = 0xFFFFFFFF;


    //******* 公有字段，抽取到BasePickerView里  ******//
    protected String textContentConfirm;//确定按钮文字
    protected String textContentCancel;//取消按钮文字
    protected String textContentTitle;//标题文字

    protected int textColorConfirm;//确定按钮颜色
    protected int textColorCancel;//取消按钮颜色
    protected int textColorTitle;//标题颜色

    protected int bgColorWheel;//滚轮背景颜色
    protected int bgColorTitle;//标题背景颜色

    protected int textSizeSubmitCancel;//确定取消按钮大小
    protected int textSizeTitle;//标题文字大小
    protected int textSizeContent;//内容文字大小

    protected int textColorOut; //分割线以外的文字颜色
    protected int textColorCenter; //分割线之间的文字颜色
    protected int dividerColor; //分割线的颜色
    protected int backgroundId; //显示时的外部背景色颜色,默认是灰色

    // 条目间距倍数 默认1.6
    protected float lineSpacingMultiplier;
    protected boolean isDialog;//是否是对话框模式

    protected boolean cancelable;//是否能取消
    protected boolean isCenterLabel;//是否只显示中间的label
    protected Typeface font;//字体样式
    protected WheelView.DividerType dividerType;//分隔线类型


    private OnDismissListener onDismissListener;
    private boolean dismissing;

    private Animation outAnim;
    private Animation inAnim;
    private boolean isShowing;
    protected int gravity = Gravity.BOTTOM;


    private Dialog mDialog;
    protected View clickView;//是通过哪个View弹出的
    private boolean isAnim = true;

    public BasePickerView(Context context) {
        this.context = context;
    }


    protected void initViews(int backgroudId) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (isDialog()) {
            //如果是对话框模式
            dialogView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, null, false);
            //设置界面的背景为透明
            dialogView.setBackgroundColor(Color.TRANSPARENT);
            //这个是真正要加载时间选取器的父布局
            contentContainer = (ViewGroup) dialogView.findViewById(R.id.content_container);
            //设置对话框 左右间距屏幕30
            this.params.leftMargin = 30;
            this.params.rightMargin = 30;
            contentContainer.setLayoutParams(this.params);
            //创建对话框
            createDialog();
            //给背景设置点击事件,这样当点击内容以外的地方会关闭界面
            dialogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        } else {
            //如果只是要显示在屏幕的下方
            //decorView是activity的根View
            if (decorView == null) {
                decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
            }
            //将控件添加到decorView中
            rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, decorView, false);
            rootView.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));
            if (backgroudId != -1) {
                rootView.setBackgroundColor(backgroudId);
            }
            //这个是真正要加载时间选取器的父布局
            contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
            contentContainer.setLayoutParams(params);
        }
        setKeyBackCancelable(true);
    }

    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }

    protected void initEvents() {
    }


    /**
     * @param v      (是通过哪个View弹出的)
     * @param isAnim 是否显示动画效果
     */
    public void show(View v, boolean isAnim) {
        this.clickView = v;
        this.isAnim = isAnim;
        show();
    }

    public void show(boolean isAnim) {
        this.isAnim = isAnim;
        show();
    }

    public void show(View v) {
        this.clickView = v;
        show();
    }


    /**
     * 添加View到根视图
     */
    public void show() {
        if (isDialog()) {
            showDialog();
        } else {
            if (isShowing()) {
                return;
            }
            isShowing = true;
            onAttached(rootView);
            rootView.requestFocus();
        }
    }


    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        decorView.addView(view);
        if (isAnim) {
            contentContainer.startAnimation(inAnim);
        }
    }


    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        if (isDialog()) {
            return false;
        } else {
            return rootView.getParent() != null || isShowing;
        }

    }

    public void dismiss() {
        if (isDialog()) {
            dismissDialog();
        } else {
            if (dismissing) {
                return;
            }

            if (isAnim) {
                //消失动画
                outAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dismissImmediately();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                contentContainer.startAnimation(outAnim);
            } else {
                dismissImmediately();
            }
            dismissing = true;
        }


    }

    public void dismissImmediately() {

        decorView.post(new Runnable() {
            @Override
            public void run() {
                //从根视图移除
                decorView.removeView(rootView);
                isShowing = false;
                dismissing = false;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(BasePickerView.this);
                }
            }
        });


    }

    public Animation getInAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public void setKeyBackCancelable(boolean isCancelable) {

        ViewGroup View;
        if (isDialog()) {
            View = dialogView;
        } else {
            View = rootView;
        }

        View.setFocusable(isCancelable);
        View.setFocusableInTouchMode(isCancelable);
        if (isCancelable) {
            View.setOnKeyListener(onKeyBackListener);
        } else {
            View.setOnKeyListener(null);
        }
    }

    private View.OnKeyListener onKeyBackListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_DOWN
                    && isShowing()) {
                dismiss();
                return true;
            }
            return false;
        }
    };

    protected BasePickerView setOutSideCancelable(boolean isCancelable) {

        if (rootView != null) {
            View view = rootView.findViewById(R.id.outmost_container);

            if (isCancelable) {
                view.setOnTouchListener(onCancelableTouchListener);
            } else {
                view.setOnTouchListener(null);
            }
        }

        return this;
    }

    /**
     * 设置对话框模式是否可以点击外部取消
     */
    public void setDialogOutSideCancelable() {
        if (mDialog != null) {
            mDialog.setCancelable(cancelable);
        }
    }


    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    public View findViewById(int id) {
        return contentContainer.findViewById(id);
    }

    public void createDialog() {
        if (dialogView != null) {
            mDialog = new Dialog(context, R.style.custom_dialog2);
            mDialog.setCancelable(cancelable);//不能点外面取消,也不能点back取消
            mDialog.setContentView(dialogView);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.pickerview_dialogAnim);
                dialogWindow.setGravity(Gravity.CENTER);//可以改成Bottom
            }

            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss(BasePickerView.this);
                    }
                }
            });
        }

    }

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public boolean isDialog() {
        return false;
    }

}
