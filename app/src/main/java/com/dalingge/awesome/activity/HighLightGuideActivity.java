package com.dalingge.awesome.activity;

import android.widget.TextView;
import android.widget.Toast;

import com.dalingge.awesome.R;
import com.dalingge.awesome.widget.MaterialGuideView;

import butterknife.BindView;

public class HighLightGuideActivity extends BaseActivity {


    @BindView(R.id.tv_buy)
    TextView tvBuy;

    @Override
    protected boolean isBack() {
        return true;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_high_light_guide;
    }

    @Override
    protected void initView() {

        new MaterialGuideView.Builder(this)
                .setTarget(tvBuy)
                .setOnDismissListener(()->Toast.makeText(this, "111", Toast.LENGTH_SHORT).show())
                .show();
    }


}
