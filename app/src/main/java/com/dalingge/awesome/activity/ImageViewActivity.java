package com.dalingge.awesome.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dalingge.awesome.R;
import com.dalingge.awesome.adapter.AppInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImageViewActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_image_view;
    }

    @Override
    protected void initView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(new AppInfoAdapter(this, getAppInfo()));
        recyclerview.addItemDecoration(new DividerItemDecoration(this, mLayoutManager.getOrientation()));

    }

    private List<AppInfo> getAppInfo() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(0);
        List<AppInfo> appInfos = new ArrayList<>();
        for (PackageInfo packageInfo : list) {
            AppInfo appInfo = new AppInfo();
            appInfo.label = packageInfo.applicationInfo.loadLabel(pm);
            appInfo.icon = packageInfo.applicationInfo.loadIcon(pm);
            //应用包名
            appInfo.packageName = packageInfo.applicationInfo.packageName;

            appInfos.add(appInfo);
        }
        return appInfos;
    }

    public class AppInfo {
        public CharSequence label;
        public Drawable icon;
        public String packageName;
    }

}
