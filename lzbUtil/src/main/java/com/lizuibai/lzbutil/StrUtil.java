package com.lizuibai.lzbutil;

import android.text.TextUtils;
import android.widget.TextView;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StrUtil {

    public static String encode(Object params) {
        if (params == null) {
            return "";
        }
        try {
            return URLEncoder.encode(String.valueOf(params), "utf-8").replaceAll("\\+", "20%");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.valueOf(params);
    }

    public static String nonNull(Object input) {
        if (input == null) {
            return "";
        }
        return String.valueOf(input);
    }

    public static boolean isSame(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    public static String getText(TextView tv) {
        if (tv == null) {
            return "";
        }
        return tv.getText().toString().trim();
    }

    public static boolean isSameIgnoreCase(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equalsIgnoreCase(b);
    }

    public static String getString(int strId) {
        return LzbU.CONTEXT.getString(strId);
    }

    public static String getString(int strId, Object... args) {
        return LzbU.CONTEXT.getString(strId, args);
    }

    public static String stream2String(InputStream inputStream) {
        return stream2String(inputStream, false);
    }

    public static String stream2String(InputStream inputStream, boolean needClose) {
        try {
            StringBuilder out = new StringBuilder();
            byte[] b = new byte[4096];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                out.append(new String(b, 0, len));
            }
            return out.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (needClose) {
                LzbU.close(inputStream);
            }
        }
        return null;
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    result.append("0").append(temp);
                } else {
                    result.append(temp);
                }
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
