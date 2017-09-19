package com.younchen.younsampleproject.sys.topquery.model;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;


import com.younchen.younsampleproject.commons.log.YLog;

import java.lang.reflect.Field;
import java.util.List;


/**
 *
 */
public class TopAppQuery {

    private final static String TAG = "TopAppQuery";

    private static final String USAGE_STATS_SERVICE = "usagestats";
    private static UsageStatsManager sUsageManager = null;

    public static boolean isTopLauncher(Context appContext) {
        ComponentName coName = getTopAppPkgName(appContext);
        if (coName == null || TextUtils.isEmpty(coName.getPackageName())) {
            YLog.i(TAG, "isTopLauncher  CANT get top app package");
            return false;
        }
        YLog.i(TAG, "isTopLauncher  TOP=" + coName.getPackageName());
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo info = appContext.getPackageManager().resolveActivity(intent, 0);
        if (info == null) {
            YLog.i(TAG, "isTopLauncher  CANT resolveActivity CATEGORY_HOME");
            return false;
        }
        YLog.i(TAG, "isTopLauncher  " + coName.getPackageName() + " VS " + info.activityInfo.packageName);
        return TextUtils.equals(coName.getPackageName(), info.activityInfo.packageName);
    }

    public static ComponentName getTopAppPkgName(Context appContext) {
        ComponentName topAppComponentName = null;
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            if (isUsageAccessEnable()) {
                topAppComponentName = getMoveToFgComponent(appContext);
            }

            if (topAppComponentName == null || TextUtils.isEmpty(topAppComponentName.getPackageName())) {
                topAppComponentName = getTopAppPkgNameAbove21(appContext);
            }

        } else {
            topAppComponentName = getTopAppPkgNameBelow21(appContext);
        }
        if (null == topAppComponentName) {
            topAppComponentName = new ComponentName("", "");
        }
        return topAppComponentName;
    }

    private static boolean isUsageAccessEnable() {
        return true;
    }

    public static ComponentName getTopAppPkgNameEx(Context appContext, boolean usageStatsEnabled) {

        ComponentName topAppComponentName = null;
        if (android.os.Build.VERSION.SDK_INT >= 21) {

            if (usageStatsEnabled || isUsageAccessEnable()) {
//                OpLog.toFile(TAG, "isUsageAccessEnable true");
                topAppComponentName = getMoveToFgComponent(appContext);
            }

            if (topAppComponentName == null || TextUtils.isEmpty(topAppComponentName.getPackageName())) {
                topAppComponentName = getTopAppPkgNameAbove21(appContext);
            }

        } else {
            topAppComponentName = getTopAppPkgNameBelow21(appContext);
        }

        if (null == topAppComponentName)
            topAppComponentName = new ComponentName("", "");

        return topAppComponentName;
    }

    private static ComponentName getTopAppPkgNameBelow21(Context appContext) {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return new ComponentName("", "");
        }

        List<RunningTaskInfo> taskRunnings = null;
        RunningTaskInfo runningTask = null;
        try {
            taskRunnings = am.getRunningTasks(1);
        } catch (SecurityException e) {
        } catch (NullPointerException e) {
        } catch (NoSuchFieldError e) {
        }

        if (taskRunnings != null && taskRunnings.size() > 0) {
            runningTask = taskRunnings.get(0);
            if (runningTask != null) {
                ComponentName topActivity = runningTask.topActivity;
                return topActivity;
            }
        }

        return new ComponentName("", "");
    }

    private static long sLastEventTime = -1;
    private static UsageEvents.Event sQueryEvent;
    private static final long USAGE_STATS_TIME_CHANGE = (2 * 1000) + 500;

    @SuppressLint("NewApi")
    public static ComponentName getMoveToFgComponent(Context ctx) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        long end = System.currentTimeMillis();
        UsageStatsManager usageManager = getUsageManager(ctx);
        final long begin = (sLastEventTime == -1 || sLastEventTime >= end) ?
                (end - 60 * 1000) : sLastEventTime - 10;//这里-10的原因是要保证拿到一个【发生了移动到前台的App】，
        // 如果是准确sLastEventTime，有可能到现在为止都没有应用变化，导致取不到App

        // Add extra 2 seconds to the end since UsageStatsService use SystemClock.elaspedRealTime()
        // instead of System.currentTimeMillis() to calculate the app usage time.
        // Therefore, its time might shift from the current time we got.
        final long changedEnd = end + USAGE_STATS_TIME_CHANGE;

        final UsageEvents events;
        try {
            events = usageManager.queryEvents(begin, changedEnd);
        } catch (Throwable t) {
            return null;
        }

        String pkgName = null;
        String clsName = null;

        if (sQueryEvent == null) {
            sQueryEvent = new UsageEvents.Event();
        }

        while (events.hasNextEvent()) {
            events.getNextEvent(sQueryEvent);
            if (sQueryEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                pkgName = sQueryEvent.getPackageName();
                clsName = sQueryEvent.getClassName();
                sLastEventTime = sQueryEvent.getTimeStamp();
            }
        }

        ComponentName comp = null;
        if (pkgName != null && clsName != null) {
            comp = new ComponentName(pkgName, clsName);
        }

        return comp;
    }

    @SuppressLint("NewApi")
    private static UsageStatsManager getUsageManager(Context ctx) {
        if (sUsageManager == null) {
            synchronized (TopAppQuery.class) {
                if (sUsageManager == null) {
                    sUsageManager = (UsageStatsManager) ctx.getSystemService(USAGE_STATS_SERVICE);
                }
            }
        }
        return sUsageManager;
    }

    private static final String SETTING_PACKAGE_NAME = "com.android.settings";

    @SuppressLint("NewApi")
    private static ComponentName getTopAppPkgNameAbove21(Context appContext) {
        //Android 5.0 获取 TOP APP的性能太差，不能每秒都枚举，两次枚举直接的间隔必须大于 5 秒

        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null)
            return null;

        List<ActivityManager.RunningAppProcessInfo> appList = null;
        try {
            appList = am.getRunningAppProcesses();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (null == appList || appList.size() <= 0) {
            return null;
        }

        ComponentName componentName = null;

        for (ActivityManager.RunningAppProcessInfo process : appList) {
            int flags = getProcessFlag(process);
            boolean hasActivity = ((flags & FLAG_HAS_ACTIVITY) > 0);

            if (hasActivity && process.importanceReasonCode == 0 &&
                    (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)) {

                String pkgName = null;
                if (process.pkgList != null && process.pkgList.length > 0
                        // Don't use the package list for HTC setting //app DeviceUtils.isHTC() &&
                        && !(process.processName.toLowerCase().equals(SETTING_PACKAGE_NAME))) {
                    pkgName = process.pkgList[0];
                } else {
                    pkgName = process.processName;
                }

                // Special handling for QQ, as it confuses our top app determination
                if (process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                        process.processName.equalsIgnoreCase("com.tencent.mobileqq")) {
                    continue;
                }
                componentName = new ComponentName(pkgName, "");
                break;
            }

        }
        if (componentName == null) {
            //
            componentName = new ComponentName("", "");
        }
        return componentName;
    }

    public static boolean isSameComponentName(ComponentName thisName, ComponentName thatName) {
        if (TextUtils.isEmpty(thisName.getPackageName()) || TextUtils.isEmpty(thatName.getPackageName())) {
            return false;
        }
        ;
        if (thisName.getPackageName().equalsIgnoreCase(thatName.getPackageName())) {
            if (thisName.getShortClassName().isEmpty()
                    || thatName.getShortClassName().isEmpty()
                    || thisName.getShortClassName().equalsIgnoreCase(thatName.getShortClassName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private final static int FLAG_HAS_ACTIVITY = 1 << 2;
    private static Field mFlagsField;

    private static int getProcessFlag(ActivityManager.RunningAppProcessInfo processInfo) {
        if (null == processInfo) {
            return FLAG_HAS_ACTIVITY;
        }

        int flags = FLAG_HAS_ACTIVITY;
        try {
            if (mFlagsField == null) {
                mFlagsField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("flags");
            }

            flags = mFlagsField.getInt(processInfo);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        return flags;
    }

}
