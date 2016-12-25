package com.dalingge.awesome.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dalingge.awesome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * FileName:BaseActivity.java
 * Description:
 * Author:dingboyang
 * Email:445850053@qq.com
 * Date:16/4/2
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.appbatlayout)
    AppBarLayout appbatlayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Unbinder unbinder;
    /**
     * set layout of this activity
     *
     * @return the id of layout
     */
    protected abstract int getLayout();

    protected abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder= ButterKnife.bind(this);
        initToolBar();
        init(savedInstanceState);
        initView();

    }

    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消请求
        unbinder.unbind();
    }

    private void initToolBar() {

        if (toolbar == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain a toolbar.");
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);//让原始的toolbar的title不显示
            if (isBack()) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        toolbar.setTitle(getIntent().getStringExtra("title"));
        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setElevation(10.6f);
        }

    }

    protected boolean isBack() {
        return false;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

}
