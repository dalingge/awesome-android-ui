package com.dalingge.awesome.activity;

import android.support.animation.SpringAnimation;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.dalingge.awesome.R;

import butterknife.BindView;

public class ConstraintActivity extends BaseActivity {

    @BindView(R.id.box)
    ImageView box;
    @BindView(R.id.stiffness)
    SeekBar stiffnessSeekBar;
    @BindView(R.id.damping)
    SeekBar dampingSeekBar;

    //XY坐标
    private float downX, downY;

    //X/Y方向速度相关的帮助类
    private VelocityTracker velocityTracker;
    @Override
    protected int getLayout() {
        return R.layout.activity_constraint;
    }

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    protected void initView() {
        findViewById(android.R.id.content).setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        velocityTracker = VelocityTracker.obtain();
        findViewById(R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        velocityTracker.addMovement(event);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        box.setTranslationX(event.getX() - downX);
                        box.setTranslationY(event.getY() - downY);
                        velocityTracker.addMovement(event);
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        velocityTracker.computeCurrentVelocity(1000);
                        if (box.getTranslationX() != 0) {
                            SpringAnimation animX = new SpringAnimation(box, SpringAnimation.TRANSLATION_X, 0);
                            animX.getSpring().setStiffness(getStiffnessSeekBar());
                            animX.getSpring().setDampingRatio(getDampingSeekBar());
                            animX.setStartVelocity(velocityTracker.getXVelocity());
                            animX.start();
                        }
                        if (box.getTranslationY() != 0) {
                            SpringAnimation animY = new SpringAnimation(box, SpringAnimation.TRANSLATION_Y, 0);
                            animY.getSpring().setStiffness(getStiffnessSeekBar());
                            animY.getSpring().setDampingRatio(getDampingSeekBar());
                            animY.setStartVelocity(velocityTracker.getYVelocity());
                            animY.start();
                        }
                        velocityTracker.clear();
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * 从SeekBar获取自定义的强度
     *
     * @return 强度float
     */
    private float getStiffnessSeekBar() {
        return Math.max(stiffnessSeekBar.getProgress(), 1f);
    }

    /**
     * 从SeekBar获取自定义的阻尼
     *
     * @return 阻尼float
     */
    private float getDampingSeekBar() {
        return dampingSeekBar.getProgress() / 100f;
    }
}
