package com.younchen.younsampleproject.sys.notification.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import java.util.List;

public class NotificationServiceUtil {
    private static final String TAG = "NotificationServiceUtil";

    /**
     * @param context
     * @return service is open return null, otherwise return the intent to start
     * settings
     */
    public static Intent checkServiceStatus(Context context) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 18) {
            if (!NotificationServiceUtil.CheckNotifiServiceValid(context)) {
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
        } else {
//            if (!AccessibilityServiceUtils.isAccessibilitySettingsOn()) {
                intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
//            }
        }

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        return intent;
    }

    public static Intent getNotificationServiceSettingIntent() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 18) {
            intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        } else {
            intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }


//    public static boolean isServiceAvailable() {
//        if (!RuntimeCheck.IsServiceProcess())
//            throw new IllegalAccessError("NON SERVICE PROCESS CANNOT CALL THIS METHOD");
//        return CheckNotifiServiceValid(MoSecurityApplication.getAppContext());
//    }

    public static boolean checkServiceValid(Context context) {
        final boolean valid = checkServiceStatus(context) == null;
//        OpLog.toFile("AnalyzeNotificationAc", "checkServiceValid -> valid:" + valid);
        return valid;
    }


    private static boolean CheckNotifiServiceValid(Context context) {
        final String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
//        OpLog.toFile("AnalyzeNotificationAc", "CheckNotifiServiceValid -> flat:" + flat);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null && cn.getPackageName().equals(context.getPackageName())) {
//                    OpLog.toFile("AnalyzeNotificationAc", "CheckNotifiServiceValid -> packageName:" + cn.getPackageName());
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean IsSystemSupportNotifyMsg(){
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean IsNotificationServiceEnable(Context context){
        if ( !IsSystemSupportNotifyMsg() )
            return false;

        if (Build.VERSION.SDK_INT >= 18) {
            return CheckNotifiServiceValid(context);
        } else {
            return isAccessibilitySettingsOn(context);
        }
    }

    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        // 这里暂时先写死，不然的话会有问题
        // 这里有个国内版与国际版的问题
        final String service = mContext.getPackageName() + "/com.cleanmaster.boost.acc.service.AccessibilityKillService";

        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    if (accessabilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        else {
        }

        return accessibilityFound;
    }

    public final static NotificationCompat.Action getReplyAction(List<NotificationCompat.Action> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (NotificationCompat.Action act : list) {
            if (act == null || act.getRemoteInputs() == null) {
                continue;
            }
            if (act.getRemoteInputs().length > 0) {
                return act;
            }
        }
        return null;
    }
}
