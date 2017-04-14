package com.younchen.younsampleproject.sys.notification;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by Administrator on 2017/4/13.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

    public static final String TAG = "sbn";

    public void onNotificationPosted(StatusBarNotification paramStatusBarNotification) {
    }

    public void onNotificationRemoved(StatusBarNotification paramStatusBarNotification) {
    }
}
