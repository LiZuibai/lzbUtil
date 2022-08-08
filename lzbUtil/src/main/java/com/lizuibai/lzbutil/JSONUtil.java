package com.lizuibai.lzbutil;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JSONUtil {

    public static String getFirstElement(JSONArray array) {
        if (array == null || array.length() <= 0) {
            return "";
        }
        return String.valueOf(array.opt(0));
    }

    public static ArrayList<String> arrayToStringList(JSONArray array) {
        ArrayList<String> list = new ArrayList<>();
        if (array == null || array.length() <= 0) {
            return list;
        }
        for (int i = 0; i < array.length(); i++) {
            list.add(String.valueOf(array.opt(i)));
        }
        return list;
    }

    public static String formatJSON(Object json) {
        StringBuilder builder = new StringBuilder();
        if (json instanceof JSONObject) {
            formatJSONObject((JSONObject) json, builder, 0, null);
        } else if (json instanceof JSONArray) {
            formatJSONArray((JSONArray) json, builder, 0, null);
        } else {
            builder.append("NOT a JSONObject or a JSONArray");
        }

        return builder.toString();
    }

    private static final int SHRINK_SUM = 4;

    private static void formatJSONObject(JSONObject json, StringBuilder builder, int blankCount, String key) {
        appendBlank(builder, blankCount);
        if (!TextUtils.isEmpty(key)) {
            builder.append("\"");
            builder.append(key);
            builder.append("\": ");
        }
        builder.append("{\n");
        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String item = iterator.next();
            Object value = json.opt(item);
            if (value instanceof JSONObject) {
                formatJSONObject((JSONObject) value, builder, blankCount + SHRINK_SUM, item);
            } else if (value instanceof JSONArray) {
                formatJSONArray((JSONArray) value, builder, blankCount + SHRINK_SUM, item);
            } else {
                appendBlank(builder, blankCount + SHRINK_SUM);
                builder.append("\"");
                builder.append(item);
                builder.append("\": ");
                formatBasicType(value, builder);
            }
            if (iterator.hasNext()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        appendBlank(builder, blankCount);
        builder.append("}");
    }

    private static void formatJSONArray(JSONArray json, StringBuilder builder, int blankCount, String key) {
        if (!TextUtils.isEmpty(key)) {
            builder.append("\"");
            builder.append(key);
            builder.append("\": ");
        }
        builder.append("{\n");
        int len = json.length();
        for (int i = 0; i < len; i++) {
            Object value = json.opt(i);
            if (value instanceof JSONObject) {
                formatJSONObject((JSONObject) value, builder, blankCount + SHRINK_SUM, null);
            } else if (value instanceof JSONArray) {
                formatJSONArray((JSONArray) value, builder, blankCount + SHRINK_SUM, null);
            } else {
                appendBlank(builder, blankCount + SHRINK_SUM);
                builder.append("{\"");
                builder.append(String.valueOf(value));
                builder.append("\"}");
            }
            if (i != len - 1) {
                builder.append(",");
            }
            builder.append("\n");
        }

        appendBlank(builder, blankCount);
        builder.append("}");
    }

    private static void formatBasicType(Object value, StringBuilder builder) {
        String valStr = String.valueOf(value);
        if (value == null || "null".equalsIgnoreCase(valStr)) {
            builder.append("null");
        } else if ("true".equalsIgnoreCase(valStr)) {
            builder.append("true");
        } else if ("false".equalsIgnoreCase(valStr)) {
            builder.append("false");
        } else if (valStr.matches("(\\+|\\-)?\\d+")) {
            builder.append(valStr);
        } else if (valStr.matches("")) {
            builder.append(valStr);
        } else {
            builder.append("\"");
            if (valStr.contains("\"")) {
                valStr = valStr.replace("\"", "\\\\\"");
            }
            builder.append(valStr);
            builder.append("\"");
        }

    }


    private static void appendBlank(StringBuilder builder, int count) {
        while (count > 0) {
            builder.append(" ");
            count--;
        }
    }
}
