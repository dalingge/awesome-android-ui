package com.dalingge.awesome.activity;

import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalingge.awesome.R;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private Button mSignUpBtn;
    private ImageView mLeftLogoImg;
    private ImageView mRightLogoImg;
    private TextView mDescTitleTextView;
    private LinearLayout mLettersLayout;
    private LinearLayout mSignInLayout;
    private ArrayList<SpringAnimation> mLetterAnims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status ui.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        // get the screen height.
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        // letters about 'Converse'
        mLettersLayout = (LinearLayout) findViewById(R.id.letter_layout);
        mLetterAnims = new ArrayList<>();
        for (int i = 0; i < mLettersLayout.getChildCount(); i++) {
            View letterView = mLettersLayout.getChildAt(i);
            letterView.setTranslationY(screenHeight);
            SpringAnimation letterAnimY = new SpringAnimation(letterView, SpringAnimation.TRANSLATION_Y, 0);
            letterAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
            letterAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
            mLetterAnims.add(letterAnimY);
        }

//        // text about 'Native messaging'
//        mDescTitleTextView = (TextView) findViewById(R.id.desc_title_textview);
//        mDescTitleTextView.setTranslationY(500f);
//        mDescTitleTextView.setAlpha(0f);
//        final SpringAnimation descTitleAnimY = new SpringAnimation(mDescTitleTextView, DynamicAnimation.TRANSLATION_Y, 0);
//        descTitleAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//        descTitleAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//
//        final ValueAnimator descTitleAlphaAnim = ObjectAnimator.ofFloat(0f, 1f);
//        descTitleAlphaAnim.setDuration(300);
//        descTitleAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mDescTitleTextView.setAlpha((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//
//        // the button of sign up
//        mSignUpBtn = (Button) findViewById(R.id.sign_up_btn);
//        mSignUpBtn.setTranslationY(500f);
//        final SpringAnimation signUpBtnAnimY = new SpringAnimation(mSignUpBtn, SpringAnimation.TRANSLATION_Y, 0);
//        signUpBtnAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//        signUpBtnAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        // the bottom text about 'Have an account? sign in'
//        mSignInLayout = (LinearLayout) findViewById(R.id.signin_layout);
//        mSignInLayout.setTranslationY(500f);
//        final SpringAnimation signInLayoutAnimY = new SpringAnimation(mSignInLayout, SpringAnimation.TRANSLATION_Y, 0);
//        signInLayoutAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//        signInLayoutAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        // top logo by left
//        mLeftLogoImg = (ImageView) findViewById(R.id.left_logo_imageview);
//        mLeftLogoImg.setTranslationY(400f);
//        mLeftLogoImg.setAlpha(0f);
//        final SpringAnimation leftLogoAnimY = new SpringAnimation(mLeftLogoImg, SpringAnimation.TRANSLATION_Y, 0);
//        leftLogoAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//        leftLogoAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        leftLogoAnimY.setStartVelocity(-2000);
//        // top logo by right
//        mRightLogoImg = (ImageView) findViewById(R.id.right_logo_imageview);
//        mRightLogoImg.setTranslationY(400f);
//        mRightLogoImg.setAlpha(0f);
//        final SpringAnimation rightLogoAnimY = new SpringAnimation(mRightLogoImg, SpringAnimation.TRANSLATION_Y, 0);
//        rightLogoAnimY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//        rightLogoAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        rightLogoAnimY.setStartVelocity(-2000);
//
//        final ValueAnimator logoAlphaAnim = ObjectAnimator.ofFloat(0f, 1f);
//        logoAlphaAnim.setDuration(600);
//        logoAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mLeftLogoImg.setAlpha((Float) valueAnimator.getAnimatedValue());
//                mRightLogoImg.setAlpha((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//        mRightLogoImg.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                leftLogoAnimY.start();
//                mRightLogoImg.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        rightLogoAnimY.start();
//                    }
//                }, 150);
//                mDescTitleTextView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        descTitleAlphaAnim.setStartDelay(100);
//                        descTitleAlphaAnim.start();
//                        descTitleAnimY.start();
//                        signUpBtnAnimY.start();
//                        signInLayoutAnimY.start();
//                    }
//                }, 300);
//                for (final SpringAnimation letterAnim : mLetterAnims) {
//                    mLettersLayout.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            letterAnim.start();
//                        }
//                    }, 12 * mLetterAnims.indexOf(letterAnim));
//                }
//                logoAlphaAnim.start();
//            }
//        }, 1000);
    }
}
