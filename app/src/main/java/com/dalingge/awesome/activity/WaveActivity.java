package com.dalingge.awesome.activity;

import com.dalingge.awesome.R;
import com.dalingge.awesome.widget.WaveView;

import butterknife.BindView;

public class WaveActivity extends BaseActivity {
//    @BindView(R.id.dynamic_wave)
//    DynamicWave dynamicWave;

    @BindView(R.id.wave_view)
    WaveView waveView;

    @Override
    protected int getLayout() {
        return R.layout.activity_wave;
    }

    @Override
    protected void initView() {
        waveView.animateWave();
    }


}
