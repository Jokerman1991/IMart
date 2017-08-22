package com.virgo.mart.common.util;

import android.util.Log;

/**
 * Created by wangdeyu on 17-8-22.
 */

public class LogUtils {

    public static void d(String tag, String msg) {
        d(tag, "", msg);
    }

    public static void d(String tag, String module, String msg) {
        d(tag, module, "", msg);
    }

    public static void d(String tag, String module, String method, String msg) {
        Log.d(tag, module + "::" + method + ": " + msg);
    }
}
