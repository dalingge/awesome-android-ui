package com.dalingge.awesome.anim;/**
 * Created by cyril on 16/7/11.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * ----------Dragon be here!----------/
 * ***┏┓******┏┓*********
 * *┏━┛┻━━━━━━┛┻━━┓*******
 * *┃             ┃*******
 * *┃     ━━━     ┃*******
 * *┃             ┃*******
 * *┃  ━┳┛   ┗┳━  ┃*******
 * *┃             ┃*******
 * *┃     ━┻━     ┃*******
 * *┃             ┃*******
 * *┗━━━┓     ┏━━━┛*******
 * *****┃     ┃神兽保佑*****
 * *****┃     ┃代码无BUG！***
 * *****┃     ┗━━━━━━━━┓*****
 * *****┃              ┣┓****
 * *****┃              ┏┛****
 * *****┗━┓┓┏━━━━┳┓┏━━━┛*****
 * *******┃┫┫****┃┫┫********
 * *******┗┻┛****┗┻┛*********
 * ━━━━━━神兽出没━━━━━━by:wangziren
 */


public class EatBeanLoadingAnim extends View {
    private Paint mPaint, mPaintEye;

    private float mWidth = 0f;
    private float mHeight = 0f;
    private float mPadding = 5f;

    private float eatErWidth = 60f;
    private float eatErPositionX = 0f;
    int eatSpeed = 5;
    private float beansWidth = 10f;

    private float mAngle = 34;
    private float eatErStartAngle = mAngle;
    private float eatErEndAngle = 360 - 2 * eatErStartAngle;


    public EatBeanLoadingAnim(Context context) {
        this(context, null);
    }

    public EatBeanLoadingAnim(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EatBeanLoadingAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float eatRightX = mPadding + eatErWidth + eatErPositionX;
        RectF rectF = new RectF(mPadding + eatErPositionX, mHeight / 2 - eatErWidth / 2,
                eatRightX, mHeight / 2 + eatErWidth / 2);
        canvas.drawArc(rectF, eatErStartAngle, eatErEndAngle, true, mPaint);
        canvas.drawCircle(mPadding + eatErPositionX + eatErWidth / 2, mHeight / 2 - eatErWidth / 4,
                beansWidth / 2, mPaintEye);

        int beansCount = (int) ((mWidth - mPadding * 2 - eatErWidth) / beansWidth / 2);
        for (int i = 0; i < beansCount; i++) {
            float x = beansCount * i + beansWidth / 2 + mPadding + eatErWidth;
            if (x > eatRightX) {
                canvas.drawCircle(x, mHeight / 2, beansWidth / 2, mPaint);
            }
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mPaintEye = new Paint();
        mPaintEye.setAntiAlias(true);
        mPaintEye.setStyle(Paint.Style.FILL);
        mPaintEye.setColor(Color.BLACK);
    }

    ValueAnimator valueAnimator = null;

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
            eatErPositionX = 0;
            postInvalidate();
        }
    }

    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mAnimatedValue = (float) valueAnimator.getAnimatedValue();
                eatErPositionX = (mWidth - 2 * mPadding - eatErWidth) * mAnimatedValue;
                eatErStartAngle = mAngle * (1 - (mAnimatedValue * eatSpeed - (int) (mAnimatedValue * eatSpeed)));
                invalidate();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        return valueAnimator;
    }

    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 3500);
    }

}
