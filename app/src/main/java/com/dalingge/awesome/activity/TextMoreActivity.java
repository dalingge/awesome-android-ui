package com.dalingge.awesome.activity;

import android.os.Build;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import butterknife.Bind;
import com.dalingge.awesome.R;
import com.dalingge.awesome.widget.ReadMoreTextView;

public class TextMoreActivity extends BaseActivity {

    @Bind(R.id.text1) ReadMoreTextView text1;
    @Bind(R.id.text2) ReadMoreTextView text2;
    @Bind(R.id.text3) ReadMoreTextView text3;


    @Override
    protected boolean isBack() {
        return true;
    }


    @Override protected int getLayout() {
        return R.layout.activity_text_more;
    }


    @Override protected void initView() {
        int h=appbatlayout.getHeight();
        Log.d("tag","height:"+h);
        text1.setText(getString(R.string.lorem_ipsum));
        text2.setText(getString(R.string.lorem_ipsum2));
        text3.setText(getString(R.string.lorem_ipsum3));

        if (Build.VERSION.SDK_INT >= 21) {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(R.id.text1);
            slide.addTarget(R.id.text2);
            slide.addTarget(R.id.text3);
            getWindow().setEnterTransition(slide);
        }
    }

}
