package com.dalingge.awesome.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;

import com.dalingge.awesome.R;

import butterknife.BindView;

public class PullPagerActivity extends BaseActivity {

    @BindView(R.id.layout)
    AppBarLayout layout;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.coordrinator_layout)
    CoordinatorLayout coordrinatorLayout;
    private boolean mIsButtonAtTop;

    @Override
    protected int getLayout() {
        return R.layout.activity_pull_pager;
    }

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    protected void initView() {
        mIsButtonAtTop = true;

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int height = coordrinatorLayout.getHeight() / 2 - imageview.getHeight() / 2;
                int scrollY = coordrinatorLayout.getHeight() - imageview.getHeight();
//                ActionBar actionBar = getSupportActionBar();
//                if (actionBar != null) height -= actionBar.getHeight();

                if (!mIsButtonAtTop) {
                    height = -height;
                    scrollY = -scrollY;
                }
                mIsButtonAtTop = !mIsButtonAtTop;
                imageview.animate()
                        .setDuration(500)
                        .setStartDelay(300)
                        .translationYBy(height).start();

                nestedScrollView.animate()
                        .setDuration(500)
                        .setStartDelay(200)
                        .translationYBy(scrollY).start();

            }
        });
    }


}
