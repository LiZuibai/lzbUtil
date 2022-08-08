package com.lizuibai.lzbutil;

import android.util.Log;

public class L {

    private static boolean isShowLog = U.isDebug;
    private static boolean writeFileLog = false;
    private static String TAG = "LZB_";

    public static void v(String msg) {
        if (isShowLog) {
            Log.v(TAG, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.v(TAG, String.valueOf(msg));
        }
    }


    public static void v(String tag, String msg) {
        if (isShowLog) {
            Log.v(TAG + tag, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.v(TAG + tag, String.valueOf(msg));
        }
    }

    public static void d(String msg) {
        if (isShowLog) {
            Log.d(TAG, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.d(TAG, String.valueOf(msg));
        }
    }

    public static void d(String tag, String msg) {
        if (isShowLog) {
            Log.d(TAG + tag, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.d(TAG + tag, String.valueOf(msg));
        }
    }

    public static void i(String msg) {
        if (isShowLog) {
            Log.i(TAG, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.i(TAG, String.valueOf(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (isShowLog) {
            Log.i(TAG + tag, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.i(TAG + tag, String.valueOf(msg));
        }
    }

    public static void w(String msg) {
        if (isShowLog) {
            Log.w(TAG, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.w(TAG, String.valueOf(msg));
        }
    }

    public static void w(String tag, String msg) {
        if (isShowLog) {
            Log.w(TAG + tag, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.w(TAG + tag, String.valueOf(msg));
        }
    }

    public static void e(String msg) {
        if (isShowLog) {
            Log.e(TAG, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.e(TAG, String.valueOf(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (isShowLog) {
            Log.e(TAG + tag, String.valueOf(msg));
        }
        if (writeFileLog) {
            FLog.e(TAG + tag, String.valueOf(msg));
        }
    }
}
