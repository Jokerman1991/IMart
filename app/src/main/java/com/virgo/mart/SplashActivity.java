package com.virgo.mart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    private static final int SPLASH_TIME_MAX        = 5000; // ms
    private static final int SPLASH_TIME_INTERVEL   = 1000; // ms
    private static final int SPLASH_IMAGE_BLUE      = 1;
    private static final int SPLASH_IMAGE_YELLOW    = 2;
    private static final int SPLASH_IMAGE_PURPLE    = 3;

    private Timer mSplashTimer;
    private SplashCountDownTimer mCountDownTimer;
    private ImageView mImageSplash;
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
        mImageSplash = (ImageView) findViewById(R.id.splash_image);
        switch (new Random().nextInt(2) + 1) {
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
        mTvSkip = findViewById(R.id.skip_text);
        mTvSkip.setText("跳过 (" + 5 + "秒)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isStarted) {
            mCountDownTimer.start();
            mSplashTimer.schedule(new SplashTimerTask(), SPLASH_TIME_MAX);
            isStarted = true;
        }
    }

    class SplashTimerTask extends TimerTask {

        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, MartActivity.class));
            SplashActivity.this.finish();
        }
    }

    class SplashCountDownTimer extends CountDownTimer {

        public SplashCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mTvSkip.setText("跳过(" + (l / 1000) + "秒)");
        }

        @Override
        public void onFinish() {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashTimer.cancel();
        mSplashTimer = null;
    }
}
