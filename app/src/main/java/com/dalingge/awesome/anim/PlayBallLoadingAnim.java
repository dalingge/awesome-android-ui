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
import android.graphics.Path;
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


public class PlayBallLoadingAnim extends View {

    private Paint mPaint, mPaintCircle, mPaintBall;
    private float mPaintStrokeWidth;
    private float mHeight = 0f;
    private float mWidth = 0f;
    private float quadToStart = 0f;
    private float mRadius = 0f;
    private float mRadiusBall = 0f;
    private float ballY = 0f;

    Path path = new Path();

    public PlayBallLoadingAnim(Context context) {
        this(context, null);
    }

    public PlayBallLoadingAnim(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayBallLoadingAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        quadToStart = mHeight / 2;
        mRadius = dip2px(3);
        mPaintStrokeWidth = 2;
        ballY = mHeight / 2;
        mRadiusBall = dip2px(4);
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();
        path.moveTo(0 + mRadius * 2 + mPaintStrokeWidth, getMeasuredHeight() / 2);
        path.quadTo(mWidth / 2, quadToStart, mWidth - mRadius * 2 - mPaintStrokeWidth, mHeight / 2);
        mPaint.setStrokeWidth(2);
        canvas.drawPath(path, mPaint);

        mPaintCircle.setStrokeWidth(mPaintStrokeWidth);
        canvas.drawCircle(mRadius + mPaintStrokeWidth, mHeight / 2, mRadius, mPaintCircle);
        canvas.drawCircle(mWidth - mRadius - mPaintStrokeWidth, mHeight / 2, mRadius, mPaintCircle);

        if (ballY - mRadiusBall > mRadiusBall) {
            canvas.drawCircle(mWidth / 2, ballY - mRadiusBall, mRadiusBall, mPaintBall);
        } else {
            canvas.drawCircle(mWidth / 2, mRadiusBall, mRadiusBall, mPaintBall);
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(Color.WHITE);

        mPaintBall = new Paint();
        mPaintBall.setAntiAlias(true);
        mPaintBall.setStyle(Paint.Style.FILL);
        mPaintBall.setColor(Color.WHITE);
    }

    ValueAnimator valueAnimator = null;

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            quadToStart = mHeight / 2;
            ballY = mHeight / 2;
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
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (value > 0.75) {
                    quadToStart = mHeight / 2 - (1f - (float) valueAnimator.getAnimatedValue()) * mHeight / 3f;
                } else {
                    quadToStart = mHeight / 2 + (1f - (float) valueAnimator.getAnimatedValue()) * mHeight / 3f;
                }
                if (value > 0.35f) {
                    ballY = mHeight / 2 - (mHeight / 2 * value);
                } else {
                    ballY = mHeight / 2 + (mHeight / 6 * value);
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        return valueAnimator;
    }

    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 800);
    }
}
