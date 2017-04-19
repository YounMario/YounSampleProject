package com.younchen.younsampleproject.sys.notification;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.younchen.younsampleproject.sys.notification.model.NotificationAction;

/**
 * Created by Administrator on 2017/4/13.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

    public static final String TAG = "sbn";

    public void onNotificationPosted(StatusBarNotification paramStatusBarNotification) {
        NotificationAction notificationAction = NotificationManager.getInstance().extractWearNotification(paramStatusBarNotification.getNotification(), paramStatusBarNotification.getPackageName(), paramStatusBarNotification.getTag(), this, paramStatusBarNotification.getId());
        NotificationDispatcher.getInstance().dispatchNotification(notificationAction);
    }

    public void onNotificationRemoved(StatusBarNotification paramStatusBarNotification) {
    }
}
