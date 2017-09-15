package com.virgo.mart;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.virgo.mart.common.BaseActivity;
import com.virgo.mart.common.util.LogUtils;

/**
 * Created by wangdeyu on 17-8-21.
 */

public class MartActivity extends BaseActivity {

    private static final String TAG                 = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate: main");
        setContentView(R.layout.activity_mart);
    }

}
