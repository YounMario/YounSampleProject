package com.younchen.younsampleproject.sys.notification.model;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.support.v4.app.RemoteInput;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/13.
 */

public class NotificationAction {
    public String bigText;
    public String extraStr;
    public String id = UUID.randomUUID().toString();
    public String listener;
    public PendingIntent mIntent;
    public String packageName = "";
    public RemoteInput[] remoteInputs;
    public String summary;
    public String tag;
    public String title;
    public Bitmap head;
}
