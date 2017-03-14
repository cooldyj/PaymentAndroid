package com.jerry.payment.mobile.utils;

import android.util.Log;

/**
 * Log工具类, DEBUG设置开关
 * Created by jerry on 2016/7/6.
 */
public class Logs {

    public static boolean DEBUG = true;

    private static String TAG = "JERRY";

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, (msg == null ? "null" : msg));
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, (msg == null ? "null" : msg));
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, (msg == null ? "null" : msg));
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, (msg == null ? "null" : msg));
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, (msg == null ? "null" : msg));
        }
    }

}
