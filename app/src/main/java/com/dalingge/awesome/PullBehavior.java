package com.dalingge.awesome;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * FileName: PullBehavior.java
 * description:
 * Author: dingby(445850053@qq.com)
 * Date: 2016/6/6
 */
public class PullBehavior extends CoordinatorLayout.Behavior<AppBarLayout> {

    private final Context mContext;


    public PullBehavior(Context context, AttributeSet attrs) {
        mContext = context;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, AppBarLayout child, View dependency) {

        Log.d("Dependent","Height:"+child.getHeight());
        return true;
    }

}
