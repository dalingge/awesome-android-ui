package com.dalingge.awesome.activity;

import android.os.Bundle;
import android.util.Log;

import com.dalingge.awesome.R;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class BottomBarActivity extends BaseActivity {

    //    int[] testColors = {0xFF7BA3A8,0xFFF4F3DE,0xFFBEAD92,0xFFF35A4A,0xFF5B4947};
    //    int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    int[] testColors = {0xFF00796B,0xFF5B4947,0xFF607D8B,0xFFF57C00,0xFFF57C00};

    private static final int BOTTOM_ITEM_TITLE_MEIZI_INDEX = 0;
    private static final int BOTTOM_ITEM_TITLE_ANDROID_INDEX = 1;
    private static final int BOTTOM_ITEM_TITLE_IOS_INDEX = 2;
    private static final int BOTTOM_ITEM_TITLE_VIDEO_INDEX = 3;
    private static final int BOTTOM_ITEM_TITLE_WEB_INDEX = 4;

    @BindView(R.id.tab) PagerBottomTabLayout tab;

    private BottomBar mBottomBar;


    @Override
    protected boolean isBack() {
        return true;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_bottom_bar;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        initBottomBar(savedInstanceState);
    }


    @Override protected void initView() {

    }

    Controller controller;
    private void initBottomBar(Bundle savedInstanceState) {

        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
            .setDefaultIcon(android.R.drawable.ic_menu_send)
            .setText("信息")
            .setSelectedColor(testColors[0])
            .setTag("A")
            .build();

        //构建导航栏,得到Controller进行后续控制
        controller=tab.builder()
            .addTabItem(tabItemBuilder)
            .addTabItem(android.R.drawable.ic_menu_compass, "位置",testColors[1])
            .addTabItem(android.R.drawable.ic_menu_search, "搜索",testColors[2])
            .addTabItem(android.R.drawable.ic_menu_help, "帮助",testColors[3])
             //.setMode(TabLayoutMode.HIDE_TEXT)
             //.setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
            .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
            .build();
        controller.setMessageNumber("A",100);
        controller.setDisplayOval(1,true);
        controller.addTabItemClickListener(listener);
        // mBottomBar = BottomBar.attach(this, savedInstanceState);
        // mBottomBar.noNavBarGoodness();
        // mBottomBar.useFixedMode(); // show all title and icon on bottom bar
        // mBottomBar.setItems(R.menu.bottombar_menu);
        //
        // // Setting colors for different tabs when there's more than three of them.
        // // You can set colors for tabs in three different ways as shown below.
        // mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_MEIZI_INDEX, ContextCompat.getColor(this, R.color.colorAccent));
        // mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_ANDROID_INDEX, ContextCompat.getColor(this, R.color.colorPrimary));
        // mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_IOS_INDEX, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        // mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_VIDEO_INDEX, ContextCompat.getColor(this, R.color.colorLightBlue));
        // mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_WEB_INDEX, ContextCompat.getColor(this, R.color.sa_green));
        // mBottomBar.setActiveTabColor("#009688");
        //
        // BottomBarBadge unreadMessages = mBottomBar.makeBadgeForTabAt(0, "#FF0000", 13);
        // unreadMessages.show();
        //// unreadMessages.hide();
        //
        // // Change the displayed count for this badge.
        // unreadMessages.setCount(4);
        //
        // // Change the show / hide animation duration.
        //// unreadMessages.setAnimationDuration(200);
        //
        // // If you want the badge be shown always after unselecting the tab that contains it.
        //unreadMessages.setAutoShowAfterUnSelection(true);

        // If you don't want this badge to be hidden after selecting the tab contains it.
        //unreadMessages.setAutoShowAfterUnSelection(false);
    }

    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag)
        {
            Log.i("asd","onSelected:"+index+"   TAG: "+tag.toString());
            if(index==3){
                controller.setMessageNumber("A",0);
                controller.setDisplayOval(1,false);
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
        }
    };

}

