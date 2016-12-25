package com.dalingge.awesome.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import com.dalingge.awesome.R;

public class DiagonalLayout extends FrameLayout {

    private static final float EPSILON = 0.5f;

    int height = 0;

    int width = 0;

    Path clipPath, outlinePath;

    Paint paint;

    Integer defaultMargin_forPosition;

    private PorterDuffXfermode pdMode;

    public final static int LEFT = 1;
    public final static int RIGHT = 2;
    public final static int BOTTOM = 4;
    public final static int TOP = 8;
    private float angle = 15;
    private boolean handleMargins;
    private boolean isRight = false;
    private boolean isTop = false;
    private float elevation;

    public DiagonalLayout(Context context) {
        super(context);
        init(context, null);
    }

    public DiagonalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DiagonalLayout, 0, 0);
        angle = styledAttributes.getInt(R.styleable.DiagonalLayout_diagonal_angle, 0);

        int gravity = styledAttributes.getInt(R.styleable.DiagonalLayout_diagonal_gravity, 0);
        isRight = (gravity & RIGHT) == RIGHT;
        isTop = (gravity & TOP) == TOP;
        handleMargins = styledAttributes.getBoolean(R.styleable.DiagonalLayout_diagonal_handleMargins, false);

        styledAttributes.recycle();
        setElevation(ViewCompat.getElevation(this));

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        pdMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    private void calculateLayout() {

        height = getMeasuredHeight();
        width = getMeasuredWidth();
        if (width > 0 && height > 0) {

            final float perpendicularHeight = (float) (width * Math.tan(Math.toRadians(getAngle())));

            clipPath = createClipPath(perpendicularHeight);
            outlinePath = createOutlinePath(perpendicularHeight);

            handleMargins(perpendicularHeight);

            ViewCompat.setElevation(this, getElevation());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setOutlineProvider(getOutlineProvider());
            }
        }
    }

    private Path createClipPath(float perpendicularHeight) {
        Path path = new Path();
        if (isBottom()) {
            if (isGravityLeft()) {
                path.moveTo(width - getPaddingRight() + EPSILON, height - perpendicularHeight - getPaddingBottom() + EPSILON);
                path.lineTo(width - getPaddingRight() + EPSILON, height - getPaddingBottom() + EPSILON);
                path.lineTo(getPaddingLeft() - EPSILON, height - getPaddingBottom() + EPSILON);
                path.close();
            } else {
                path.moveTo(width - getPaddingRight() + EPSILON, height - getPaddingBottom() + EPSILON);
                path.lineTo(getPaddingLeft() - EPSILON, height - getPaddingBottom() + EPSILON);
                path.lineTo(getPaddingLeft() - EPSILON, height - perpendicularHeight - getPaddingBottom() + EPSILON);
                path.close();
            }
        } else {
            if (isGravityLeft()) {
                path.moveTo(width - getPaddingRight() + EPSILON, getPaddingTop() + perpendicularHeight - EPSILON);
                path.lineTo(getPaddingLeft() - EPSILON, getPaddingTop() - EPSILON);
                path.lineTo(width - getPaddingRight() + EPSILON, getPaddingTop() - EPSILON);
                path.close();
            } else {
                path.moveTo(width - getPaddingRight() + EPSILON, getPaddingTop() - EPSILON);
                path.lineTo(getPaddingLeft() - EPSILON, getPaddingTop() + perpendicularHeight - EPSILON);
                path.lineTo(getPaddingLeft() - EPSILON, getPaddingTop() - EPSILON);
                path.close();
            }
        }
        return path;
    }

    private Path createOutlinePath(float perpendicularHeight) {
        Path path = new Path();
        if (isBottom()) {
            if (isGravityLeft()) {
                path.moveTo(getPaddingLeft(), getPaddingRight());
                path.lineTo(width - getPaddingRight(), getPaddingTop());
                path.lineTo(width - getPaddingRight(), height - perpendicularHeight - getPaddingBottom());
                path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                path.close();
            } else {
                path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                path.lineTo(getPaddingLeft(), height - perpendicularHeight - getPaddingBottom());
                path.lineTo(getPaddingLeft(), getPaddingTop());
                path.lineTo(width - getPaddingRight(), getPaddingTop());
                path.close();
            }
        } else {
            if (isGravityLeft()) {
                path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                path.lineTo(width - getPaddingRight(), getPaddingTop() + perpendicularHeight);
                path.lineTo(getPaddingLeft(), getPaddingTop());
                path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                path.close();
            } else {
                path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                path.lineTo(width - getPaddingRight(), getPaddingTop());
                path.lineTo(getPaddingLeft(), getPaddingTop() + perpendicularHeight);
                path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                path.close();
            }
        }
        return path;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewOutlineProvider getOutlineProvider() {
        return new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setConvexPath(outlinePath);
            }
        };
    }

    private void handleMargins(float perpendicularHeight) {
        if (isHandleMargins()) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams instanceof MarginLayoutParams) {
                MarginLayoutParams lp = (MarginLayoutParams) layoutParams;

                if (isBottom()) {
                    if (defaultMargin_forPosition == null) {
                        defaultMargin_forPosition = lp.bottomMargin;
                    } else {
                        defaultMargin_forPosition = 0;
                    }
                    lp.bottomMargin = (int) (defaultMargin_forPosition - perpendicularHeight);
                } else {
                    if (defaultMargin_forPosition == null) {
                        defaultMargin_forPosition = lp.topMargin;
                    } else {
                        defaultMargin_forPosition = 0;
                    }
                    lp.topMargin = (int) (defaultMargin_forPosition - perpendicularHeight);
                }
                setLayoutParams(lp);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateLayout();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);

        paint.setXfermode(pdMode);
        canvas.drawPath(clipPath, paint);

        canvas.restoreToCount(saveCount);
        paint.setXfermode(null);
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public boolean isGravityLeft() {
        return !isRight;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean isHandleMargins() {
        return handleMargins;
    }

    public void setHandleMargins(boolean handleMargins) {
        this.handleMargins = handleMargins;
    }

    public boolean isBottom(){
        return !isTop;
    }
}
