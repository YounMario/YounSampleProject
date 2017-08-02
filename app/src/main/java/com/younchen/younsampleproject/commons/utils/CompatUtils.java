package com.younchen.younsampleproject.commons.utils;

import android.os.Build;

/**
 * Created by yinlongquan on 2017/7/28.
 */

public class CompatUtils {

    public static boolean isJellyBeanCompatible() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isJellyBeanMr1Compatible() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isJellyBeanMr2Compatible() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean isKitkatCompatible() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isLollipopCompatible() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isMarshmallowCompatible() {
        return Build.VERSION.SDK_INT >= 23;
    }

}
