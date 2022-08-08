package com.lizuibai.lzbutil;

import android.graphics.Paint;
import android.text.TextPaint;

public class DimenUtils {

    public static final int NORMAL_PADDING = dp2pxInt(15);

    public static float getTextBaseLineByCenter(float bottom, TextPaint paint, int size) {
        paint.setTextSize(size);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return bottom - metrics.bottom;
    }

    public static float getTextBaseLineByCenter(float center, TextPaint paint) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float height = metrics.bottom - metrics.top;
        return center + height / 2 - metrics.bottom;
    }

    public static float dp2px(float dp) {
        return LzbU.CONTEXT.getResources().getDisplayMetrics().density * dp;
    }

    public static int dp2pxInt(float dp) {
        return (int) (LzbU.CONTEXT.getResources().getDisplayMetrics().density * dp);
    }

    public static int getDimen(int dimen) {
        return LzbU.CONTEXT.getResources().getDimensionPixelOffset(dimen);
    }

    public static int getScreenHeight() {
        return LzbU.CONTEXT.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return LzbU.CONTEXT.getResources().getDisplayMetrics().widthPixels;
    }
}
