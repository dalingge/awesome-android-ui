package com.dalingge.awesome.activity;

import android.support.v4.app.Fragment;

import com.dalingge.awesome.R;
import com.dalingge.awesome.fragment.FragmentStack;
import com.dalingge.awesome.fragment.MainFragment;

public class FragmentActivity extends BaseActivity {

    private FragmentStack fragmentStack;

    @Override
    protected boolean isBack() {
        return true;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initView() {


        fragmentStack = new FragmentStack(this, getSupportFragmentManager(), R.id.fragmentContainer);
        fragmentStack.replace(new MainFragment());
    }

    public void push(Fragment fragment) {
        fragmentStack.push(fragment);
    }

    @Override
    public void onBackPressed() {
        if (!fragmentStack.pop())
            super.onBackPressed();
    }

    public void replace(Fragment fragment) {
        fragmentStack.replace(fragment);
    }

}
