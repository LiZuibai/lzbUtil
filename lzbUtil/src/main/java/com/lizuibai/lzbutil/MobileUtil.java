package com.lizuibai.lzbutil;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MobileUtil {
    private static long lastClickTime;
    private static int pkgVersionCode;
    private static String pkgVersionName;
    private static String pkgName;

    public static boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) U.CONTEXT
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        } catch (Throwable e) {

        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time < lastClickTime) { // 用户前调了系统时间
            lastClickTime = time;
            return false;
        }

        if (time - lastClickTime > 500) {
            lastClickTime = time;
            return false;
        }

        return true;
    }

    public static void hideKeyboard(View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Throwable e) {

        }
    }

    public static boolean isSoftKeyboardActive(Context context) {
        boolean isOpen = false;
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        } catch (Throwable e) {

        }
        return isOpen;
    }

    public static void showSoftKeyboard(View view) {
        try {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Throwable e) {

        }
    }

    public static int getPkgVersionCode() {
        if (pkgVersionCode > 0) {
            return pkgVersionCode;
        }
        try {
            PackageInfo info = U.CONTEXT.getPackageManager().getPackageInfo(U.CONTEXT.getPackageName(), 0);
            if (info != null) {
                pkgVersionCode = info.versionCode;
                pkgVersionName = info.versionName;
                pkgName = info.packageName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkgVersionCode;
    }

    public static String getPkgVersionName() {
        if (TextUtils.isEmpty(pkgVersionName)) {
            try {
                PackageInfo info = U.CONTEXT.getPackageManager().getPackageInfo(U.CONTEXT.getPackageName(), 0);
                if (info != null) {
                    pkgVersionCode = info.versionCode;
                    pkgVersionName = info.versionName;
                    pkgName = info.packageName;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pkgVersionName;
    }


    public static String getPkgName() {
        if (TextUtils.isEmpty(pkgName)) {
            try {
                PackageInfo info = U.CONTEXT.getPackageManager().getPackageInfo(U.CONTEXT.getPackageName(), 0);
                if (info != null) {
                    pkgVersionCode = info.versionCode;
                    pkgVersionName = info.versionName;
                    pkgName = info.packageName;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pkgName;
    }

    private static String imei;
    private static String androidId;

    public static synchronized String getImei() {
        if (imei == null) {
            try {
                Context context = U.CONTEXT;
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = null;
                if (tm != null) {
                    deviceId = tm.getDeviceId();
                }

                if (deviceId != null) {
                    imei = deviceId;
                } else {
                    imei = "";
                }
            } catch (Throwable e) {
                imei = "";
            }
        }

        return imei;
    }

    public static String getAndroidId() {
        if (androidId == null) {
            try {
                // Try to get the ANDROID_ID
                String aid = Settings.Secure.getString(U.CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);
                // Try to avoid NullPointerException when the _androidId is null
                // if (aid != null) {
                // aid = aid.toLowerCase();
                // }

                if (aid != null && aid.length() > 14 && !aid.equals("9774d56d682e549c"))// android
                // 2.2
                {
                    androidId = aid;
                }
            } catch (Throwable e) {

            }
        }
        return androidId;
    }

}
