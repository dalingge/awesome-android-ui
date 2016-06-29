package com.dalingge.awesome.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dalingge.awesome.R;

/**
 * FileName: ViewPagerAdapter.java
 * description:
 * Author: dingby(445850053@qq.com)
 * Date: 2016/6/24
 */
public class ViewPagerAdapter extends PagerAdapter {

    private int count;
    private Context mContext;
    public ViewPagerAdapter(Context context, int totalCount) {
        mContext=context;
        count=totalCount;
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.image1 + position);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView, position);
        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}
