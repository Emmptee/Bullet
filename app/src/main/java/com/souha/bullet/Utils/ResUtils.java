package com.souha.bullet.Utils;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.souha.bullet.MyApplication;


public final class ResUtils {

    public static final int INVALID_COLOR = -1;

    private ResUtils() {
    }

    public static String getString(int strId) {
        if (strId <= 0) {
            return null;
        }
        return MyApplication.get().getResources().getString(strId);
    }

    public static String[] getStringArray(int strArrayId) {
        if (strArrayId <= 0) {
            return null;
        }
        return MyApplication.get().getResources().getStringArray(strArrayId);
    }

    public static String getString(int strId, Object... formatArgs) {
        if (strId <= 0) {
            return null;
        }
        return MyApplication.get().getResources().getString(strId, formatArgs);
    }

    public static int getColor(int colorId) {
        if (colorId <= 0) {
            return INVALID_COLOR;
        }
        return MyApplication.get().getResources().getColor(colorId);
    }

    public static Drawable getDrawable(int drawableId) {
        if (drawableId <= 0) {
            return null;
        }
        return MyApplication.get().getResources().getDrawable(drawableId);
    }
}
