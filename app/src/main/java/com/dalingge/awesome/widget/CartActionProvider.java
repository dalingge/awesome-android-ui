package com.dalingge.awesome.widget;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;

import com.dalingge.awesome.R;

/**
 * FileName:CartActionProvider.java
 * Description:
 * Author:dingboyang
 * Email:445850053@qq.com
 * Date:16/8/21
 */
public class CartActionProvider extends ActionProvider{

    private Context mContext;

    public CartActionProvider(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public View onCreateActionView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view =inflater.inflate(R.layout.cart, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
        return view;
    }
}
