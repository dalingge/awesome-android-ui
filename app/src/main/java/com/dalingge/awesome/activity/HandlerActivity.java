package com.dalingge.awesome.activity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dalingge.awesome.R;
import com.dalingge.awesome.utils.ScreenshotUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dalingge.awesome.R.id.tab;

public class HandlerActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.coordrinator_layout)
    CoordinatorLayout coordrinatorLayout;
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
    private BottomSheetDialog bottomSheetDialog;
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

//        HashMap hashMap = new HashMap();
//        SimpleArrayMap sam = new SimpleArrayMap();
//        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
//        SparseArray sparseArray = new SparseArray();
//        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat();
//        LongSparseArray longSparseArray = new LongSparseArray();

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
    @OnClick({R.id.start, R.id.stop,R.id.btn_dialog_bottom_sheet})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                mainHandler.sendEmptyMessage(1);
                break;
            case R.id.stop:
                mainHandler.removeMessages(1);
                threadHandler.removeMessages(1);
                break;
            case R.id.btn_dialog_bottom_sheet:
                ScreenshotUtil.getBitmapByView(v.getContext(),coordrinatorLayout);
               // setMargins(coordrinatorLayout,150,0,150,400);
//                bottomSheetDialog = new BottomSheetDialog(v.getContext());
//                bottomSheetDialog.setContentView(R.layout.dialog_bottom_sheet);
//                View bottomSheet = bottomSheetDialog.findViewById(R.id.bottom_sheet);
//                final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//                    @Override
//                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//                    }
//
//                    @Override
//                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                        setMargins(coordrinatorLayout,10,10,10,bottomSheet.getHeight());
//                    }
//                });
//                bottomSheetDialog.show();

                break;
            default:
                break;
        }
    }


    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
