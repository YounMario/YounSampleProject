package com.younchen.younsampleproject.commons.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;

import com.younchen.younsampleproject.App;

import java.io.File;

/**
 * Created by Administrator on 2017/4/24.
 */

public class Evn {


    public static String getPkgName(Context context) {
        return context.getPackageName();
    }

    public static String getPkgName() {
        return App.getInstance().getPackageName();
    }

    static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionName + "(" + info.versionCode + ")";
        } catch (/*NameNotFoundException*/Exception e) {
            return null;
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (/*NameNotFoundException*/Exception e) {
            return -1;
        }
    }


    public static String getExternalStorageDirectoryx() {
        File apkCacheFile = App.getInstance().getExternalFilesDir(null);

        String apkCacheDir = null;
        if (apkCacheFile != null) {
            apkCacheDir = apkCacheFile.getAbsolutePath();
        }

        try {
            if (apkCacheDir == null) {
                apkCacheDir = new File(Environment.getExternalStorageDirectory(), "YounSample").getAbsolutePath();
            }

            final File file = new File(apkCacheDir + "/");
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return apkCacheDir;
    }

    public static String getAppPath() {
        return App.getInstance().getFilesDir().getPath();
    }
}
