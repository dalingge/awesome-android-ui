package com.dalingge.awesome.activity;

import android.os.Handler;
import android.os.Message;

import com.dalingge.awesome.R;
import com.dalingge.awesome.anim.BlockLoadingAnim;
import com.dalingge.awesome.anim.ChromeLogoLoadingAnim;
import com.dalingge.awesome.anim.CircularCDLoadingAnim;
import com.dalingge.awesome.anim.CircularJumpLoadingAnim;
import com.dalingge.awesome.anim.CircularLoadingAnim;
import com.dalingge.awesome.anim.CircularRingLoadingAnim;
import com.dalingge.awesome.anim.CircularSmileLoadingAnim;
import com.dalingge.awesome.anim.CircularZoomLoadingAnim;
import com.dalingge.awesome.anim.EatBeanLoadingAnim;
import com.dalingge.awesome.anim.FinePoiStarLoadingAnim;
import com.dalingge.awesome.anim.GearsLoadingAnim;
import com.dalingge.awesome.anim.GearsTwoLoadingAnim;
import com.dalingge.awesome.anim.GhostLoadingAnim;
import com.dalingge.awesome.anim.LineWithTextLoadingAnim;
import com.dalingge.awesome.anim.NewsLoadingAnim;
import com.dalingge.awesome.anim.PlayBallLoadingAnim;
import com.dalingge.awesome.anim.WifiLoadingAnim;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class LoadingAnimActivity extends BaseActivity {

    @BindView(R.id.circular)
    CircularLoadingAnim circular;
    @BindView(R.id.circularcd)
    CircularCDLoadingAnim circularcd;
    @BindView(R.id.circularring)
    CircularRingLoadingAnim circularring;
    @BindView(R.id.circularjump)
    CircularJumpLoadingAnim circularjump;
    @BindView(R.id.circularsmile)
    CircularSmileLoadingAnim circularsmile;
    @BindView(R.id.circularzoom)
    CircularZoomLoadingAnim circularzoom;
    @BindView(R.id.linewithtext)
    LineWithTextLoadingAnim linewithtext;
    @BindView(R.id.wifianim)
    WifiLoadingAnim wifianim;
    @BindView(R.id.playball)
    PlayBallLoadingAnim playball;
    @BindView(R.id.eatbeans)
    EatBeanLoadingAnim eatbeans;
    @BindView(R.id.gearloading)
    GearsLoadingAnim gearloading;
    @BindView(R.id.chromelogoloading)
    ChromeLogoLoadingAnim chromelogoloading;
    @BindView(R.id.geartwoloading)
    GearsTwoLoadingAnim geartwoloading;
    @BindView(R.id.ghostloadinganim)
    GhostLoadingAnim ghostloadinganim;
    @BindView(R.id.finepoistar)
    FinePoiStarLoadingAnim finepoistar;
    @BindView(R.id.newsloadanim)
    NewsLoadingAnim newsloadanim;
    @BindView(R.id.blockloadinganim)
    BlockLoadingAnim blockloadinganim;


    private int mValueLinWithText = 0;
    private Timer mTimerLineWithText = new Timer();

    int mValueNews = 0;
    private Timer mTimerNews = new Timer();

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_loading_anim;
    }

    @Override
    protected void initView() {
        circular.startAnim();
        circularcd.startAnim();
        circularring.startAnim();
        circularjump.startAnim();
        circularsmile.startAnim();
        circularzoom.startAnim();
        startLineWithText();
        wifianim.startAnim();
        playball.startAnim();
        eatbeans.startAnim();
        gearloading.startAnim();
        chromelogoloading.startAnim();
        geartwoloading.startAnim();
        ghostloadinganim.startAnim();
        finepoistar.startAnim();
        startNewsAnim();
        blockloadinganim.isShadow(true);
        blockloadinganim.startAnim();
    }
    private void startLineWithText() {
        mValueLinWithText = 0;
        if (mTimerLineWithText != null) {
            mTimerLineWithText.cancel();
        }
        mTimerLineWithText = new Timer();
        timerTaskLineWithText();
    }

    public void timerTaskLineWithText() {
        mTimerLineWithText.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mValueLinWithText < 100) {
                    mValueLinWithText++;
                    Message msg = mHandler.obtainMessage(2);
                    msg.arg1 = mValueLinWithText;
                    mHandler.sendMessage(msg);
                } else {
                    mTimerLineWithText.cancel();
                }
            }
        }, 0, 50);
    }

    private void stopNewsAnim() {
        newsloadanim.stopAnim();
        if (mTimerNews != null) {
            mTimerNews.cancel();
        }
    }


    private void startNewsAnim() {
        mValueNews = 0;
        if (mTimerNews != null) {
            mTimerNews.cancel();
        }
        mTimerNews = new Timer();
        timerTaskNews();
    }

    public void timerTaskNews() {
        mTimerNews.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mValueNews < 100) {
                    mValueNews++;
                    Message msg = mHandler.obtainMessage(1);
                    msg.arg1 = mValueNews;
                    mHandler.sendMessage(msg);
                } else {
                    mTimerNews.cancel();
                }
            }
        }, 0, 10);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                linewithtext.setValue(msg.arg1);
            } else if (msg.what == 1) {
                newsloadanim.setValue(msg.arg1);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        circular.stopAnim();
        circularcd.stopAnim();
        circularring.stopAnim();
        circularjump.stopAnim();
        circularsmile.stopAnim();
        circularzoom.stopAnim();
        wifianim.stopAnim();
        playball.stopAnim();
        eatbeans.stopAnim();
        gearloading.stopAnim();
        chromelogoloading.stopAnim();
        geartwoloading.stopAnim();
        ghostloadinganim.stopAnim();
        finepoistar.stopAnim();
        stopNewsAnim();
        blockloadinganim.isShadow(false);
        blockloadinganim.stopAnim();
    }
}
