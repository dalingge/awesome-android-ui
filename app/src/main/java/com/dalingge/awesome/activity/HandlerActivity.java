package com.dalingge.awesome.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;

import com.dalingge.awesome.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.stop)
    Button stop;
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("tag", "main handler");
            threadHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };
    private Handler threadHandler;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
