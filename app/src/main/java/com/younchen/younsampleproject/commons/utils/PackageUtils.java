package com.younchen.younsampleproject.commons.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.younchen.younsampleproject.App;

import java.util.List;

public class PackageUtils {

    public static boolean isSystemApp(String pkgName) {

        ApplicationInfo ai = getApplicationInfo(pkgName);
        if (ai != null) {
            return (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } else {
            return false;
        }
    }

    public static boolean isGPAvailable(Context ctx) {
        if (!isPkgInstalled("com.android.vending")) {
            return false;
        }

        // 判断GP服务包
        if (null == getPackageInfo("com.google.android.gsf")) {
            return false;
        }

        return true;
    }

    public static boolean openGooglePlay(Context context, String pkgName) {

        boolean result = false;

        if (isGPAvailable(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.android.vending");
            if (!(context instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            }
            intent.setData(Uri.parse("market://details?id=" + pkgName + "&referrer=utm_source%3D20004"));
            result = startActivity(context, intent);

        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + pkgName + "&referrer=utm_source%3D20004"));
            if (isPkgInstalled("com.android.browser")) {
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            result = startActivity(context, intent);
        }

        return result;
    }

    // 必须都使用此方法打开外部activity,避免外部activity不存在而造成崩溃，
    public static boolean startActivity(Context context, Intent intent) {
        boolean bResult = true;
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            bResult = false;
        }
        return bResult;
    }

    public static boolean isPkgInstalled(String pkgName) {
        return (getApplicationInfo(pkgName) != null);
    }

    public static PackageInfo getPackageInfo(String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return null;
        }

        Context ctx = App.getInstance().getApplicationContext();

        try {
            return ctx.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
        }

        return null;
    }


    public static ApplicationInfo getApplicationInfo(String pkgName) {

        if (TextUtils.isEmpty(pkgName)) {
            return null;
        }

        Context ctx = App.getInstance().getApplicationContext();

        try {
            PackageInfo pkgInfo = ctx.getPackageManager().getPackageInfo(pkgName, 0);
            if (pkgInfo != null) {
                ApplicationInfo appInfo = pkgInfo.applicationInfo;
                return appInfo;
            }
        } catch (Exception e) {
        }

        return null;
    }


    public static String getPkgName(ActivityManager.RunningAppProcessInfo rpi) {
        if (rpi != null && rpi.pkgList != null && rpi.pkgList.length > 0) {
            return rpi.pkgList[0];
        } else {
            return null;
        }
    }

    public static void openTrustInfoSetting(String packName, Context context) {
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + packName)));
    }


    public static final int RESULT_FAIL = -1;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_BETTER = 1;
    public static final int RESULT_EXTRA = 2;

    public static Drawable getAppIcon(String packname) {
        try {
            PackageManager pm = App.getInstance().getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            if (info != null) {
                return info.loadIcon(pm);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppLabel(String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return null;
        }

        Context ctx = App.getInstance().getApplicationContext();
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(pkgName, 0);
            if (info != null) {
                return info.applicationInfo.loadLabel(ctx.getPackageManager()).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode() {
        // 获取packagemanager的实例
        Context context = App.getInstance();
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (packInfo == null) {
            return 0;
        }

        return packInfo.versionCode;
    }


    public static int getAppVersion(String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return 0;
        }

        Context ctx = App.getInstance().getApplicationContext();
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(pkgName, 0);
            if (info != null) {
                return info.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Intent getAppIntentWithPackageName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageinfo = null;
        try {
            packageinfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return null;
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        List<ResolveInfo> list = null;
        try {
            list = packageManager.queryIntentActivities(resolveIntent, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list != null && !list.isEmpty()) {
            ResolveInfo resolveInfo = list.get(0);
            if (resolveInfo != null) {
                String pn = resolveInfo.activityInfo.packageName;
                String className = resolveInfo.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(pn, className);
                intent.setComponent(cn);
                return intent;
            }
        }
        return null;
    }


    public static Intent getSecuritySettingIntent(Context context) {
        if (Build.BRAND.equalsIgnoreCase("huawei")) {
            Intent intent = new Intent();
            intent.setClassName("com.android.settings",
                    "com.android.settings.fingerprint.FingerprintMainSettingsActivity");
            return intent;
        } else {
            Intent intent = new Intent("android.settings.SECURITY_SETTINGS");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            return intent;
        }
    }


    public static void uninstall(Context context, String pkg) {
        if (context == null || TextUtils.isEmpty(pkg)) {
            return;
        }

        Uri packageURI = Uri.parse("package:" + pkg);
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    public static Intent getSystemBrightScreenTimeSettingIntent() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_DISPLAY_SETTINGS);
        return intent;
    }

    public static boolean gotoGooglePlay(Context context, String url) {
        if (null == context || TextUtils.isEmpty(url)) {
            return false;
        }

        if (isGPAvailable(context)) {
            int index = -1;
            if (!url.startsWith("market://") && (index = url.indexOf("?")) >= 0) {
                url = "market://details" + url.substring(index);
            }
            return launchGooglePlay(context, url);
        }

        return launchBrowser(context, url);
    }

    private static boolean launchGooglePlay(Context context, String uriString) {
        if (context == null || TextUtils.isEmpty(uriString)) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.android.vending");
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }
        intent.setData(Uri.parse(uriString));
        return startActivity(context, intent);
    }

    public static boolean launchBrowser(Context context, String uriString) {
        if (context == null || TextUtils.isEmpty(uriString)) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uriString));
        if (isPkgInstalled("com.android.browser")) {
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return startActivity(context, intent);
    }

}
