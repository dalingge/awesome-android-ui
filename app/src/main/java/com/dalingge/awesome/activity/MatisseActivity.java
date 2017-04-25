package com.dalingge.awesome.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dalingge.awesome.R;
import com.dalingge.awesome.utils.GifSizeFilter;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.List;

import rx.Subscriber;

public class MatisseActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_CHOOSE = 23;

    private UriAdapter mAdapter;

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_matisse;
    }

    @Override
    protected void initView() {
        findViewById(R.id.zhihu).setOnClickListener(this);
        findViewById(R.id.dracula).setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new UriAdapter());
    }

    @Override
    public void onClick(View v) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            switch (v.getId()) {
                                case R.id.zhihu:
                                    Matisse.from(MatisseActivity.this)
                                            .choose(MimeType.allOf())
                                            .countable(true)
                                            .maxSelectable(9)
                                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                            .gridExpectedSize(
                                                    getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                            .thumbnailScale(0.85f)
                                            .imageEngine(new GlideEngine())
                                            .forResult(REQUEST_CODE_CHOOSE);
                                    break;
                                case R.id.dracula:
                                    Matisse.from(MatisseActivity.this)
                                            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                                            .theme(R.style.Matisse_Dracula)
                                            .countable(false)
                                            .maxSelectable(9)
                                            .imageEngine(new PicassoEngine())
                                            .forResult(REQUEST_CODE_CHOOSE);
                                    break;
                            }
                            mAdapter.setData(null);
                        } else {
                            Toast.makeText(MatisseActivity.this, R.string.permission_request_denied, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mAdapter.setData(Matisse.obtainResult(data));
        }
    }

    private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

        private List<Uri> mUris;

        void setData(List<Uri> uris) {
            mUris = uris;
            notifyDataSetChanged();
        }

        @Override
        public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UriViewHolder(
                    (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
        }

        @Override
        public void onBindViewHolder(UriViewHolder holder, int position) {
            Uri uri = mUris.get(position);
            holder.mUri.setText(uri.toString());
        }

        @Override
        public int getItemCount() {
            return mUris == null ? 0 : mUris.size();
        }

        static class UriViewHolder extends RecyclerView.ViewHolder {

            private TextView mUri;

            UriViewHolder(TextView uri) {
                super(uri);
                mUri = uri;
            }
        }
    }
}
