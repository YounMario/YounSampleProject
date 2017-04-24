package com.younchen.younsampleproject.sys.notification;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import com.younchen.younsampleproject.commons.utils.ReflectHelper;
import com.younchen.younsampleproject.sys.notification.controller.ChatEntranceController;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.
 */

public class ChatNotificationUtils {


    /**
     * 步骤详见 NotificationCompat.Action  的代码
     * @param rawNotification
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static NotificationCompat.Action getActions(Notification rawNotification) {
        Object notificationCompatImpl = ReflectHelper.getFieldValue(NotificationCompat.class, "IMPL");
        Bundle extras = rawNotification.extras;
        ChatEntranceController.getInstance().saveEntranceIfNotExist(extras);
        Bundle wearableBundle = extras != null ? extras.getBundle("android.wearable.EXTENSIONS") : null;
        if (wearableBundle != null) {
            Object[] actions = (Object[]) ReflectHelper.invokeMethodStrictly(notificationCompatImpl, "getActionsFromParcelableArrayList", new Class[]{ArrayList.class}, new Object[]{wearableBundle.getParcelableArrayList("actions")});
            if (actions != null) {
                for (int i = 0; i < actions.length; i++) {
                    NotificationCompat.Action action = (NotificationCompat.Action) actions[i];
                    if ((action != null) && (action.getRemoteInputs() != null) && (action.getRemoteInputs().length > 0))
                        return action;
                }
            }
        }
        return null;
    }




}
