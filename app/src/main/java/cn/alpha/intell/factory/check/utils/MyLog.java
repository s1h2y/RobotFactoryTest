package cn.alpha.intell.factory.check.utils;

import android.util.Log;

/**
 * Created by shy on 16-5-19.
 */
public class MyLog {

    public static final String LOGD_TAG = "alpha";

    public static final boolean enableLogD = true;
    public static final boolean enableLogW = true;
    public static final boolean enableLogE = true;

    public static void d(String info) {
        if (enableLogD) {
            Log.d(LOGD_TAG, info);
        }
    }

    public static void w(String info) {
        if (enableLogW) {
            Log.d(LOGD_TAG, info);
        }
    }

    public static void e(String info) {
        if (enableLogE) {
            Log.d(LOGD_TAG, info);
        }
    }
}
