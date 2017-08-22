package com.virgo.mart.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.virgo.mart.R;
import com.virgo.mart.constant.Constants;

/**
 * Created by wangdeyu on 17-8-21.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
    }

    private void initTheme() {
        // mThemeUtil = ThemeUtil.getInstance(this);
        // String theme = mThemeUtil.getTheme();
        String theme = "";
        switch (theme) {
            case Constants.Theme.Blue:
                setTheme(R.style.BlueTheme);
                break;

            case Constants.Theme.White:
                setTheme(R.style.WhiteTheme);
                break;

            case Constants.Theme.Gray:
                setTheme(R.style.GrayTheme);
                break;

            default:
                setTheme(R.style.BlueTheme);
                break;
        }
    }
}
