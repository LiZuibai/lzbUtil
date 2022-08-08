package com.lizuibai.lzbutil;

import android.content.Context;
import android.content.SharedPreferences;

public class Sp {

    private static SharedPreferences mSp = U.CONTEXT.getSharedPreferences("common", Context.MODE_PRIVATE);

    public static String getValue(String key, String def) {
        if (key != null) {
            return mSp.getString(key, def);
        }
        return null;
    }

    public static void putValue(String key, String value) {
        if (key != null) {
            mSp.edit().putString(key, value).apply();
        }
    }

    public static boolean getValue(String key, boolean def) {
        if (key != null) {
            return mSp.getBoolean(key, def);
        }
        return def;
    }

    public static void putValue(String key, boolean value) {
        if (key != null) {
            mSp.edit().putBoolean(key, value).apply();
        }
    }
}
