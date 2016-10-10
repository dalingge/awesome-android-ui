package com.dalingge.awesome.anim;/**
 * Created by cyril on 16/7/8.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

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


public class CircularLoadingAnim extends View {

    private Paint mPaint;

    private float mWidth = 0f;
    private float mAnimatedValue = 0f;
    private int mStartAngle = 0;
    private float mMaxRadius = 4;
    RotateAnimation mProgerssRotateAnim;

    public CircularLoadingAnim(Context context) {
        this(context, null);
    }

    public CircularLoadingAnim(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularLoadingAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();

        mMaxRadius = mWidth / 30f;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < 9; i++) {
            float x2 = (float) ((mWidth / 2.f - mMaxRadius) * Math.cos(mStartAngle + 45f * i * Math.PI / 180f));
            float y2 = (float) ((mWidth / 2.f - mMaxRadius) * Math.sin(mStartAngle + 45f * i * Math.PI / 180f));
            canvas.drawCircle(mWidth / 2.f - x2, mWidth / 2.f - y2, mMaxRadius, mPaint);
        }

        canvas.drawCircle(mWidth / 2.f, mWidth / 2.f, mWidth / 2.f - mMaxRadius * 6, mPaint);


    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mProgerssRotateAnim = new RotateAnimation(0f, 360f, android.view.animation.Animation.RELATIVE_TO_SELF,
                0.5f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
        mProgerssRotateAnim.setRepeatCount(-1);
        mProgerssRotateAnim.setInterpolator(new LinearInterpolator());//不停顿
        mProgerssRotateAnim.setFillAfter(true);//停在最后

    }

    public void startAnim() {
        stopAnim();
        mProgerssRotateAnim.setDuration(3500);
        startAnimation(mProgerssRotateAnim);
    }

    public void stopAnim() {
        clearAnimation();
    }


}
