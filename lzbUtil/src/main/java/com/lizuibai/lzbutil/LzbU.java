package com.lizuibai.lzbutil;

import android.content.Context;

import java.io.Closeable;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class LzbU {

    public static boolean isDebug = false;
    public static Context CONTEXT;

    public static void init(Context context) {
        if (CONTEXT == null && context != null) {
            CONTEXT = context.getApplicationContext();
        }
    }

    public static String getUUID() {
        String id;
        try {
            UUID uuid = UUID.randomUUID();
            id = uuid.toString();
        } catch (Throwable e) {
            id = System.currentTimeMillis() + "_" + Math.random();
        }
        return id;
    }

    public static boolean isEmpty(Collection list) {
        return list == null || list.isEmpty();
    }

    public boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public boolean isEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(float[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(double[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(long[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {

            }
        }
    }

    public static void close(HttpURLConnection conn) {
        if (conn != null) {
            try {
                conn.disconnect();
            } catch (Throwable e) {

            }
        }
    }
}
