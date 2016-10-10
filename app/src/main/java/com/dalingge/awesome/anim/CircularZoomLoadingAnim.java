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


public class CircularZoomLoadingAnim extends View {

    private Paint mPaint;
    private float mWidth = 0f;
    private float mHeight = 0f;
    private float mMaxRadius = 8;
    private int circularCount = 3;
    private float mAnimatedValue = 1.0f;
    private int mJumpValue = 0;

    public CircularZoomLoadingAnim(Context context) {
        this(context, null);
    }

    public CircularZoomLoadingAnim(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularZoomLoadingAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circularX = mWidth / circularCount;
        for (int i = 0; i < circularCount; i++) {
            if (i == mJumpValue % circularCount) {
                canvas.drawCircle(i * circularX + circularX / 2f, mHeight / 2, mMaxRadius * mAnimatedValue, mPaint);
            } else {
                canvas.drawCircle(i * circularX + circularX / 2f, mHeight / 2, mMaxRadius, mPaint);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 500);
    }

    ValueAnimator valueAnimator = null;

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            mAnimatedValue = 0f;
            mJumpValue = 0;
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
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
                mAnimatedValue = (float) valueAnimator.getAnimatedValue();
                if (mAnimatedValue < 0.2) {
                    mAnimatedValue = 0.2f;
                }
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
                mJumpValue++;
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
}
