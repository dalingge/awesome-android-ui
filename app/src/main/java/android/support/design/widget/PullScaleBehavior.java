package android.support.design.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dalingge.awesome.R;

/**
 * FileName: PullScaleBehavior.java
 * description:
 * Author: dingby(445850053@qq.com)
 * Date: 2016/6/3
 */
public class PullScaleBehavior extends AppBarLayout.Behavior {

    private static final String TAG = PullScaleBehavior.class.getCanonicalName();

    private ViewOffsetHelper mTopHelper;
    private View mView;

    Context mContext;
    //private View mView;
    private float touchY;
    private int scrollY = 0;
    private boolean handleStop = false;
    private int eachStep = 0;

    private static final int MAX_SCROLL_HEIGHT = 200;// 最大滑动距离
    private static final float SCROLL_RATIO = 0.4f;// 阻尼系数,越小阻力就越大

    public PullScaleBehavior() {
        super();
    }

    public PullScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchY = ev.getY();
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        super.onTouchEvent(parent, child, ev);
        if (child == null) {
            return super.onTouchEvent(parent, child, ev);
        } else {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    if (child.getScrollY() != 0) {
                        handleStop = true;
                        animation();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG,"Y:"+ev.getY());
//                    float nowY = ev.getY();
//                    int deltaY = (int) (touchY - nowY);
//                    touchY = nowY;
//                    if (isNeedMove()) {
//                        int offset = mView.getScrollY();
//                        if (offset < MAX_SCROLL_HEIGHT && offset > -MAX_SCROLL_HEIGHT) {
//                            child.scrollBy(0, (int) (deltaY * SCROLL_RATIO));
//                            handleStop = false;
//                        }
//                    }

                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(parent, child, ev);
    }

    private boolean isNeedMove() {
        int viewHight = mView.getMeasuredHeight();
        int srollHight =mView. getHeight();
        int offset = viewHight - srollHight;
        int scrollY = mView.getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    private void animation() {
        scrollY = mView.getScrollY();
        eachStep = scrollY / 10;
        resetPositionHandler.sendEmptyMessage(0);
    }

    Handler resetPositionHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (scrollY != 0 && handleStop) {
                scrollY -= eachStep;
                if ((eachStep < 0 && scrollY > 0) ||  (eachStep > 0 && scrollY < 0)) {
                    scrollY = 0;
                }
                mView.scrollTo(0, scrollY);
                this.sendEmptyMessageDelayed(0, 5);
            }
        }
    };

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        if (mTopHelper == null) {
            mView = getParallaxView(abl);
            if (mView != null) {
                mTopHelper = new ViewOffsetHelper(mView);
               //clipChild(parent, mContent);
            }
        }
        return super.onLayoutChild(parent, abl, layoutDirection);
    }


    @Override
    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout header, int newOffset, int minOffset, int maxOffset) {
       // header.setClipChildren(newOffset <= 0);

      //  header.setTranslationY(newOffset);
//        Log.d("setHeaderTopBotto",
//                "Height:"+header.getHeight()
//                +"\ny:"+coordinatorLayout.getY()
//                +"\nPivotY:"+coordinatorLayout.getPivotY()
//                +"\nRotationY:"+coordinatorLayout.getRotationY()
//                +"\nScaleY:"+coordinatorLayout.getScaleY()
//                +"\nScrollY:"+coordinatorLayout.getScrollY()
//                +"\nTranslationY:"+coordinatorLayout.getTranslationY());
        if (getTopAndBottomOffset() > 0 || newOffset >= 0) {//fling start
          //  return overScroll(coordinatorLayout, header, newOffset, getMaxRange());
        }
        int scalled = super.setHeaderTopBottomOffset(coordinatorLayout, header, newOffset, minOffset, maxOffset);
//        if (scalled == 0 && newOffset > 0 && maxOffset == 0) {
//            scalled = overScroll(coordinatorLayout, header, newOffset, getMaxRange());
//        } else {
//            scaleContent(1);
//            header.setClipChildren(true);
//        }
        return scalled;
    }

    @Override
    public int getTopAndBottomOffset() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
        if (stackTrace.getMethodName().equals("dispatchOffsetUpdates"))
            return Math.min(0, super.getTopAndBottomOffset());
        return super.getTopAndBottomOffset();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {

        Log.d("onStartNestedScroll","Height:"+child.getHeight()+" nestedScrollAxes:"+directTargetChild.getTop());
        return true;
    }



    private View getParallaxView(AppBarLayout appBarLayout) {
        return appBarLayout.findViewById(R.id.layout);
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }
    }

}
