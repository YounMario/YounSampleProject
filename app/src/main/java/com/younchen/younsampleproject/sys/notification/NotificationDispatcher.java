package com.younchen.younsampleproject.sys.notification;

import android.app.Notification;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.younchen.younsampleproject.sys.notification.model.ChatNotification;
import com.younchen.younsampleproject.sys.notification.model.NotificationAction;
import com.younchen.younsampleproject.sys.notification.model.NotificationObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
public class NotificationDispatcher {

    private static NotificationDispatcher mNotificationDispatcher;
    private List<NotificationObserver> mObservers = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            ChatNotification notification = (ChatNotification) msg.obj;
            for (NotificationObserver observer : mObservers) {
                observer.onReceiveMessage(notification);
            }
        }
    };

    public static NotificationDispatcher getInstance() {
        if (mNotificationDispatcher == null) {
            mNotificationDispatcher = new NotificationDispatcher();
        }
        return mNotificationDispatcher;
    }

    private NotificationDispatcher() {

    }

    public void dispatchNotification(ChatNotification notificationAction) {
        Message message = mHandler.obtainMessage();
        message.obj = notificationAction;
        mHandler.sendMessage(message);
    }

    public void unRegistObserver(NotificationObserver observer) {
        mObservers.remove(observer);
    }

    public void registObserver(NotificationObserver observer) {
        mObservers.add(observer);
    }
}
