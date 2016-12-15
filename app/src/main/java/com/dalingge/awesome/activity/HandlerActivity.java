package com.dalingge.awesome.activity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dalingge.awesome.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dalingge.awesome.R.id.tab;

public class HandlerActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.stop)
    Button stop;
    @BindView(tab)
    TabLayout tabLayout;
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("tag", "main handler");
            threadHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };
    private Handler threadHandler;

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_handler;
    }

    @Override
    protected void initView() {

        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                mainHandler.sendEmptyMessageDelayed(1, 1000);
            }
        };

        HashMap hashMap = new HashMap();
        SimpleArrayMap sam = new SimpleArrayMap();
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        SparseArray sparseArray = new SparseArray();
        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat();
        LongSparseArray longSparseArray = new LongSparseArray();

//        tab.addTab(tab.newTab().setCustomView(R.layout.item_tab));
//        tab.addTab(tab.newTab().setText("主贴"));
//        tab.addTab(tab.newTab().setText("回复"));

        for(int i=0;i<3;i++) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(i)));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tvTitle= (TextView) tab.getCustomView().findViewById(R.id.tab_title);
                View tabindicator= tab.getCustomView().findViewById(R.id.tab_indicator);
                tvTitle.setTextColor(getResources().getColor(R.color.colorAccent));
                tabindicator.setBackgroundResource(R.color.colorAccent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tvTitle= (TextView) tab.getCustomView().findViewById(R.id.tab_title);
                View tabindicator= tab.getCustomView().findViewById(R.id.tab_indicator);
                tvTitle.setTextColor(getResources().getColor(R.color.primary_text));
                tabindicator.setBackgroundResource(android.R.color.transparent);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public View getTabView(int position){
        View view= LayoutInflater.from(this).inflate(R.layout.item_tab,null);
        TextView tvTitle= (TextView) view.findViewById(R.id.tab_title);
        View tabindicator= view.findViewById(R.id.tab_indicator);
        tvTitle.setText("全部");
        if(position==0){
            tvTitle.setTextColor(getResources().getColor(R.color.colorAccent));
            tabindicator.setBackgroundResource(R.color.colorAccent);
        }
        return view;
    }

    @Override
    @OnClick({R.id.start, R.id.stop})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                mainHandler.sendEmptyMessage(1);
                break;
            case R.id.stop:
                mainHandler.removeMessages(1);
                threadHandler.removeMessages(1);
                break;
            default:
                break;
        }
    }

}
