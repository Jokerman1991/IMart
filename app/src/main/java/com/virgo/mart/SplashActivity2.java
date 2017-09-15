package com.virgo.mart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.virgo.mart.common.util.LogUtils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangdeyu on 17-9-13.
 */

public class SplashActivity2 extends AppCompatActivity {

    private static final String TAG                 = "SplashActivity";
    private static final int SPLASH_TIME_MAX        = 5100; // ms
    private static final int SPLASH_TIME_INTERVEL   = 1000; // ms
    private static final int SPLASH_IMAGE_BLUE      = 1;
    private static final int SPLASH_IMAGE_YELLOW    = 2;
    private static final int SPLASH_IMAGE_PURPLE    = 3;

    private Timer mSplashTimer;
    private SplashActivity2.SplashCountDownTimer mCountDownTimer;
    private ImageView mImageSplash;
    private TextView mTvSkip;
    private boolean isStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        init();
    }

    private void init() {
        mSplashTimer = new Timer();
        mCountDownTimer = new SplashActivity2.SplashCountDownTimer(SPLASH_TIME_MAX, SPLASH_TIME_INTERVEL);
        mImageSplash = (ImageView) findViewById(R.id.splash_image);
        switch (new Random().nextInt(3) + 1) {
            case SPLASH_IMAGE_BLUE:
                mImageSplash.setImageResource(R.drawable.splash_whale_blue);
                break;
            case SPLASH_IMAGE_YELLOW:
                mImageSplash.setImageResource(R.drawable.splash_whale_yellow);
                break;
            case SPLASH_IMAGE_PURPLE:
                mImageSplash.setImageResource(R.drawable.splash_whale_purple);
                break;
        }
        mTvSkip = (TextView) findViewById(R.id.skip_text);
        mTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    mSplashTimer.cancel();
                }
                lunchMart();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isStarted) {
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(300);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    LogUtils.d(TAG, "l: anim start");
                    mTvSkip.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    LogUtils.d(TAG, "l: anim end");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mTvSkip.startAnimation(animation);
            mCountDownTimer.start();
            mSplashTimer.schedule(new SplashActivity2.SplashTimerTask(), SPLASH_TIME_MAX);
            isStarted = true;
        }
    }

    class SplashTimerTask extends TimerTask {

        @Override
        public void run() {
            lunchMart();
        }
    }

    private void lunchMart() {
        startActivity(new Intent(SplashActivity2.this, GuideActivity.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        SplashActivity2.this.finish();
    }


    class SplashCountDownTimer extends CountDownTimer {

        public SplashCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mTvSkip.setText("跳过 " + (l / 1000));
            LogUtils.d(TAG, "l: " + l + ", " + (l / 1000));
            if (l < 1100) {
                mTvSkip.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation animation = new AlphaAnimation(1.0f, 0.0f);
                        animation.setDuration(300);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                LogUtils.d(TAG, "l: fade anim start");
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LogUtils.d(TAG, "l: fade anim end");
                                mTvSkip.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        mTvSkip.startAnimation(animation);
                    }
                }, 700);
            }
        }

        @Override
        public void onFinish() {
            LogUtils.d(TAG, "l: finish");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isStarted) {
            mSplashTimer.cancel();
            mSplashTimer = null;
        }
    }
}
