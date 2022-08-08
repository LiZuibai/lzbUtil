package com.lizuibai.lzbutil;

import android.text.TextUtils;

public class M {

    public static int toInt(String input, int def) {
        try {
            int value = Integer.parseInt(input);
            return value;
        } catch (Throwable e) {

        }
        return def;
    }

    public static float toFloat(String input, float def) {
        try {
            float value = Integer.parseInt(input);
            return value;
        } catch (Throwable e) {

        }
        return def;
    }

    public static boolean isInteger(String input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        return input.matches("[0-9]+");
    }
}
