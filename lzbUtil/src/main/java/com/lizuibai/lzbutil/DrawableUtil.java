package com.lizuibai.lzbutil;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class DrawableUtil {

    public static Drawable getRoundCornerShape(int fillColor, float roundRadius) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);

        return gd;
    }

    public static void changeDrawablePosition(final TextView tv) {
        if (tv == null) {
            return;
        }
        if (tv.getGravity() != Gravity.CENTER && tv.getGravity() != Gravity.CENTER_VERTICAL && tv.getGravity() != Gravity.CENTER_HORIZONTAL) {
            return;
        }
        tv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Drawable[] drawables = tv.getCompoundDrawables();
                tv.removeOnLayoutChangeListener(this);
                if (U.isEmpty(drawables)) {
                    return;
                }
                int padding = tv.getCompoundDrawablePadding();
                float textW = tv.getPaint().measureText(tv.getText().toString());

                float contentW = textW;
                if (drawables[0] != null) {
                    contentW = contentW + padding + drawables[0].getIntrinsicWidth();
                }
                if (drawables[2] != null) {
                    contentW = contentW + padding + drawables[2].getIntrinsicWidth();
                }
                int viewPadding = (int) ((tv.getWidth() - contentW) / 2);
                tv.setPadding(viewPadding, tv.getPaddingTop(), viewPadding, tv.getPaddingBottom());
            }
        });
    }
}
