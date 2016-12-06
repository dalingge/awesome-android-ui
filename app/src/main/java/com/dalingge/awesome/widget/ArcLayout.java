package com.dalingge.awesome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.dalingge.awesome.R;
import com.dalingge.awesome.utils.DensityUtils;


/**
 * Created by dingboyang on 2016/12/5.
 */

public class ArcLayout extends RelativeLayout {

    public final static int CROP_OUTSIDE = 0;
    public final static int CROP_INSIDE = 1;

    private int height = 0;
    private int width = 0;
    private boolean cropInside = true;
    private float arcHeight;
    private Path mClipPath;
    private Paint mPaint;
    private int mPaintColor = 0xFFFFFFFF;
    private PorterDuffXfermode porterDuffXfermode;

    public ArcLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ArcLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ArcHeader, 0, 0);
        arcHeight = styledAttributes.getDimension(R.styleable.ArcHeader_arc_height, DensityUtils.dip2px(context, 10));

        final int cropDirection = styledAttributes.getInt(R.styleable.ArcHeader_arc_cropDirection, CROP_INSIDE);
        cropInside = (cropDirection & CROP_INSIDE) == CROP_INSIDE;
        mPaintColor = styledAttributes.getColor(R.styleable.ArcHeader_arc_cropColor, mPaintColor);
        styledAttributes.recycle();

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mPaintColor);
        mClipPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateLayout();
        }
    }

    private void calculateLayout() {
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        if (width > 0 && height > 0) {
            mClipPath = createClipPath();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        mPaint.setXfermode(porterDuffXfermode);
        canvas.drawPath(mClipPath, mPaint);
        canvas.restoreToCount(saveCount);
        mPaint.setXfermode(null);
    }

    private Path createClipPath() {
        final Path path = new Path();

        float arcHeight = getArcHeight();

        if (isCropInside()) {
            path.moveTo(0, 0);
            path.lineTo(getPaddingLeft(), getPaddingTop());
            path.lineTo(getPaddingLeft(), height - arcHeight - getPaddingBottom());
            path.cubicTo(getPaddingLeft(), height - arcHeight - getPaddingBottom(),
                    width / 2 - getPaddingRight(), height - getPaddingBottom() + arcHeight,
                    width - getPaddingRight(), height - arcHeight - getPaddingBottom());
            path.lineTo(width - getPaddingRight(), getPaddingTop());
            path.lineTo(getPaddingLeft(), getPaddingTop());
            path.lineTo(0, 0);
            path.lineTo(width, 0);
            path.lineTo(width, height);
            path.lineTo(0, height);
            path.close();
        } else {
            path.moveTo(0, 0);
            path.lineTo(getPaddingLeft(), getPaddingTop());
            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
            path.cubicTo(getPaddingLeft(), height - getPaddingBottom(),
                    width / 2 - getPaddingRight(), height - 2 * arcHeight - getPaddingBottom(),
                    width - getPaddingRight(), height - getPaddingBottom());
            path.lineTo(width - getPaddingRight(), getPaddingTop());
            path.lineTo(getPaddingLeft(), getPaddingTop());
            path.lineTo(0, 0);
            path.lineTo(width, 0);
            path.lineTo(width, height);
            path.lineTo(0, height);
            path.close();
        }
        return path;
    }

    public boolean isCropInside() {
        return cropInside;
    }

    public float getArcHeight() {
        return arcHeight;
    }
}
