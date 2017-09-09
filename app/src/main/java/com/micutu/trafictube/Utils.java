package com.micutu.trafictube;

import android.content.Context;
import android.content.res.TypedArray;

public class Utils {

    public static int getColorFromAttribute(Context context, int attribute) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attribute});
        int color = ta.getColor(0, 0);
        ta.recycle();

        return color;
    }
}
