package com.dalingge.awesome.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dalingge.awesome.R;
import com.dalingge.awesome.activity.onboarder.GradientBackgroundExampleActivity;
import com.dalingge.awesome.activity.onboarder.ImageBackgroundExampleActivity;
import com.dalingge.awesome.activity.onboarder.SolidBackgroundExampleActivity;
import com.dalingge.awesome.activityoptions.activity.MainOptionsActivity;
import com.dalingge.awesome.adapter.MainAdapter;
import com.dalingge.awesome.transitionseverywhere.MainTransitionsActivity;
import com.dalingge.awesome.widget.CartActionProvider;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    private String[] strings = {
            "主线程与子线程的交互",
            "view事件分发机制",
            "imageview学习",
            "有间隔的滑动切换图片",
            "上下滑动跟随到图片浏览",
            "底部导航栏",
            "展示更多文字",
            "Options的动画实现兼容",
            "Transitions的动画实现兼容",
            "引导页"};
    private Class[] classes = {HandlerActivity.class, ViewTouchActivity.class, ImageViewActivity.class, ViewPagerActivity.class, PullPagerActivity.class, BottomBarActivity.class, TextMoreActivity.class,
            MainOptionsActivity.class, MainTransitionsActivity.class,null};

    @BindView(R.id.recycler_view_team)
    RecyclerView recyclerViewTeam;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        recyclerViewTeam.setHasFixedSize(true);
        // 创建线性布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // 为RecyclerView指定布局管理对象
        recyclerViewTeam.setLayoutManager(linearLayoutManager);
        recyclerViewTeam.setAdapter(new MainAdapter(strings, onRecyclerItemClick));
    }

    private MainAdapter.OnRecyclerItemClick onRecyclerItemClick = new MainAdapter.OnRecyclerItemClick() {
        @Override
        public void onItemClick(View view) {
            if (classes[(Integer) view.getTag()]==null){
                new AlertDialog.Builder(view.getContext())
                        .setTitle("引导页")
                        .setItems(new String[]{"GradientBackgroundExampleActivity", "ImageBackgroundExampleActivity", "SolidBackgroundExampleActivity"}
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Class[] classes1 ={GradientBackgroundExampleActivity.class,ImageBackgroundExampleActivity.class,SolidBackgroundExampleActivity.class};
                                Intent intent = new Intent(
                                        MainActivity.this,classes1[i]);
                                startActivity(intent);
                            }
                        }

                ).show();
            }else {
                Intent intent = new Intent(
                        MainActivity.this, classes[(Integer) view.getTag()]);
                intent.putExtra("title", strings[(Integer) view.getTag()]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivityLollipop(view, intent);
                    startActivity(intent);
                } else {
                    startActivityGingerBread(view, intent);
                }
            }
        }
    };


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startActivityLollipop(View view, Intent intent) {
        Pair squareParticipant = new Pair<>(view, ViewCompat.getTransitionName(view));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this, squareParticipant);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    private void startActivityGingerBread(View view, Intent intent) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
                view,
                view.getWidth() / 2, view.getHeight() / 2,//拉伸开始的坐标
                0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        ActivityCompat.startActivity(this, intent, options.toBundle());

    }


    ShareActionProvider shareProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        MenuItem item = menu.findItem(R.id.cart);
        CartActionProvider cartActionProvider = (CartActionProvider) MenuItemCompat.getActionProvider(item);

//        shareProvider = (ShareActionProvider) MenuItemCompat
//                .getActionProvider(menu.findItem(R.id.menu_item_share_provider));
//        if (shareProvider == null) {
//            System.out.println("----------------> is null");
//        } else {
//            System.out.println("--------------not null-----------");
//        }
//        shareProvider
//                .setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
//        shareProvider.setShareIntent(createShareIntent());

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * @return The sharing intent.
     */
    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "share");
        return shareIntent;
    }


}
