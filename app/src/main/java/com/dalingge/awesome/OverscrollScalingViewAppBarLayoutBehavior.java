package com.dalingge.awesome;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * FileName: OverscrollScalingViewAppBarLayoutBehavior.java
 * description:
 * Author: dingby(445850053@qq.com)
 * Date: 2016/6/6
 */
public class OverscrollScalingViewAppBarLayoutBehavior extends AppBarLayout.ScrollingViewBehavior{

    private static final String TAG = "overScrollScale";
    private View mTargetScalingView;
    private int mPivotX;
    private int mPivotY;


    private Scaler mScaleImpl;
//    private static final int[] SCROLLVIEW_STYLEABLE = new int[]{
//            R.attr.targetScalingViewId
//    };

    public OverscrollScalingViewAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleImpl = mScaleImpl == null ? new ViewScaler() : mScaleImpl;
    }

    public OverscrollScalingViewAppBarLayoutBehavior() {
        super();
        mScaleImpl = mScaleImpl == null ? new ViewScaler() : mScaleImpl;
    }

    private int mTotalDyUnconsumed = 0;
    private int mTotalTargetDyUnconsumed;

    public void setScaler(Scaler scaler) {
        this.mScaleImpl = scaler;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View abl, int layoutDirection) {
        boolean superLayout = super.onLayoutChild(parent, abl, layoutDirection);
        if (mTargetScalingView == null) {
            mTargetScalingView = parent.findViewWithTag(TAG);
            if (mTargetScalingView != null) {
                mScaleImpl.obtainInitialValues();
            }
        }
        return superLayout;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (mTargetScalingView == null || dyConsumed != 0) {
            mScaleImpl.cancelAnimations();
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
            return;
        }
        mTargetScalingView.animate().setDuration(500)
                .setStartDelay(300)
                .translationYBy(mTargetScalingView.getTop()).start();
        if (dyUnconsumed < 0 && getTopAndBottomOffset() >= mScaleImpl.getInitialParentBottom()) {
            int absDyUnconsumed = Math.abs(dyUnconsumed);
            mTotalDyUnconsumed += absDyUnconsumed;
            mTotalDyUnconsumed = Math.min(mTotalDyUnconsumed, mTotalTargetDyUnconsumed);
            mScaleImpl.updateViewScale();
        } else {
            mTotalDyUnconsumed = 0;
            mScaleImpl.setShouldRestore(false);
            if (dyConsumed != 0) {
                mScaleImpl.cancelAnimations();
            }
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == View.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {

        mScaleImpl.retractScale();
        super.onStopNestedScroll(coordinatorLayout, child, target);

    }

    private class ViewScaler extends ParentScaler {
        private boolean mRetracting = false;

        private ViewPropertyAnimatorListener mShouldRestoreListener = new ViewPropertyAnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                mShouldRestore = false;
            }
        };
        private ViewPropertyAnimatorCompat mScaleAnimator;

        @Override
        public void updateViewScale() {
            float scale = getScale();
            setScale(scale);
            mCurrentScale = scale;
            mShouldRestore = true;
        }

        @Override
        public void retractScale() {
            super.retractScale();
            if (mShouldRestore) {
                mRetracting = true;
                mScaleAnimator = ViewCompat.animate(mTargetScalingView).setListener(mShouldRestoreListener).scaleY(1f).scaleX(1f);
                mScaleAnimator.start();
                mTotalDyUnconsumed = 0;
            }
        }

        @Override
        public void setScale(float scale) {
            super.setScale(scale);
            ViewCompat.setScaleX(mTargetScalingView, scale);
            ViewCompat.setScaleY(mTargetScalingView, scale);
        }

        @Override
        public void obtainInitialValues() {
            super.obtainInitialValues();
            mInitialScale = Math.max(ViewCompat.getScaleY(mTargetScalingView), ViewCompat.getScaleX(mTargetScalingView));
        }

        @Override
        public boolean isRetracting() {
            return mRetracting;
        }

        @Override
        public void cancelAnimations() {
            super.cancelAnimations();
            mShouldRestore = false;
            ViewCompat.animate(mTargetScalingView).cancel();
            ViewCompat.setScaleY(mTargetScalingView, 1f);
            ViewCompat.setScaleX(mTargetScalingView, 1f);
        }
    }

    private class ParentScaler implements Scaler {
        float mCurrentScale;
        float mInitialScale;
        boolean mShouldRestore = false;
        private int mInitialParentBottom;
        private ViewGroup mParent;
        IntEvaluator mIntEvaluator = new IntEvaluator();
        private int mTargetParentBottom;
        private ValueAnimator mBottomAnimator;

        @Override
        public void setShouldRestore(boolean restore) {
            mShouldRestore = restore;
        }


        public float getCurrentScale() {
            return mCurrentScale;
        }

        @Override
        public void cancelAnimations() {
            mShouldRestore = false;
            if (mBottomAnimator != null && mBottomAnimator.isRunning())
                mBottomAnimator.cancel();
        }

        @Override
        public int getInitialParentBottom() {
            return mInitialParentBottom;
        }

        @Override
        public boolean isShouldRestore() {
            return mShouldRestore;
        }

        @Override
        public boolean isRetracting() {
            return false;
        }


        public float getScale() {
            float ratio = (float) mTotalDyUnconsumed / mTotalTargetDyUnconsumed;
            float scale = 1f + ratio;
            return scale;
        }

        @Override
        public void updateViewScale() {

        }

        @Override
        public void retractScale() {
            final View parent = mParent;
            if (parent.getBottom() > mInitialParentBottom) {
                mBottomAnimator = ValueAnimator.ofInt(parent.getBottom(), mInitialParentBottom);
                mBottomAnimator.setEvaluator(mIntEvaluator);
                mBottomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int bottom = (int) animation.getAnimatedValue();
                        parent.setBottom(bottom);
                    }
                });
                mBottomAnimator.start();
            }
        }

        @Override
        public void setScale(float scale) {
            final View parent = mParent;
            Integer evaluate = mIntEvaluator.evaluate(scale, mInitialParentBottom, mTargetParentBottom);
            parent.setBottom(evaluate);
            parent.postInvalidate();

        }

        @Override
        public void obtainInitialValues() {
            mParent = (ViewGroup) mTargetScalingView.getParent();
            mInitialParentBottom = mParent.getHeight();
            mTargetParentBottom = (int) (mInitialParentBottom * 1.1);
            mPivotX = mTargetScalingView.getWidth() / 2;
            mPivotY = mTargetScalingView.getHeight() / 2;
            ViewCompat.setPivotX(mTargetScalingView, mPivotX);
            ViewCompat.setPivotY(mTargetScalingView, mPivotY);
            mTotalTargetDyUnconsumed = 50;
        }
    }
}
