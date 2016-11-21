package com.bigkoo.pickerview.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.utils.PickerViewAnimateUtil;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.listener.OnDismissListener;

/**
 * Created by Sai on 15/11/22.
 * 精仿iOSPickerViewController控件
 */
public class BasePickerView extends View {
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

    private Context context;
    protected ViewGroup contentContainer;
    private ViewGroup decorView;//activity的根View
    private ViewGroup rootView;//附加View 的 根View

    private OnDismissListener onDismissListener;
    private boolean dismissing;

    private Animation outAnim;
    private Animation inAnim;
    private boolean isShowing;
    private int gravity = Gravity.BOTTOM;


    public BasePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initViews();
        init();
        initEvents();
    }


    public BasePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initViews();
        init();
        initEvents();
    }


    public BasePickerView(Context context) {
        super(context);
        this.context = context;

        initViews();
        init();
        initEvents();
    }


    //public BasePickerView(Context context) {
    //    super(context);
    //    this.context = context;
    //
    //    initViews();
    //    init();
    //    initEvents();
    //}

    //public BasePickerView(Context context){
    //    this.context = context;
    //
    //    initViews();
    //    init();
    //    initEvents();
    //}


    protected void initViews() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        //rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, decorView, false);
        //rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, null);
        rootView = (ViewGroup) ((Activity) context).getLayoutInflater().inflate(R.layout.layout_basepickerview, null);
        //rootView.setLayoutParams(
        //        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
        // .WRAP_CONTENT));
        //contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
        //contentContainer.setLayoutParams(params);
        contentContainer = rootView;
    }


    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }


    public void initEvents() {
        //if (isShowing()) {
        //    return;
        //}
        //isShowing = true;
        onAttached(rootView);
    }


    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        contentContainer.startAnimation(inAnim);
    }


    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return rootView.getParent() != null || isShowing;
    }


    public void dismiss() {
        if (dismissing) {
            return;
        }

        dismissing = true;

        //消失动画
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {

            }


            @Override public void onAnimationEnd(Animation animation) {
                decorView.post(new Runnable() {
                    @Override public void run() {
                        dismissImmediately();
                    }
                });
            }


            @Override public void onAnimationRepeat(Animation animation) {

            }
        });
        contentContainer.startAnimation(outAnim);
    }


    public void dismissImmediately() {
        //从activity根视图移除
        decorView.removeView(rootView);
        isShowing = false;
        dismissing = false;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(BasePickerView.this);
        }
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


    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };


    public View findViewBy(int id) {
        return contentContainer.findViewById(id);
    }
}
