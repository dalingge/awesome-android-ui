package com.dalingge.awesome.anim;/**
 * Created by cyril on 16/7/8.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dalingge.awesome.R;

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


public class CircularRingLoadingAnim extends View {

    private Paint mPaint, mPaintRing;
    private float mWidth = 0f;
    private float mPadding = 0f;
    private float startAngle = 0f;

    RectF rectF = new RectF();


    public CircularRingLoadingAnim(Context context) {
        this(context, null);
    }

    public CircularRingLoadingAnim(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularRingLoadingAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();
        mPadding = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaintRing.setColor(Color.argb(100, 255, 255, 255));
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaintRing);
//        mPaint.setColor(Color.RED);
        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        canvas.drawArc(rectF, startAngle, 100, false, mPaint);
    }

    private void initPaint(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularRingLoadingAnim);
        int mRingBgColor = typedArray.getColor(R.styleable.CircularRingLoadingAnim_ring_color, Color.WHITE);
        int mBlockColor= typedArray.getColor(R.styleable.CircularRingLoadingAnim_block_color, Color.WHITE);


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mBlockColor);
        mPaint.setStrokeWidth(8);

        mPaintRing = new Paint();
        mPaintRing.setAntiAlias(true);
        mPaintRing.setStyle(Paint.Style.STROKE);
        mPaintRing.setColor(mRingBgColor);
        mPaintRing.setStrokeWidth(8);
    }

    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 1000);
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
        }
    }

    ValueAnimator valueAnimator;

    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) valueAnimator.getAnimatedValue();
                startAngle = 360 * value;
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

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

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        return valueAnimator;
    }
}
