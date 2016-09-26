package com.asiatravel.atpointview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.asiatravel.atpointview.R;
import com.asiatravel.atpointview.model.Point;

/**
 * Created by kuangxiaoguo on 16/9/26.
 */

public class PointView extends View {

    /**
     * view默认宽度
     */
    private static final int DEFAULT_WIDTH = 240;
    /**
     * view默认高度
     */
    private static final int DEFAULT_HEIGHT = 240;

    /**
     * 默认颜色
     */
    private static final int DEFAULT_COLOR = Color.GREEN;
    private Paint mPaint;
    private int width;
    private int height;
    private Point mPoint;
    private int mColor;
    private int minValue;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initPaint();
    }

    /**
     * 获取自定义属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointView);
        mColor = typedArray.getColor(R.styleable.PointView_point_color, DEFAULT_COLOR);
        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        /**
         * 测量view的宽高
         */
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_WIDTH, getResources().getDisplayMetrics()) + getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEIGHT, getResources().getDisplayMetrics()) + getPaddingTop() + getPaddingBottom();
        }
        /**
         * 使用setMeasureDimension方法确定view的最终宽和高
         */
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPoint != null) {
            canvas.drawCircle(width / 2, height / 2, mPoint.getRadius(), mPaint);
        }
    }

    public void startAnimation() {
        /**
         * 使用ValueAnimator.ofObject()方法并使用自定义的Evaluator实现动画
         */
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), new Point(0), new Point(minValue * 2 / 5));
        animator.setDuration(2000);
        /**
         * 设置插值器为BounceInterpolator,其效果为:动画结束的时候弹起
         */
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 通过getAnimatedValue获取我们变化中的Point对象
                 */
                mPoint = (Point) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /**
         * 在onSizeChanged重载方法中获取view的最终宽度和高度
         */
        width = w;
        height = h;
        /**
         * 使用width减去左右的padding
         * 使用height减去上下的padding
         * 并取两者中的最小值用来确定最大弹球的半径
         */
        minValue = Math.min(width - getPaddingLeft() - getPaddingRight(), height - getPaddingTop() - getPaddingBottom());
    }

}
