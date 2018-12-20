package com.contrarywind.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.interfaces.IPickerViewData;
import com.contrarywind.listener.LoopViewGestureListener;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.timer.InertiaTimerTask;
import com.contrarywind.timer.MessageHandler;
import com.contrarywind.timer.SmoothScrollTimerTask;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 3d滚轮控件
 */
public class WheelView extends View {

    public enum ACTION { // 点击，滑翔(滑到尽头)，拖拽事件
        CLICK, FLING, DAGGLE
    }

    public enum DividerType { // 分隔线类型
        FILL, WRAP
    }

    public enum LabelGravity{ // label
        RIGHT/*最右边*/, AFTER_CONTENT/*文字后面*/
    }

    private DividerType dividerType;//分隔线类型

    private Context context;
    private Handler handler;
    private GestureDetector gestureDetector;
    private OnItemSelectedListener onItemSelectedListener;

    private boolean isOptions = false;
    private boolean isCenterLabel = true;

    // Timer mTimer;
    private ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    private Paint paintOuterText;
    private Paint paintCenterText;
    private Paint paintIndicator;
    private Paint paintCenterBg;

    private WheelAdapter adapter;

    private String label;//附加单位
    private int textSize;//选项的文字大小
    private int maxTextWidth;
    private int maxTextHeight;
    private int textXOffset;
    private float itemHeight;//每行高度


    private Typeface typeface = Typeface.MONOSPACE;//字体样式，默认是等宽字体
    private int textColorOut;
    private int textColorCenter;
    private int dividerColor;
    private int centerItemBgColor;

    // 条目间距倍数
    private float lineSpacingMultiplier = 1.6F;
    private boolean isLoop;

    // 第一条线Y坐标值
    private float firstLineY;
    //第二条线Y坐标
    private float secondLineY;
    //中间label绘制的Y坐标
    private float centerY;

    //当前滚动总高度y值
    private float totalScrollY;

    //初始化默认选中项
    private int initPosition;

    //选中的Item是第几个
    private int selectedItem;
    private int preCurrentIndex;
    //滚动偏移值,用于记录滚动了多少个item
    private int change;

    // 绘制几个条目，实际上第一项和最后一项Y轴压缩成0%了，所以可见的数目实际为9
    private int itemsVisible = 11;

    private int measuredHeight;// WheelView 控件高度
    private int measuredWidth;// WheelView 控件宽度

    // 半径
    private int radius;

    private int mOffset = 0;
    private float previousY = 0;
    private long startTime = 0;

    // 修改这个值可以改变滑行速度
    private static final int VELOCITY_FLING = 5;
    private int widthMeasureSpec;

    private int mGravity = Gravity.CENTER;
    private LabelGravity mLabelGravity = LabelGravity.RIGHT;
    private int drawCenterContentStart = 0;//中间选中文字开始绘制位置
    private int drawOutContentStart = 0;//非中间文字开始绘制位置
    private static final float SCALE_CONTENT = 0.8F;//非中间文字则用此控制高度，压扁形成3d错觉
    private float CENTER_CONTENT_OFFSET;//偏移量

    //item内容的最大宽度
    private int maxItemContentWidth;
    private String maxItemContent = "";

    private final float DEFAULT_TEXT_TARGET_SKEWX = 0.5f;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        textSize = getResources().getDimensionPixelSize(R.dimen.pickerview_textsize);//默认大小

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度比（0.75/1.0/1.5/2.0/3.0）

