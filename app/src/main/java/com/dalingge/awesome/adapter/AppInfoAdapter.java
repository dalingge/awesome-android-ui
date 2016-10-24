package com.dalingge.awesome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalingge.awesome.R;
import com.dalingge.awesome.activity.ImageViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingboyang on 2016/10/22.
 */

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    private List<ImageViewActivity.AppInfo> mAppInfo;
    private Context mContext;

    public AppInfoAdapter(Context context, List<ImageViewActivity.AppInfo> appInfo) {
        mContext = context;
        mAppInfo = appInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_app_info, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageViewActivity.AppInfo appInfo= mAppInfo.get(position);
        holder.icon.setImageDrawable(appInfo.icon);
        holder.label.setText(appInfo.label);
        holder.packageName.setText(appInfo.packageName);
    }


    @Override
    public int getItemCount() {
        return mAppInfo.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.label)
        TextView label;
        @BindView(R.id.package_name)
        TextView packageName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
