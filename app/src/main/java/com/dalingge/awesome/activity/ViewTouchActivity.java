package com.dalingge.awesome.activity;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.dalingge.awesome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;

public class ViewTouchActivity extends BaseActivity {

    private static final String TAG = ViewTouchActivity.class.getSimpleName();

    @BindView(R.id.button3)
    Button button3;

    public Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    }){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

        @Override
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public String getMessageName(Message message) {
            return super.getMessageName(message);
        }
    };
    @Override
    protected int getLayout() {
        return R.layout.activity_view_touch;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
         new Thread(){
             public void run(){
                 try {
                     Thread.sleep(1000);
                     button3.setText("12dasd1");
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

             }
         }.start();

        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "onTouch ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "onTouch ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "onTouch ACTION_UP");
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

    }

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEventActivity ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEventActivity ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEventActivity ACTION_UP");
                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ActivityTouch ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ActivityTouch ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ActivityTouch ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
