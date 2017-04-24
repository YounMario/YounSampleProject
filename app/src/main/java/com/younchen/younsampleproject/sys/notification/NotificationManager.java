package com.younchen.younsampleproject.sys.notification;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.younchen.younsampleproject.commons.utils.ReflectHelper;
import com.younchen.younsampleproject.sys.notification.model.ChatNotification;
import com.younchen.younsampleproject.sys.notification.model.NotificationAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NotificationManager {
    private static final String TAG = "russell";
    private static NotificationManager sInstance;
    private Context mContext;

    public static final String from(Bundle paramBundle) {
        return from(paramBundle, false);
    }

    public static final String from(Bundle paramBundle, boolean paramBoolean) {
        if (paramBundle == null)
            return null;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Bundle(");
        localStringBuilder.append(paramBundle.hashCode());
        localStringBuilder.append("): ");
        Iterator localIterator = paramBundle.keySet().iterator();
        while (localIterator.hasNext()) {
            String str = (String) localIterator.next();
            Object localObject = paramBundle.get(str);
            if (!paramBoolean)
                localStringBuilder.append("\n");
            localStringBuilder.append("[");
            localStringBuilder.append(str);
            if (localObject != null) {
                localStringBuilder.append("(");
                localStringBuilder.append(localObject.getClass().getSimpleName());
                localStringBuilder.append(")");
            }
            localStringBuilder.append(" = ");
            if ((localObject instanceof Bundle)) {
                localStringBuilder.append(from((Bundle) localObject));
                continue;
            }
            localStringBuilder.append(localObject);
            localStringBuilder.append("] ");
        }
        return localStringBuilder.toString();
    }

    private String getActionsFromBundle(Bundle paramBundle) {
        if (paramBundle == null)
            return null;
        return getActionsStr((List) paramBundle.get("actions"));
    }

    private List<Action> getActionsFromNotification(Context paramContext, Notification paramNotification, String paramString) {
        List<Action> actionList = new ArrayList();
        try {
            ;
            if (paramString == null)
                return actionList;

            Notification.Action[] actions = (Notification.Action[]) ReflectHelper.getFieldValue(Notification.class, "actions");
            Object NotificationAction;
            int j;
            int i;
            if (actions != null) {
                NotificationAction = Class.forName("android.app.Notification$Action");
                j = actions.length;
                i = 0;
                while (i < j) {
                    Action localAction = new Action();
                    localAction.icon = ((Class) NotificationAction).getDeclaredField("icon").getInt(actions[i]);
                    localAction.title = ((String) ((Class) NotificationAction).getDeclaredField("title").get(actions[i]));
                    localAction.actionIntent = ((PendingIntent) ((Class) NotificationAction).getDeclaredField("actionIntent").get(actions[i]));
                    actionList.add(localAction);
                    i += 1;
                }
            }
            Iterator<NotificationCompat.Action> actionIterator = getWearableOptions(paramNotification).getActions().iterator();
            while (actionIterator.hasNext()) {
                NotificationCompat.Action action = actionIterator.next();
                Action localAction = new Action();
                localAction.icon = action.icon;
                localAction.actionIntent = action.actionIntent;
                localAction.title = action.title;
                localAction.remoteInputs = action.getRemoteInputs();
                actionList.add(localAction);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return actionList;
    }

    private String getActionsStr(List<?> paramList) {
        if (paramList == null)
            return null;
        StringBuilder localStringBuilder = new StringBuilder();
        for (Object action : paramList) {
            if ((action instanceof Notification.Action)) {
                Notification.Action nAction = (Notification.Action) action;
                localStringBuilder.append(nAction);
                localStringBuilder.append("\n\n");
            }
        }
        return localStringBuilder.toString();
    }

    private Bundle[] getBundleArrayFromBundle(Bundle paramBundle, String paramString) {
        Bundle[] bundles = null;
        Parcelable[] parcelable = paramBundle.getParcelableArray(paramString);
        if (!(parcelable instanceof Bundle[])) {
            if (parcelable != null) {
                bundles = Arrays.copyOf(parcelable, parcelable.length, Bundle[].class);
            }
        }
        return bundles;
    }

    public static NotificationManager getInstance() {
        if (sInstance == null)
            sInstance = new NotificationManager();
        NotificationManager localNotificationManager = sInstance;
        return localNotificationManager;
    }


    private String getPrivacy(Context paramContext, String paramString) {
        PreferenceManager.getDefaultSharedPreferences(paramContext);
        return "none";
    }

    private NotificationCompat.Action getReplyAction(List<NotificationCompat.Action> paramList) {
        if ((paramList == null) || (paramList.isEmpty()))
            return null;
        Iterator<NotificationCompat.Action> iterator = paramList.iterator();
        while (iterator.hasNext()) {
            NotificationCompat.Action localAction = iterator.next();
            if ((localAction != null) && (localAction.getRemoteInputs() != null) && (localAction.getRemoteInputs().length > 0))
                return localAction;
        }
        return null;
    }


    private void log(String paramString) {
        Log.d("russell", paramString);
    }

    private final String mapToString(Map paramMap) {
        if ((paramMap == null) || (paramMap.isEmpty()))
            return null;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("SIZE:" + paramMap.size());
        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry localEntry = (Map.Entry) iterator.next();
            localStringBuilder.append("\n");
            localStringBuilder.append(String.valueOf(localEntry.getKey()));
            localStringBuilder.append(":");
            localStringBuilder.append(String.valueOf(localEntry.getValue()));
        }
        return localStringBuilder.toString();
    }

    private android.support.v4.app.RemoteInput parseLegacyRemoteInputBundle(Bundle paramBundle) {
        return new RemoteInput.Builder(paramBundle.getString("return_key")).setLabel(paramBundle.getString("label")).setChoices(paramBundle.getStringArray("choices")).setAllowFreeFormInput(paramBundle.getBoolean("allowFreeFormInput")).build();
    }

    private android.support.v4.app.RemoteInput[] parseLegacyRemoteInputBundles(Bundle[] paramArrayOfBundle) {
        android.support.v4.app.RemoteInput[] arrayOfRemoteInput = new android.support.v4.app.RemoteInput[paramArrayOfBundle.length];
        int i = 0;
        while (i < paramArrayOfBundle.length) {
            arrayOfRemoteInput[i] = parseLegacyRemoteInputBundle(paramArrayOfBundle[i]);
            i += 1;
        }
        return arrayOfRemoteInput;
    }

    private NotificationCompat.Action parseLegacyWearableAction(Bundle paramBundle) {
        int icon = paramBundle.getInt("icon");
        CharSequence title = paramBundle.getCharSequence("title");
        Parcelable localParcelable = paramBundle.getParcelable("action_intent");
        paramBundle = paramBundle.getParcelable("extras");
        NotificationCompat.Action.Builder notificationBuilder = new NotificationCompat.Action.Builder(icon, title, (PendingIntent) localParcelable);
        if (paramBundle != null) {
            notificationBuilder.addExtras(paramBundle);
            Bundle[] bundles = getBundleArrayFromBundle(paramBundle, "android.support.wearable.remoteInputs");
            RemoteInput[] input = parseLegacyRemoteInputBundles(bundles);
            if (input != null) {
                int i = 0;
                while (i < input.length) {
                    notificationBuilder.addRemoteInput(input[i]);
                    i += 1;
                }
            }
        }
        return notificationBuilder.build();
    }


    private boolean shouldIgnore(String paramString) {
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        KeyguardManager localKeyguardManager = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        return ((localSharedPreferences.getBoolean("collectonunlock", true)) || (localKeyguardManager.inKeyguardRestrictedInputMode()) || (localSharedPreferences.getBoolean("widgetlocker", false))) && (!localSharedPreferences.getBoolean(paramString + "." + "ignoreapp", false)) && (!paramString.equals("com.android.providers.downloads"));
    }

    private String toString(Object[] paramArrayOfObject) {
        if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
            return "null";
        return String.valueOf(Arrays.asList(paramArrayOfObject));
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public ChatNotification extractWearNotification(Notification paramNotification, String packageName, String tag, NotificationListenerService notificationListenerService, int id) {
        log("\n\n\n\n\n\n\nextractWearNotification\n");
        ChatNotification chatNotification = new ChatNotification();
        chatNotification.rawNotification = paramNotification;
        List<NotificationCompat.Action> localList = new NotificationCompat.WearableExtender(paramNotification).getActions();
        NotificationAction localNotificationAction = new NotificationAction();
        localNotificationAction.tag = tag;
        localNotificationAction.packageName = packageName;
        localNotificationAction.mIntent = paramNotification.contentIntent;
        NotificationCompat.Action replyAction = getReplyAction(localList);
        if (replyAction != null) {
            localNotificationAction.remoteInputs = replyAction.getRemoteInputs();
            localNotificationAction.mIntent = replyAction.getActionIntent();
        }
        chatNotification.notificationAction = localNotificationAction;
        ParsedNotification parsedNotification = new ParsedNotification(paramNotification);
        if (paramNotification.actions != null) {
            printNotificationLog(paramNotification, packageName, id, tag, notificationListenerService, localList, localNotificationAction, parsedNotification);
        }
        chatNotification.parsedNotification = parsedNotification;
        return chatNotification;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @NonNull
    private NotificationAction printNotificationLog(Notification paramNotification, String packageName, int id, String tag, NotificationListenerService notificationListenerService, List<NotificationCompat.Action> localList, NotificationAction localNotificationAction, ParsedNotification localObject) {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(String.format("\n\nPKG:\n      %s", new Object[]{packageName}));
        localStringBuilder.append(String.format("\n\nNotificationID:\n      %s", new Object[]{String.valueOf(id)}));
        localStringBuilder.append(String.format("\n\nTAG:\n%s", new Object[]{tag}));
        Notification.Action[] actions = paramNotification.actions;
        for (int i = 0; i < actions.length; i++) {
            Notification.Action action = actions[i];
            localStringBuilder.append(String.format("\n\nACTION:\n%s", new Object[]{actions}));
            localStringBuilder.append(String.format("\n\nEXTRA:\n%s", new Object[]{from(paramNotification.extras)}));
            localStringBuilder.append(String.format("\n\nACTION IN EXTRA:\n%s", new Object[]{getActionsFromBundle(paramNotification.extras)}));
            localStringBuilder.append(String.format("\n\nREMOTE INPUTS::\n%s", new Object[]{getActionsStr(localList)}));
            localStringBuilder.append(String.format("\n\nFLAG:\"0x%s\"", new Object[]{Integer.toString(localObject.getFlags(), 16)}));
            localStringBuilder.append(String.format("\n\nContent  TEXT: %s", new Object[]{mapToString(localObject.getContentViewTexts())}));
            localStringBuilder.append(String.format("\n\nBig Content  TEXT: %s", new Object[]{mapToString(localObject.getBigContentViewTexts())}));
            localStringBuilder.append(String.format("\n\nTicker TEXT: %s", new Object[]{localObject.getTickerText()}));
            localStringBuilder.append(String.format("\n\nWho am I: %s", new Object[]{notificationListenerService.getClass().getSuperclass().getSimpleName()}));
            localStringBuilder.append("\n\n");
            localNotificationAction.title = localObject.getContentViewTexts().toString();
            localNotificationAction.extraStr = localStringBuilder.toString();
            log("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            log("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            log(localNotificationAction.extraStr.trim());
            log("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
            log("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
            log("\n\n");
        }
        return localNotificationAction;
    }

    public NotificationCompat.WearableExtender getWearableOptions(Notification paramNotification) {
        NotificationCompat.WearableExtender localWearableExtender = new NotificationCompat.WearableExtender(paramNotification);
        Log.e("russell", "showWearableExtender from NotificationResponse");
        Bundle params = NotificationCompat.getExtras(paramNotification);
        if (params != null) {
            Parcelable[] actions = params.getParcelableArray("android.support.wearable.actions");
            if ((actions != null) && (actions.length > 0)) {
                localWearableExtender.clearActions();
                int i = 0;
                while (i < actions.length) {
                    localWearableExtender.addAction(parseLegacyWearableAction((Bundle) actions[i]));
                    i += 1;
                }
            }
        }
        return localWearableExtender;
    }

    public void init(Context paramContext) {
        this.mContext = paramContext;
    }


    private class Action {
        public PendingIntent actionIntent;
        public Bitmap drawable;
        public int icon;
        public android.support.v4.app.RemoteInput[] remoteInputs;
        public CharSequence title;

        private Action() {
        }
    }
}