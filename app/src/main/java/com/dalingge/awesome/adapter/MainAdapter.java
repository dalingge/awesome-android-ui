package com.dalingge.awesome.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dalingge.awesome.R;

/**
 * FileName: MainAdapter.java
 * description:
 * Author: dingby(445850053@qq.com)
 * Date: 2016/6/24
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private String[] strings;
    private OnRecyclerItemClick mOnRecyclerItemClickListener;


    public MainAdapter(String[] strings, OnRecyclerItemClick onRecyclerItemClick) {
        this.strings = strings;
        mOnRecyclerItemClickListener = onRecyclerItemClick;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
            mTextView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            if (mOnRecyclerItemClickListener != null) {
                mOnRecyclerItemClickListener.onItemClick(mTextView);
            }
        }
    }

    @Override public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_main, parent, false);
        return new ViewHolder((TextView) v.findViewById(R.id.text_name));
    }


    @Override public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(strings[position]);
        holder.mTextView.setTag(position);
    }


    @Override public int getItemCount() {
        return strings.length;
    }

    public interface OnRecyclerItemClick {
        void onItemClick(View View);
    }
}
