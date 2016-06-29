package com.dalingge.awesome.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.dalingge.awesome.R;
import com.dalingge.awesome.adapter.ViewPagerAdapter;

public class ViewPagerActivity extends BaseActivity {

    @Bind(R.id.view_pager) ViewPager viewPager;
    @Bind(R.id.pager_layout) RelativeLayout pagerLayout;


    @Bind(R.id.viewPager2) ViewPager viewPager2;
    @Bind(R.id.pagerLayout2) LinearLayout pagerLayout2;
    private int pagerWidth = 0;
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
        viewPager.setOffscreenPageLimit(TOTAL_COUNT);
        viewPager.setPageMargin(20);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        pagerLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return viewPager.dispatchTouchEvent(event);
            }
        });

        pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
        viewPager2.measure(0, 0);
        ViewGroup.LayoutParams lp = viewPager2.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(pagerWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        viewPager2.setLayoutParams(lp);//设置页面宽度为屏幕的3/5
        viewPager2.setOffscreenPageLimit(4);  //设置ViewPager至多缓存4个Pager页面，防止多次加载
        viewPager2.setPageMargin(20);  //设置Pager之间的间距
        viewPager2.setAdapter(new ViewPagerAdapter(this, TOTAL_COUNT));

        viewPager2.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (pagerLayout2 != null) {
                    pagerLayout2.invalidate();  //更新超出区域页面，否则会出现页面缓存，导致页面效果不佳
                }
            }
        });

    }


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public class MyOnPageChangeListener2 implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (pagerLayout2 != null) {
                pagerLayout2.invalidate();
            }
        }


        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (pagerLayout != null) {
                pagerLayout.invalidate();
            }
        }


        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }

}
