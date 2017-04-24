package com.younchen.younsampleproject.sys.notification.model;

import android.app.Notification;

/**
 * Created by Administrator on 2017/4/19.
 */
public interface NotificationObserver {
    public void onReceiveMessage(ChatNotification notification);
}
