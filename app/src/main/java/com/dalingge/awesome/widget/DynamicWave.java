package com.dalingge.awesome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.dalingge.awesome.utils.DensityUtils;

import java.lang.ref.WeakReference;

/**
 * Created by dingboyang on 2016/12/12.
 */

public class DynamicWave extends View{

    private static final int WAVE_PAINT_COLOR = 0xFFFFFFFF;
    // y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 20;
    private static final int OFFSET_Y = 0;

    private static final int TRANSLATE_X_SPEED_ONE = 5;

    private static final int TRANSLATE_X_SPEED_TWO = 2;
    private float mCycleFactorW;

    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private float[] mResetTwoYPositions;
    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset;
    private int mXTwoOffset;

    private Paint mWavePaint;
    private DrawFilter mDrawFilter;

    /** 正在执行波浪动画 */
    private boolean isWaving = false;

    public DynamicWave(Context context, AttributeSet attrs) {
        super(context, attrs);

        mXOffsetSpeedOne = DensityUtils.getDensityValue(TRANSLATE_X_SPEED_ONE,context);
        mXOffsetSpeedTwo = DensityUtils.getDensityValue(TRANSLATE_X_SPEED_TWO,context);

        mWavePaint = new Paint();

        mWavePaint.setAntiAlias(true);

        mWavePaint.setStyle(Paint.Style.FILL);

        mWavePaint.setColor(WAVE_PAINT_COLOR);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mHandler = new MyHandler(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(mDrawFilter);
        resetPositonY();
        for (int i = 0; i < mTotalWidth; i++) {

            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - 15, i,
                    mTotalHeight,
                    mWavePaint);

            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 15, i,
                    mTotalHeight,
                    mWavePaint);

            //加下面两条线是防止透明
            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 15, i,
                    mTotalHeight,
                    mWavePaint);
//            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 15, i,
//                    mTotalHeight,
//                    mWavePaint);
//            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 15, i,
//                    mTotalHeight,
//                    mWavePaint);
//            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 15, i,
//                    mTotalHeight,
//                    mWavePaint);
        }

        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;

        if (mXOneOffset >= mTotalWidth) {
            mXOneOffset = 0;
        }
        if (mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0;
        }

        //postInvalidate();
    }

    private void resetPositonY() {

        int yOneInterval = mYPositions.length - mXOneOffset;

        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
                yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    private MyHandler mHandler = null;

    private static class MyHandler extends Handler {
        private WeakReference<DynamicWave> mWeakRef = null;

        private int refreshPeriod = 100;

        public MyHandler(DynamicWave host) {
            mWeakRef = new WeakReference<DynamicWave>(host);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakRef.get() != null) {
                mWeakRef.get().postInvalidate();
                sendEmptyMessageDelayed(0, refreshPeriod);
            }
        }
    }

    public void animateWave() {
        if (!isWaving) {
          //  mWaveFactor = 0L;
            isWaving = true;
            mHandler.sendEmptyMessage(0);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTotalWidth = w;
        mTotalHeight = h;

        mYPositions = new float[mTotalWidth];

        mResetOneYPositions = new float[mTotalWidth];

        mResetTwoYPositions = new float[mTotalWidth];


        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);


        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }


}