        if (density < 1) {//根据密度不同进行适配
            CENTER_CONTENT_OFFSET = 2.4F;
        } else if (1 <= density && density < 2) {
            CENTER_CONTENT_OFFSET = 3.6F;
        } else if (1 <= density && density < 2) {
            CENTER_CONTENT_OFFSET = 4.5F;
        } else if (2 <= density && density < 3) {
            CENTER_CONTENT_OFFSET = 6.0F;
        } else if (density >= 3) {
            CENTER_CONTENT_OFFSET = density * 2.5F;
        }

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.pickerview, 0, 0);
            mGravity = a.getInt(R.styleable.pickerview_wheelview_gravity, Gravity.CENTER);
            textColorOut = a.getColor(R.styleable.pickerview_wheelview_textColorOut, 0xFFa8a8a8);
            textColorCenter = a.getColor(R.styleable.pickerview_wheelview_textColorCenter, 0xFF2a2a2a);
            centerItemBgColor = a.getColor(R.styleable.pickerview_wheelview_centerItemBgColor, 0x00000000);
            dividerColor = a.getColor(R.styleable.pickerview_wheelview_dividerColor, 0xFFd5d5d5);
            textSize = a.getDimensionPixelOffset(R.styleable.pickerview_wheelview_textSize, textSize);
            lineSpacingMultiplier = a.getFloat(R.styleable.pickerview_wheelview_lineSpacingMultiplier, lineSpacingMultiplier);
            a.recycle();//回收内存
        }

        judgeLineSpace();
        initLoopView(context);
    }

    /**
     * 判断间距是否在1.0-4.0之间
     */
    private void judgeLineSpace() {
        if (lineSpacingMultiplier < 1.0f) {
            lineSpacingMultiplier = 1.0f;
        } else if (lineSpacingMultiplier > 4.0f) {
            lineSpacingMultiplier = 4.0f;
        }
    }

    private void initLoopView(Context context) {
        this.context = context;
        handler = new MessageHandler(this);
        gestureDetector = new GestureDetector(context, new LoopViewGestureListener(this));
        gestureDetector.setIsLongpressEnabled(false);
        isLoop = true;

        totalScrollY = 0;
        initPosition = -1;
        initPaints();
    }

    private void initPaints() {
        paintOuterText = new Paint();
        paintOuterText.setColor(textColorOut);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTypeface(typeface);
        paintOuterText.setTextSize(textSize);

        paintCenterText = new Paint();
        paintCenterText.setColor(textColorCenter);
        paintCenterText.setAntiAlias(true);
        paintCenterText.setTextScaleX(1.1F);
        paintCenterText.setTypeface(typeface);
        paintCenterText.setTextSize(textSize);

        paintIndicator = new Paint();
        paintIndicator.setColor(dividerColor);
        paintIndicator.setAntiAlias(true);

        paintCenterBg = new Paint();
        paintCenterBg.setColor(centerItemBgColor);
        paintCenterBg.setAntiAlias(true);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void remeasure() {//重新测量
        if (adapter == null) {
            return;
        }

        measureTextWidthHeight();

        //半圆的周长 = item高度乘以item数目-1
        int halfCircumference = (int) (itemHeight * (itemsVisible - 1));
        //整个圆的周长除以PI得到直径，这个直径用作控件的总高度
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        //求出半径
        radius = (int) (halfCircumference / Math.PI);
        //控件宽度，这里支持weight
        measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        //计算两条横线 和 选中项画笔的基线Y位置
        firstLineY = (measuredHeight - itemHeight) / 2.0F;
        secondLineY = (measuredHeight + itemHeight) / 2.0F;
        centerY = secondLineY - (itemHeight - maxTextHeight) / 2.0f - CENTER_CONTENT_OFFSET;

        //初始化显示的item的position
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (adapter.getItemsCount() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }
        preCurrentIndex = initPosition;
    }

    /**
     * 计算最大length的Text的宽高度
     */
    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < adapter.getItemsCount(); i++) {
            String s1 = getContentText(adapter.getItem(i));
            paintCenterText.getTextBounds(s1, 0, s1.length(), rect);

            int textWidth = rect.width();

            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
            paintCenterText.getTextBounds("\u661F\u671F", 0, 2, rect); // 星期的字符编码（以它为标准高度）

            maxTextHeight = rect.height() + 2;

        }
        itemHeight = lineSpacingMultiplier * maxTextHeight;
    }

    public void smoothScroll(ACTION action) {//平滑滚动的实现
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            mOffset = (int) ((totalScrollY % itemHeight + itemHeight) % itemHeight);
            if ((float) mOffset > itemHeight / 2.0F) {//如果超过Item高度的一半，滚动到下一个Item去
                mOffset = (int) (itemHeight - (float) mOffset);
            } else {
                mOffset = -mOffset;
            }
        }
        //停止的时候，位置有偏移，不是全部都能正确停止到中间位置的，这里把文字位置挪回中间去
        mFuture = mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }

    public final void scrollBy(float velocityY) {//滚动惯性的实现
        cancelFuture();
        mFuture = mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0, VELOCITY_FLING, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public final void setCyclic(boolean cyclic) {
        isLoop = cyclic;
    }

    public final void setTypeface(Typeface font) {
        typeface = font;
        paintOuterText.setTypeface(typeface);
        paintCenterText.setTypeface(typeface);
    }

    public final void setTextSize(float size) {
        if (size > 0.0F) {
            textSize = (int) (context.getResources().getDisplayMetrics().density * size);
            paintOuterText.setTextSize(textSize);
            paintCenterText.setTextSize(textSize);
        }
    }

    public final void setCurrentItem(int currentItem) {
        //不添加这句,当这个wheelView不可见时,默认都是0,会导致获取到的时间错误
        this.selectedItem = currentItem;
        this.initPosition = currentItem;
        totalScrollY = 0;//回归顶部，不然重设setCurrentItem的话位置会偏移的，就会显示出不对位置的数据
        invalidate();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        this.onItemSelectedListener = OnItemSelectedListener;
    }

    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        measureMaxItemContentWidth();
        remeasure();
        invalidate();
    }

    private void measureMaxItemContentWidth(){
        if(adapter != null){
            int maxWidth = 0;
            for (int i = 0; i < adapter.getItemsCount(); i++) {
                String content = adapter.getItem(i).toString();
                int width = getTextWidth(paintCenterText, content);
                if(width > maxWidth){
                    maxWidth = width;
                    maxItemContent = content;
                }
            }
            maxItemContentWidth = maxWidth;
        }
    }

    public final WheelAdapter getAdapter() {
        return adapter;
    }

    public final int getCurrentItem() {
        // return selectedItem;
        if (adapter == null) {
            return 0;
        }
        if (isLoop && (selectedItem < 0 || selectedItem >= adapter.getItemsCount())) {
            return Math.max(0, Math.min(Math.abs(Math.abs(selectedItem) - adapter.getItemsCount()), adapter.getItemsCount() - 1));
        }
        return Math.max(0, Math.min(selectedItem, adapter.getItemsCount() - 1));
    }

    public final void onItemSelected() {
        if (onItemSelectedListener != null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemSelectedListener.onItemSelected(getCurrentItem());
                }
            }, 200L);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (adapter == null) {
            return;
        }
        //initPosition越界会造成preCurrentIndex的值不正确
        initPosition = Math.min(Math.max(0, initPosition), adapter.getItemsCount() - 1);

        //可见的item数组
        @SuppressLint("DrawAllocation")
        Object visibles[] = new Object[itemsVisible];
        //滚动的Y值高度除去每行Item的高度，得到滚动了多少个item，即change数
        change = (int) (totalScrollY / itemHeight);
        // Log.d("change", "" + change);

        try {
            //滚动中实际的预选中的item(即经过了中间位置的item) ＝ 滑动前的位置 ＋ 滑动相对位置
            preCurrentIndex = initPosition + change % adapter.getItemsCount();

        } catch (ArithmeticException e) {
            Log.e("WheelView", "出错了！adapter.getItemsCount() == 0，联动数据不匹配");
        }
        if (!isLoop) {//不循环的情况
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {
                preCurrentIndex = adapter.getItemsCount() - 1;
            }
        } else {//循环
            if (preCurrentIndex < 0) {//举个例子：如果总数是5，preCurrentIndex ＝ －1，那么preCurrentIndex按循环来说，其实是0的上面，也就是4的位置
                preCurrentIndex = adapter.getItemsCount() + preCurrentIndex;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {//同理上面,自己脑补一下
                preCurrentIndex = preCurrentIndex - adapter.getItemsCount();
            }
        }
        //跟滚动流畅度有关，总滑动距离与每个item高度取余，即并不是一格格的滚动，每个item不一定滚到对应Rect里的，这个item对应格子的偏移值
        float itemHeightOffset = (totalScrollY % itemHeight);

        // 设置数组中每个元素的值
        int counter = 0;
        while (counter < itemsVisible) {
            int index = preCurrentIndex - (itemsVisible / 2 - counter);//索引值，即当前在控件中间的item看作数据源的中间，计算出相对源数据源的index值
            //判断是否循环，如果是循环数据源也使用相对循环的position获取对应的item值，如果不是循环则超出数据源范围使用""空白字符串填充，在界面上形成空白无数据的item项
            if (isLoop) {
                index = getLoopMappingIndex(index);
                visibles[counter] = adapter.getItem(index);
            } else if (index < 0) {
                visibles[counter] = "";
            } else if (index > adapter.getItemsCount() - 1) {
                visibles[counter] = "";
            } else {
                visibles[counter] = adapter.getItem(index);
            }

            counter++;

        }

        //绘制中间item背景颜色
        canvas.drawRect(0.0f, firstLineY, measuredWidth, secondLineY, paintCenterBg);

        //绘制中间两条横线
        if (dividerType == DividerType.WRAP) {//横线长度仅包裹内容
            float startX;
            float endX;

            if (TextUtils.isEmpty(label)) {//隐藏Label的情况
                startX = (measuredWidth - maxTextWidth) / 2 - 12;
            } else {
                startX = (measuredWidth - maxTextWidth) / 4 - 12;
            }

            if (startX <= 0) {//如果超过了WheelView的边缘
                startX = 10;
            }
            endX = measuredWidth - startX;
            canvas.drawLine(startX, firstLineY, endX, firstLineY, paintIndicator);
            canvas.drawLine(startX, secondLineY, endX, secondLineY, paintIndicator);
        } else {
            canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, paintIndicator);
            canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, paintIndicator);
        }

        //只显示选中项Label文字的模式，并且Label文字不为空，则进行绘制
        if (!TextUtils.isEmpty(label) && isCenterLabel) {
            if(mLabelGravity == LabelGravity.RIGHT){
                //绘制文字，靠右并留出空隙
                int drawRightContentStart = measuredWidth - getTextWidth(paintCenterText, label);
                canvas.drawText(label, drawRightContentStart - CENTER_CONTENT_OFFSET, centerY, paintCenterText);
            }else {
                measuredCenterContentStart(maxItemContent);
                int drawRightContentStart = drawCenterContentStart + maxItemContentWidth;
                canvas.drawText(label, drawRightContentStart + CENTER_CONTENT_OFFSET, centerY, paintCenterText);
            }
        }

        counter = 0;
        while (counter < itemsVisible) {
            canvas.save();
            // 弧长 L = itemHeight * counter - itemHeightOffset
            // 求弧度 α = L / r  (弧长/半径) [0,π]
            double radian = ((itemHeight * counter - itemHeightOffset)) / radius;
            // 弧度转换成角度(把半圆以Y轴为轴心向右转90度，使其处于第一象限及第四象限
            // angle [-90°,90°]
            float angle = (float) (90D - (radian / Math.PI) * 180D);//item第一项,从90度开始，逐渐递减到 -90度

            // 计算取值可能有细微偏差，保证负90°到90°以外的不绘制
            if (angle >= 90F || angle <= -90F) {
                canvas.restore();
            } else {
                // 根据当前角度计算出偏差系数，用以在绘制时控制文字的 水平移动 透明度 倾斜程度
                float offsetCoefficient = (float) Math.pow(Math.abs(angle) / 90f, 2.2);
                //获取内容文字
                String contentText;

                //如果是label每项都显示的模式，并且item内容不为空、label 也不为空
                if (!isCenterLabel && !TextUtils.isEmpty(label) && !TextUtils.isEmpty(getContentText(visibles[counter]))) {
                    contentText = getContentText(visibles[counter]) + label;
                } else {
                    contentText = getContentText(visibles[counter]);
                }

                reMeasureTextSize(contentText);
                //计算开始绘制的位置
                measuredCenterContentStart(contentText);
                measuredOutContentStart(contentText);
                float translateY = (float) (radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                //根据Math.sin(radian)来更改canvas坐标系原点，然后缩放画布，使得文字高度进行缩放，形成弧形3d视觉差
                canvas.translate(0.0F, translateY);
//                canvas.scale(1.0F, (float) Math.sin(radian));
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    // 条目经过第一条线
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALE_CONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1.0F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTER_CONTENT_OFFSET, paintCenterText);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    // 条目经过第二条线
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1.0F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTER_CONTENT_OFFSET, paintCenterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALE_CONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    // 中间条目
                    // canvas.clipRect(0, 0, measuredWidth, maxTextHeight);
                    //让文字居中
                    float Y = maxTextHeight - CENTER_CONTENT_OFFSET;//因为圆弧角换算的向下取值，导致角度稍微有点偏差，加上画笔的基线会偏上，因此需要偏移量修正一下
                    canvas.drawText(contentText, drawCenterContentStart, Y, paintCenterText);

                    //设置选中项
                    selectedItem = preCurrentIndex - (itemsVisible / 2 - counter);

                } else {
                    // 其他条目
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALE_CONTENT);
                    // 控制文字倾斜角度
                    paintOuterText.setTextSkewX((textXOffset == 0 ? 0 : (textXOffset > 0 ? 1 : -1)) * (angle > 0 ? -1 : 1) * DEFAULT_TEXT_TARGET_SKEWX * offsetCoefficient);
                    // 控制透明度
                    paintOuterText.setAlpha((int) ((1 - offsetCoefficient) * 255));
                    // 控制文字水平偏移距离
                    canvas.drawText(contentText, drawOutContentStart + textXOffset * offsetCoefficient, maxTextHeight, paintOuterText);
                    canvas.restore();
                }
                canvas.restore();
                paintCenterText.setTextSize(textSize);
            }
            counter++;
        }
    }

    /**
     * reset the size of the text Let it can fully display
     *
     * @param contentText item text content.
     */
    private void reMeasureTextSize(String contentText) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
        int width = rect.width();
        int size = textSize;
        while (width > measuredWidth) {
            size--;
            //设置2条横线中间的文字大小
            paintCenterText.setTextSize(size);
            paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
            width = rect.width();
        }
        //设置2条横线外面的文字大小
        paintOuterText.setTextSize(size);
    }


    //递归计算出对应的index
    private int getLoopMappingIndex(int index) {
        if (index < 0) {
            index = index + adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        } else if (index > adapter.getItemsCount() - 1) {
            index = index - adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        }
        return index;
    }

    /**
     * 获取所显示的数据源
     *
     * @param item data resource
     * @return 对应显示的字符串
     */
    private String getContentText(Object item) {
        if (item == null) {
            return "";
        } else if (item instanceof IPickerViewData) {
            return ((IPickerViewData) item).getPickerViewText();
        } else if (item instanceof Integer) {
            //如果为整形则最少保留两位数.
            return String.format(Locale.getDefault(), "%02d", (int) item);
        }
        return item.toString();
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER://显示内容居中
                if (isOptions || label == null || label.equals("") || !isCenterLabel) {
                    drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                } else {//只显示中间label时，时间选择器内容偏左一点，留出空间绘制单位标签
                    if(mLabelGravity == LabelGravity.RIGHT){
                        drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.25);
                    }else {
                        drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                    }
                }
                break;
            case Gravity.LEFT:
                drawCenterContentStart = 0;
                break;
            case Gravity.RIGHT://添加偏移量
                drawCenterContentStart = measuredWidth - rect.width() - (int) CENTER_CONTENT_OFFSET;
                break;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        paintOuterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity) {
            case Gravity.CENTER:
                if (isOptions || label == null || label.equals("") || !isCenterLabel) {
                    drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                } else {//只显示中间label时，时间选择器内容偏左一点，留出空间绘制单位标签
                    if(mLabelGravity == LabelGravity.RIGHT){
                        drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.25);
                    }else {
                        drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                    }
                }
                break;
            case Gravity.LEFT:
                drawOutContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawOutContentStart = measuredWidth - rect.width() - (int) CENTER_CONTENT_OFFSET;
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = gestureDetector.onTouchEvent(event);
        boolean isIgnore = false;//超过边界滑动时，不再绘制UI。

        float top = -initPosition * itemHeight;
        float bottom = (adapter.getItemsCount() - 1 - initPosition) * itemHeight;
        float ratio = 0.25f;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                cancelFuture();
                previousY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                float dy = previousY - event.getRawY();
                previousY = event.getRawY();
                totalScrollY = totalScrollY + dy;

                // normal mode。
                if (!isLoop) {
                    if ((totalScrollY - itemHeight * ratio < top && dy < 0)
                            || (totalScrollY + itemHeight * ratio > bottom && dy > 0)) {
                        //快滑动到边界了，设置已滑动到边界的标志
                        totalScrollY -= dy;
                        isIgnore = true;
                    } else {
                        isIgnore = false;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            default:
                if (!eventConsumed) {//未消费掉事件

                    /**
                     *@describe <关于弧长的计算>
                     *
                     * 弧长公式： L = α*R
                     * 反余弦公式：arccos(cosα) = α
                     * 由于之前是有顺时针偏移90度，
                     * 所以实际弧度范围α2的值 ：α2 = π/2-α    （α=[0,π] α2 = [-π/2,π/2]）
                     * 根据正弦余弦转换公式 cosα = sin(π/2-α)
                     * 代入，得： cosα = sin(π/2-α) = sinα2 = (R - y) / R
                     * 所以弧长 L = arccos(cosα)*R = arccos((R - y) / R)*R
                     */

                    float y = event.getY();
                    double L = Math.acos((radius - y) / radius) * radius;
                    //item0 有一半是在不可见区域，所以需要加上 itemHeight / 2
                    int circlePosition = (int) ((L + itemHeight / 2) / itemHeight);
                    float extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight;
                    //已滑动的弧长值
                    mOffset = (int) ((circlePosition - itemsVisible / 2) * itemHeight - extraOffset);

                    if ((System.currentTimeMillis() - startTime) > 120) {
                        // 处理拖拽事件
                        smoothScroll(ACTION.DAGGLE);
                    } else {
                        // 处理条目点击事件
                        smoothScroll(ACTION.CLICK);
                    }
                }
                break;
        }
        if (!isIgnore && event.getAction() != MotionEvent.ACTION_DOWN) {
            invalidate();
        }
        return true;
    }

    public int getItemsCount() {
        return adapter != null ? adapter.getItemsCount() : 0;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void isCenterLabel(boolean isCenterLabel) {
        this.isCenterLabel = isCenterLabel;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int getTextWidth(Paint paint, String str) { //calculate text width
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public void setIsOptions(boolean options) {
        isOptions = options;
    }

    public void setTextColorOut(int textColorOut) {

        this.textColorOut = textColorOut;
        paintOuterText.setColor(this.textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        paintCenterText.setColor(this.textColorCenter);
    }

    public void setTextXOffset(int textXOffset) {
        this.textXOffset = textXOffset;
        if (textXOffset != 0) {
            paintCenterText.setTextScaleX(1.0f);
        }
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        paintIndicator.setColor(dividerColor);
    }

    public void setDividerType(DividerType dividerType) {
        this.dividerType = dividerType;
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        if (lineSpacingMultiplier != 0) {
            this.lineSpacingMultiplier = lineSpacingMultiplier;
            judgeLineSpace();
        }
    }

    public boolean isLoop() {
        return isLoop;
    }

    public float getTotalScrollY() {
        return totalScrollY;
    }

    public void setTotalScrollY(float totalScrollY) {
        this.totalScrollY = totalScrollY;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getItemsVisibleNum() {
        return itemsVisible;
    }

    public void setItemsVisibleNum(int itemsVisible) {
        this.itemsVisible = itemsVisible;
    }

    public int getCenterItemBgColor() {
        return centerItemBgColor;
    }

    public void setCenterItemBgColor(int centerItemBgColor) {
        this.centerItemBgColor = centerItemBgColor;
        paintCenterBg.setColor(centerItemBgColor);
    }

    public LabelGravity getLabelGravity() {
        return mLabelGravity;
    }

    public void setLabelGravity(LabelGravity mLabelGravity) {
        this.mLabelGravity = mLabelGravity;
    }
  
    public int getInitPosition() {
        return initPosition;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}