package com.dalingge.awesome.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import com.dalingge.awesome.R;
import com.dalingge.awesome.activityoptions.activity.MainOptionsActivity;
import com.dalingge.awesome.adapter.MainAdapter;
import com.dalingge.awesome.transitionseverywhere.MainTransitionsActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private String[] strings ={"有间隔的滑动切换图片","上下滑动跟随到图片浏览","底部导航栏","展示更多文字","Options的动画实现兼容","Transitions的动画实现兼容"};
    private Class[] classes={ViewPagerActivity.class,PullPagerActivity.class, BottomBarActivity.class,TextMoreActivity.class,
        MainOptionsActivity.class, MainTransitionsActivity.class};

    @Bind(R.id.recycler_view_team) RecyclerView recyclerViewTeam;


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
        recyclerViewTeam.setAdapter(new MainAdapter(strings,onRecyclerItemClick));
    }

    private MainAdapter.OnRecyclerItemClick onRecyclerItemClick = new MainAdapter.OnRecyclerItemClick() {
        @Override
        public void onItemClick(View view) {
            Intent intent = new Intent(
                MainActivity.this, classes[(Integer) view.getTag()]);
            intent.putExtra("title",strings[(Integer) view.getTag()]);
            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
                startActivityLollipop (view,intent);
            }else {
                startActivityGingerBread(view, intent);
            }

        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startActivityLollipop(View view, Intent intent) {
        Pair squareParticipant = new Pair<>(view, ViewCompat.getTransitionName(view));
        ActivityOptionsCompat options =ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this, squareParticipant);

        ActivityCompat.startActivity(this,intent,options.toBundle());
    }

    private void startActivityGingerBread(View view, Intent intent) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
            view,
            view.getWidth()/2, view.getHeight()/2,//拉伸开始的坐标
            0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        ActivityCompat.startActivity(this,intent,options.toBundle());

    }

    @Override public void onClick(View v) {

    }

}
