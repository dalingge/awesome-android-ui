package com.dalingge.awesome.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.dalingge.awesome.R;
import com.dalingge.awesome.adapter.SwipeCardAdapter;
import com.dalingge.awesome.widget.swipeadapterview.SwipeAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SwipeCardActivity extends BaseActivity implements SwipeAdapterView.onFlingListener,
        SwipeAdapterView.OnItemClickListener{

    @BindView(R.id.swipe_view)
    SwipeAdapterView swipeView;

    private SwipeCardAdapter mSwipeCardAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_swipe_card;
    }

    @Override
    protected void initView() {
        swipeView.setFlingListener(this);
        swipeView.setOnItemClickListener(this);

        mSwipeCardAdapter = new SwipeCardAdapter(this);
        swipeView.setAdapter(mSwipeCardAdapter);

        loadData();
    }

    private void loadData() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                ArrayList<String> list = new ArrayList<>(10);
                for (int i = 0; i < 10; i++) {
                    list.add("第"+i+"页");
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<String> list) {
                super.onPostExecute(list);
                mSwipeCardAdapter.addAll(list);
            }
        }.execute();
    }

    @Override
    protected boolean isBack() {
        return true;
    }


    @Override
    public void onItemClicked(View v, Object dataObject) {
        Log.e("tag", "click 大图");
    }

    @Override
    public void removeFirstObjectInAdapter() {
        mSwipeCardAdapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        Log.e("tag", "swipe left");
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        Log.e("tag", "swipe right");
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            loadData();
        }
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }
}
