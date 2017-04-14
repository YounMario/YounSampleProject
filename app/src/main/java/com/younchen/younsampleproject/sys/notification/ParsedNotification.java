package com.younchen.younsampleproject.sys.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


import com.younchen.younsampleproject.commons.utils.ReflectHelper;
import com.younchen.younsampleproject.commons.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ParsedNotification {
    public static final String TAG = "Z.TAG.Notification";
    public static Class<?> sClassSetOnClickPendingIntent = null;
    public static Class<?> sClassReflectionAction = null;
    private static Class<?> sClassBitmapReflectionAction = null;

    static {
        try {
            sClassReflectionAction = Class.forName("android.widget.RemoteViews$ReflectionAction");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            sClassSetOnClickPendingIntent = Class.forName("android.widget.RemoteViews$SetOnClickPendingIntent");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            sClassBitmapReflectionAction = Class.forName("android.widget.RemoteViews$BitmapReflectionAction");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Notification mRawNotifiction;
    private final List<ParsedPendingIntent> mNotificationActions = new ArrayList<ParsedPendingIntent>();
    private boolean mHasReplayAction;

    private final Map<Integer, ParsedPendingIntent> mContentViewActions = new LinkedHashMap<Integer, ParsedPendingIntent>();
    private final Map<Integer, ParsedPendingIntent> mBigContentViewActions = new LinkedHashMap<Integer, ParsedPendingIntent>();
    private final Map<Integer, String> mContentViewTexts = new LinkedHashMap<Integer, String>();
    private final Map<Integer, String> mBigContentViewTexts = new LinkedHashMap<Integer, String>();
    private final Map<Integer, Bitmap> mContentViewBitmaps = new LinkedHashMap<Integer, Bitmap>();
    private final Map<Integer, Bitmap> mBigContentViewBitmaps = new LinkedHashMap<Integer, Bitmap>();
    /** intent对应的view的ID */
    private final Map<ParsedPendingIntent, Integer> mActionIdMap = new HashMap<ParsedPendingIntent, Integer>();
    private String mTickerText = "";
    private String mGroup = "";
    private int mFlags = 0;
    private String mCategory = "";
    private boolean mIsProgressBar = false;
    private ParsedPendingIntent mContentIntent = null;
    private ParsedPendingIntent mDeleteIntent = null;
    private ParsedPendingIntent mFullScreenIntent = null;
    private Bundle mExtras = null;

    public ParsedNotification(Notification notification) {
        super();
        if (notification == null) {
            mRawNotifiction = null;
            return;
        }
        mRawNotifiction = notification;

        mFlags = notification.flags;
        mCategory = NotificationCompat.getCategory(notification);
        mGroup = NotificationCompat.getGroup(notification);
        mExtras = NotificationCompat.getExtras(notification);

        if (notification.tickerText != null) {
            mTickerText = notification.tickerText.toString();
        }

        if (notification.contentIntent != null) {
            mContentIntent = new ParsedPendingIntent(notification.contentIntent);
        }
        if (notification.deleteIntent != null) {
            mDeleteIntent = new ParsedPendingIntent(notification.deleteIntent);
        }
        if (notification.fullScreenIntent != null) {
            mFullScreenIntent = new ParsedPendingIntent(notification.fullScreenIntent);
        }

        // 首先获取notification.actions
        int n_Count = NotificationCompat.getActionCount(notification);
        if (n_Count > 0) {
            for (int i = 0; i < n_Count; ++i) {
                NotificationCompat.Action action = NotificationCompat.getAction(notification, i);
                mNotificationActions.add(new ParsedPendingIntent(action));
            }
        }

        mHasReplayAction = false;

        // 其次获取WearableExtender.actions
        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender(notification);
        if (extender.getActions() != null) {
            List<NotificationCompat.Action> actions = extender.getActions();
            if (actions != null) {
                for (NotificationCompat.Action itAction : actions) {
                    if (itAction.getRemoteInputs() != null && itAction.getRemoteInputs().length > 0) {
                        mHasReplayAction = true;
                    }
                }
            }
        }

        // 解析notification.contentView
        if (notification.contentView != null) {
            parseRemoteViews(notification.contentView, true, mContentViewTexts,
                    mContentViewBitmaps, mContentViewActions, mActionIdMap);
        }

        // 解析notification.bigContentView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 4.1以上才有bigContentView
            if (notification.bigContentView != null) {
                parseRemoteViews(notification.bigContentView, false, mBigContentViewTexts,
                        mBigContentViewBitmaps, mBigContentViewActions, mActionIdMap);
            }
        }
    }

    public final Notification getRawNotification() {
        return mRawNotifiction;
    }

    public final Bundle getExtras() {
        return mExtras;
    }

    public final String getGroup() {
        return mGroup;
    }

    public final String getTickerText() {
        return mTickerText;
    }

    public final ParsedPendingIntent getContentIntent() {
        return mContentIntent;
    }

    public final ParsedPendingIntent getDeleteIntent() {
        return mDeleteIntent;
    }

    public final ParsedPendingIntent getFullScreenIntent() {
        return mFullScreenIntent;
    }

    public int getFlags() {
        return mFlags;
    }

    public String getCategory() {
        return mCategory;
    }

    private void parseRemoteViews(RemoteViews remoteViews, boolean bSetIsProgressBar,
                                  Map<Integer, String> mTexts,
                                  Map<Integer, Bitmap> mBitmaps,
                                  Map<Integer, ParsedPendingIntent> mActions,
                                  Map<ParsedPendingIntent, Integer> mActionIds) {
        if (remoteViews == null) {
            return;
        }

        Object actions = ReflectHelper.getFieldValue(remoteViews, "mActions");
        if (actions instanceof List) {
            for (Object action : (List) actions) {
                if (action.getClass() == sClassReflectionAction) {  // 即使系统里没有这个类也不会出错
                    // 获取方法名的字符串
                    String methodName = StringUtils.nullStrToEmpty(ReflectHelper.getFieldValue(action, "methodName"));
                    if ("setText".equals(methodName)) {
                        Object viewId = ReflectHelper.getFieldValue(action, "viewId");
                        if (viewId instanceof Integer) {
                            mTexts.put((Integer) viewId, StringUtils.nullStrToEmpty(ReflectHelper.getFieldValue(action, "value")));
                        }
                    }
                    if (bSetIsProgressBar) {
                        // 检查是否是进度条
                        if ("setProgress".equals(methodName)) {
                            mIsProgressBar = true;
                        }
                    }
                }
                if (action.getClass() == sClassSetOnClickPendingIntent) {   // 即使系统里没有这个类也不会出错
                    // 获取PendingIntent
                    Object obj = ReflectHelper.getFieldValue(action, "pendingIntent");
                    if (obj instanceof PendingIntent) {
                        Object viewId = ReflectHelper.getFieldValue(action, "viewId");
                        if (viewId instanceof Integer) {
                            final ParsedPendingIntent parsedPendingIntent = new ParsedPendingIntent((PendingIntent) obj);
                            mActions.put((Integer) viewId, parsedPendingIntent);
                            mActionIds.put(parsedPendingIntent, (Integer) viewId);
                        }
                    }
                }
                if (action.getClass() == sClassBitmapReflectionAction) {    // 即使系统里没有这个类也不会出错
                    // 获取方法名的字符串
                    String methodName = StringUtils.nullStrToEmpty(ReflectHelper.getFieldValue(action, "methodName"));
                    if ("setImageBitmap".equals(methodName)) {
                        Object viewId = ReflectHelper.getFieldValue(action, "viewId");
                        if (viewId instanceof Integer) {
                            Object bitmap = ReflectHelper.getFieldValue(action, "bitmap");
                            if (bitmap instanceof Bitmap) {
                                mBitmaps.put((Integer) viewId, (Bitmap) bitmap);
                            }
                        }
                    }
                }
            }
        }
    }

    public final List<ParsedPendingIntent> getNotificationActions() {
        return mNotificationActions;
    }

	//是否带有回复入口
    public final boolean hasReplyAction() {
        return mHasReplayAction;
    }

    public final Map<Integer, ParsedPendingIntent> getContentViewActions() {
        return mContentViewActions;
    }

    public final Map<Integer, ParsedPendingIntent> getBigContentViewActions() {
        return mBigContentViewActions;
    }

    public final Map<Integer, Bitmap> getContentViewBitmaps() {
        return mContentViewBitmaps;
    }

    public final Map<Integer, Bitmap> getBigContentViewBitmaps() {
        return mBigContentViewBitmaps;
    }

    public final Map<Integer, String> getContentViewTexts() {
        return mContentViewTexts;
    }

    public final Map<Integer, String> getBigContentViewTexts() {
        return mBigContentViewTexts;
    }

    public final Map<ParsedPendingIntent, Integer> getActionIdMap() {
        return mActionIdMap;
    }

    public final boolean isProgressBar() {
        return mIsProgressBar;
    }
}
