package com.dalingge.awesome.activity;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.dalingge.awesome.R;
import com.dalingge.awesome.adapter.ViewPagerAdapter;

import butterknife.Bind;

public class ViewPagerActivity extends BaseActivity {

    @Bind(R.id.view_pager) ViewPager viewPager;
    @Bind(R.id.pager_layout) RelativeLayout pagerLayout;

    private static int TOTAL_COUNT = 6;


    @Override
    protected boolean isBack() {
        return true;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_view_pager;
    }


    @Override
    protected void initView() {

        viewPager.setAdapter(new ViewPagerAdapter(this, TOTAL_COUNT));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(20);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (pagerLayout != null) {
                    pagerLayout.invalidate();
                }
            }
        });

        pagerLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }


}
