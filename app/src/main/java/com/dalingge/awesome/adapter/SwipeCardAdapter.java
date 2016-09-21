package com.dalingge.awesome.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.dalingge.awesome.R;

import java.util.ArrayList;
import java.util.Collection;

/**
 * FileName:SwipeCardAdapter.java
 * Description:
 * Author:dingboyang
 * Email:445850053@qq.com
 * Date:16/9/21
 */

public class SwipeCardAdapter extends BaseAdapter {


    ArrayList<String> objs;
    private Context context;
    public SwipeCardAdapter(Context context) {
       this.context=context;
        objs = new ArrayList<>();
    }

    public void addAll(Collection<String> collection) {
        if (isEmpty()) {
            objs.addAll(collection);
            notifyDataSetChanged();
        } else {
            objs.addAll(collection);
        }
    }

    public void clear() {
        objs.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return objs.isEmpty();
    }

    public void remove(int index) {
        if (index > -1 && index < objs.size()) {
            objs.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return objs.size();
    }

    @Override
    public String getItem(int position) {
        if(objs==null ||objs.size()==0) return null;
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String s = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_swipe_card, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.collectView = (CheckedTextView) convertView.findViewById(R.id.favorite);
            holder.collectView.setOnClickListener(view ->Log.e("tag", "+ 关注"));
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(String.format("%s", s));


        return convertView;
    }

    private static class ViewHolder {
        TextView nameView;
        CheckedTextView collectView;
    }
}
