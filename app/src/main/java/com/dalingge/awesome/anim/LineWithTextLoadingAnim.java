package com.dalingge.awesome.anim;/**
 * Created by cyril on 16/7/11.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

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


public class LineWithTextLoadingAnim extends View {

    private Paint mPaint;
    private float mWidth = 0f;
    private float mHeight = 0f;
    private int mValue = 0;
    private float mPadding = 5f;

    public LineWithTextLoadingAnim(Context context) {
        this(context, null);
    }

    public LineWithTextLoadingAnim(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineWithTextLoadingAnim(Context context, AttributeSet attrs, int defStyleAttr) {
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

        String text = mValue + "%";
        float textLength = getFontLength(mPaint, text);
        float textHeight = getFontHeight(mPaint, text);

        if (mValue == 0) {
            canvas.drawText(text, mPadding, mHeight / 2 + textHeight / 2, mPaint);
            canvas.drawLine(mPadding + textLength, mHeight / 2, mWidth - mPadding, mHeight / 2, mPaint);
        } else if (mValue >= 100) {
            canvas.drawText(text, mWidth - mPadding - textLength, mHeight / 2 + textHeight / 2, mPaint);
            canvas.drawLine(mPadding, mHeight / 2, mWidth - mPadding - textLength, mHeight / 2, mPaint);
        } else {
            float w = mWidth - 2 * mPadding - textLength;
            canvas.drawLine(mPadding, mHeight / 2, mPadding + w * mValue / 100, mHeight / 2, mPaint);
            canvas.drawLine(mPadding + w * mValue / 100 + textLength, mHeight / 2, mWidth - mPadding, mHeight / 2, mPaint);
            canvas.drawText(text, mPadding + w * mValue / 100, mHeight / 2 + textHeight / 2, mPaint);
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dip2px(10));
        mPaint.setStrokeWidth(dip2px(1));
    }

    public float getFontLength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setValue(int value) {
        this.mValue = value;
        invalidate();
    }
}
