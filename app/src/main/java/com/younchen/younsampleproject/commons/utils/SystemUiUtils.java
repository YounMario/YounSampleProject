package com.younchen.younsampleproject.commons.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.ViewConfiguration;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SystemUiUtils {

    public final static String[] PhoneModePkgS = new String[]{
            "MX4:Meizu",
            "motorola:XT919",
            "X909:OPPO"
    };

    private static String sNavBarOverride = null;

    /**
     * 指定机型直接返回没有虚拟键
     *
     * @return
     */
    private static boolean filterSpecialPhoneMode() {
        return isSpecialMode(Build.MODEL + ":" + Build.BRAND);
    }

    private static boolean isSpecialMode(String pkg) {
        for (String x : PhoneModePkgS) {
            if (x.equals(pkg)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNavBar(Context context) {

        if (filterSpecialPhoneMode()) {
            return false;
        }
        // Android allows a system property to override the presence of the navigation bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
        }
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    public static int getNavigationBarHeight(Context context) {
        Resources res = context.getResources();
        int result = 0;
        if (!hasNavBar(context)) return result;
        String key = "navigation_bar_height";
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        } else {
            key = "navigation_bar_height_landscape";
            resourceId = res.getIdentifier(key, "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

}
