package com.dalingge.awesome.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by dingboyang on 2016/11/29.
 */

public class MaterialGuideView extends RelativeLayout {


    private Bitmap bitmap;// 前景
    private Canvas canvas;//绘制蒙版层的画布
    private Paint mPaint;// 绘制蒙版层画笔
    private int width, height;// 屏幕宽高
    private int radius;//圆半径
    private int vCenterY, vCenterX; //圆心坐标
    private OnDismissListener onDismissListener;//关闭监听
    private Handler handler;
    /*******************
     * 可配置属性
     *****************************/
    private View targetView;//高亮目标view
    private boolean touchOutsideCancel ;//外部点击是否可关闭
    private int maskColor ;// 蒙版层颜色
    private int highLisghtPadding = 0;// 高亮控件padding
    private boolean isFadeAnimationEnabled;//过度动画

    public MaterialGuideView(Context activity) {
        super(activity);
        init(activity);
    }

    public MaterialGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MaterialGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化对象
     */
    private void init(Context context) {
        setWillNotDraw(false);
        setVisibility(INVISIBLE);

        handler = new Handler();
        maskColor = 0x70000000;
        isFadeAnimationEnabled = true;
        touchOutsideCancel = true;
        highLisghtPadding = getStatusBarHeight(context);
        // 实例化画笔并开启其抗锯齿和抗抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        // 设置画笔透明度为0是关键！
        mPaint.setARGB(0, 255, 0, 0);
        // 设置混合模式为DST_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //设置画笔类型
        mPaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // if (!isReady) return;

        if (bitmap == null || canvas == null) {
            if (bitmap != null) bitmap.recycle();
            // 生成前景图Bitmap
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // 将其注入画布
            this.canvas = new Canvas(bitmap);
        }

        // 清除画布，绘制指定的颜色
        this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        // 绘制前景画布颜色
        this.canvas.drawColor(maskColor);

        if (targetView == null)
            return;
        // 绘制前景
        canvas.drawBitmap(bitmap, 0, 0, null);
        //高亮控件宽高
        int vWidth = targetView.getWidth();
        int vHeight = targetView.getHeight();
        int[] location = new int[2];
        targetView.getLocationInWindow(location);
        Rect rtLocation = new Rect(
                location[0],
                location[1],
                location[0] + vWidth,
                location[1] + vHeight);

        radius = vWidth > vHeight ? vWidth / 2 + highLisghtPadding / 2 : vHeight / 2 + highLisghtPadding / 2;
        if (radius < 50) {
            radius = 100;
        }
        vCenterX = rtLocation.left + vWidth / 2;
        vCenterY = rtLocation.top + vHeight / 2;
        this.canvas.drawCircle(vCenterX, vCenterY, radius, mPaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP://

                //获取屏幕上点击的坐标
                int lastX = (int) event.getRawX();
                int lastY = (int) event.getRawY();

                //点击位置x坐标与圆心的x坐标的距离
                int distanceX = Math.abs(vCenterX - lastX);
                //点击位置y坐标与圆心的y坐标的距离
                int distanceY = Math.abs(vCenterY - lastY);
                //点击位置与圆心的直线距离
                int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

                //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
                if (distanceZ > radius) {
                    return false;
                }

                if (touchOutsideCancel) {
                    dismiss();
                    return true;
                }
                break;
        }
        return true;
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    private void setMaskColor(int maskColor) {
        this.maskColor = maskColor;
    }

    private void setTarget(View target) {
        this.targetView = target;
    }

    private void setTouchOutsideDismiss(boolean cancel) {
        this.touchOutsideCancel = cancel;
    }

    private void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }

    /********************
     * builder模式设置属性
     ******************************/

    public static class Builder {

        private MaterialGuideView materialIntroView;

        private Activity activity;


        public Builder(Activity activity) {
            this.activity = activity;
            materialIntroView = new MaterialGuideView(activity);
        }

        public MaterialGuideView build() {


            return materialIntroView;
        }

        /**
         * 遮罩颜色
         * @param maskColor
         * @return
         */
        public Builder setMaskColor(int maskColor) {
            materialIntroView.setMaskColor(maskColor);
            return this;
        }


        /**
         * 设置需要高亮的View和提示的图片
         *
         * @param targetView
         */
        public Builder setTarget(View targetView) {
            materialIntroView.setTarget(targetView);
            return this;
        }

        /**
         * 设置外部是否关闭，默认关闭
         *
         * @param cancel
         */
        public Builder setTouchOutsideDismiss(boolean cancel) {
            materialIntroView.setTouchOutsideDismiss(cancel);
            return this;
        }

        /**
         * 设置关闭监听
         *
         * @param listener
         */
        public Builder setOnDismissListener(OnDismissListener listener) {
            materialIntroView.setOnDismissListener(listener);
            return this;
        }

        public MaterialGuideView show() {
            build().show(activity);
            return materialIntroView;
        }
    }

    /**
     * 显示
     */
    public void show(Activity activity) {

        ((ViewGroup) activity.getWindow().getDecorView()).addView(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFadeAnimationEnabled)
                    animateFadeIn(MaterialGuideView.this, 700, new AnimationListener.OnAnimationStartListener() {
                        @Override
                        public void onAnimationStart() {
                            setVisibility(VISIBLE);
                        }
                    });
                else
                    setVisibility(VISIBLE);
            }
        }, 0);
    }

    /*
    * 关闭
    */
    private void dismiss() {
        animateFadeOut(this, 700, new AnimationListener.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                setVisibility(GONE);
                removeMaterialView();

                //返回监听
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }

            }
        });
    }

    private void removeMaterialView() {
        if (getParent() != null)
            ((ViewGroup) getParent()).removeView(this);
    }


    public void animateFadeIn(View view, long duration, final AnimationListener.OnAnimationStartListener onAnimationStartListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (onAnimationStartListener != null)
                    onAnimationStartListener.onAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    public void animateFadeOut(View view, long duration, final AnimationListener.OnAnimationEndListener onAnimationEndListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (onAnimationEndListener != null)
                    onAnimationEndListener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    public void performAnimation(View view) {

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.6f);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setDuration(1000);

        ValueAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.6f);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setDuration(1000);

        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }


    interface AnimationListener {

        interface OnAnimationStartListener {
            void onAnimationStart();
        }

        interface OnAnimationEndListener {
            void onAnimationEnd();
        }

    }

}
