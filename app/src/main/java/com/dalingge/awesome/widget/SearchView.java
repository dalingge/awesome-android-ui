package com.dalingge.awesome.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.dalingge.awesome.R;
import com.dalingge.awesome.utils.DensityUtils;

/**
 * Created by dingboyang on 2016/12/24.
 */

public class SearchView extends View {

    private static final int DEFAULT_BACKGROUND_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_TEXT_COLOR = 0xFF8A8A8A;

    public static final int STATE_ANIM_NONE = 0;
    public static final int STATE_ANIM_START = 1;
    public static final int STATE_ANIM_STOP = 2;
    public static final int DEFAULT_ANIM_TIME = 500;
    public static final float DEFAULT_ANIM_STARTF = 0;
    public static final float DEFAULT_ANIM_ENDF = 1;

    private Paint mBackgroundRectPaint;
    private Paint mSearchBitmapPaint;
    private Paint mTitleTextPaint;
    private Bitmap mBitmapSearch;

    private Path mBackgroundRectPath;
    private int MIN_WIDTH = dip2px(80);
    private int MAX_WITH = dip2px(300);
    private int MIN_HEIGHT = dip2px(30);
    private int MAX_HEIGHT = dip2px(240);
    private final int MAX_BACKGROUND_ROUND_RECT_RADIUS = dip2px(30);
    private String mTitleText = "搜索";
    private int mWidth = 0;
    private int mHeight = 0;

    private int mBackgroundRectWidth;
    private int mBackgroundRectHeight;
    private int mBgRoundRectRadius;

    private RectF mBackgroundRectF = new RectF();


    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBackgroundRectPaint = new Paint();
        mBackgroundRectPaint.setColor(DEFAULT_BACKGROUND_COLOR);
        mBackgroundRectPaint.setAntiAlias(true);
        mBackgroundRectPaint.setStrokeWidth(5);

        mSearchBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSearchBitmapPaint.setFilterBitmap(true);
        mSearchBitmapPaint.setDither(true);

        Rect mBound = new Rect();
        mTitleTextPaint = new Paint();
        mTitleTextPaint.setColor(DEFAULT_TEXT_COLOR);
        mTitleTextPaint.setAntiAlias(true);
        mTitleTextPaint.setStyle(Paint.Style.FILL);
        mTitleTextPaint.setTextSize(DensityUtils.sp2px(context, 16));
        mTitleTextPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

        mBitmapSearch = getBitmap(context, R.drawable.ic_search);

        mBackgroundRectPath = new Path();
        mBackgroundRectPath.reset();

        initValues();
    }

    private void initValues() {
        mBackgroundRectWidth = MIN_WIDTH;
        mBackgroundRectHeight = MIN_HEIGHT;
        mBgRoundRectRadius = MAX_BACKGROUND_ROUND_RECT_RADIUS;
    }


    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = onMeasureR(0, widthMeasureSpec);
        int height = onMeasureR(1, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int widthMode = MeasureSpec.getMode(measureSpec);
        int widthSize = MeasureSpec.getSize(measureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            result = widthSize;
        } else {
            result = MIN_WIDTH;
            if (widthMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, widthSize);
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int result;
        int heightMode = MeasureSpec.getMode(measureSpec);
        int heightSize = MeasureSpec.getSize(measureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            result = heightSize;
        } else {
            result = MIN_HEIGHT;
            if (heightMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, heightSize);
            }
        }
        return result;
    }


    /**
     * 计算控件宽高
     *
     * @param attr       属性
     *                   [0宽,1高]
     * @param oldMeasure
     * @author Ruffian
     */
    public int onMeasureR(int attr, int oldMeasure) {

        int newSize = 0;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
                float value;

                if (attr == 0) {

                    // value = mBound.width();
                    value = mTitleTextPaint.measureText(mTitleText);

                    // 控件的宽度 + getPaddingLeft() + getPaddingRight()
                    newSize = (int) (getPaddingLeft() + value + getPaddingRight());

                } else if (attr == 1) {

                    // value = mBound.height();
                    Paint.FontMetrics fontMetrics = mTitleTextPaint.getFontMetrics();
                    value = Math.abs((fontMetrics.bottom - fontMetrics.top));

                    // 控件的高度 + getPaddingTop() + getPaddingBottom()
                    newSize = (int) (getPaddingTop() + value + getPaddingBottom());

                }

                break;
        }

        return newSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int rectLeft = mWidth - mBackgroundRectWidth;
        int rectRight = mWidth;
        mBackgroundRectF.set(rectLeft, 0, rectRight, mBackgroundRectHeight);

        canvas.drawRoundRect(mBackgroundRectF, mBgRoundRectRadius, mBgRoundRectRadius, mBackgroundRectPaint);
        canvas.drawBitmap(mBitmapSearch, rectLeft, (mHeight - mBitmapSearch.getHeight()) / 2, mSearchBitmapPaint);
        Paint.FontMetricsInt fm = mTitleTextPaint.getFontMetricsInt();
        float startY = mHeight / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText(mTitleText, rectLeft + mBitmapSearch.getWidth(), startY, mTitleTextPaint);
    }

    private boolean isAnim = true;

    public void startAnim() {
        if (!isAnim) return;
        AnimatorSet backgroundAnimSet = new AnimatorSet();
        ValueAnimator widthAnim = ValueAnimator.ofInt(MIN_WIDTH, MAX_WITH);
        widthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBackgroundRectWidth = (int) animation.getAnimatedValue();
                mTitleText = "搜索简书上的内容和朋友";
                invalidate();
                isAnim = false;
            }
        });
        ValueAnimator alphaAnim = ValueAnimator.ofFloat(0f,1f);
        backgroundAnimSet.playTogether(widthAnim,alphaAnim);
        backgroundAnimSet.setDuration(500);
        backgroundAnimSet.start();
    }

    public void resetAnim() {
        if (isAnim) return;
        AnimatorSet backgroundAnimSet = new AnimatorSet();
        ValueAnimator widthAnim = ValueAnimator.ofInt(MAX_WITH, MIN_WIDTH);
        widthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBackgroundRectWidth = (int) animation.getAnimatedValue();
                invalidate();
                isAnim = true;
            }
        });
        backgroundAnimSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mTitleText = "搜索";
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        backgroundAnimSet.playTogether(widthAnim);
        backgroundAnimSet.setDuration(500);
        backgroundAnimSet.start();
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
