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

import com.virgo.mart.common.BaseActivity;
import com.virgo.mart.common.util.LogUtils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG                 = "SplashActivity";
    private static final int SPLASH_TIME_MAX        = 5100; // ms
    private static final int SPLASH_TIME_INTERVEL   = 1000; // ms
    private static final int SPLASH_IMAGE_BLUE      = 1;
    private static final int SPLASH_IMAGE_YELLOW    = 2;
    private static final int SPLASH_IMAGE_PURPLE    = 3;

    private Timer mSplashTimer;
    private SplashCountDownTimer mCountDownTimer;
    private View mRootView;
    private TextView mTvSkip;
    private boolean isStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        mSplashTimer = new Timer();
        mCountDownTimer = new SplashCountDownTimer(SPLASH_TIME_MAX, SPLASH_TIME_INTERVEL);
        mRootView = findViewById(R.id.splash_root);
        switch (new Random().nextInt(3) + 1) {
            case SPLASH_IMAGE_BLUE:
                mRootView.setBackgroundResource(R.drawable.splash_whale_blue);
                break;
            case SPLASH_IMAGE_YELLOW:
                mRootView.setBackgroundResource(R.drawable.splash_whale_yellow);
                break;
            case SPLASH_IMAGE_PURPLE:
                mRootView.setBackgroundResource(R.drawable.splash_whale_purple);
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
            mSplashTimer.schedule(new SplashTimerTask(), SPLASH_TIME_MAX);
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
        startActivity(new Intent(SplashActivity.this, MartActivity.class));
        SplashActivity.this.finish();
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
