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
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.younchen.younsampleproject.sys.notification.model.NotificationAction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

//    private List<Action> getActionsFromNotification(Context paramContext, Notification paramNotification, String paramString) {
//        List<Action> actionList = new ArrayList();
//        try {
//            Field actions = Notification.class.getDeclaredField("actions");
//            if (paramString == null)
//                return actionList;
//            actions.setAccessible(true);
//            paramString = (Notification.Action[]) (Notification.Action[]) actions.get(paramNotification);
//            Object localObject1;
//            int j;
//            int i;
//            Object localObject2;
//            if (paramString != null) {
//                localObject1 = Class.forName("android.app.Notification$Action");
//                j = paramString.length;
//                i = 0;
//                while (i < j) {
//                    localObject2 = paramString[i];
//                    Action localAction = new Action(null);
//                    localAction.icon = ((Class) localObject1).getDeclaredField("icon").getInt(localObject2);
//                    localAction.title = ((String) ((Class) localObject1).getDeclaredField("title").get(localObject2));
//                    localAction.actionIntent = ((PendingIntent) ((Class) localObject1).getDeclaredField("actionIntent").get(localObject2));
//                    paramContext.add(localAction);
//                    i += 1;
//                }
//            }
//            paramNotification = getWearableOptions(paramNotification).getActions().iterator();
//            while (paramNotification.hasNext()) {
//                localObject1 = (NotificationCompat.Action) paramNotification.next();
//                paramString = new Action(null);
//                paramString.icon = ((NotificationCompat.Action) localObject1).icon;
//                paramString.actionIntent = ((NotificationCompat.Action) localObject1).actionIntent;
//                paramString.title = ((NotificationCompat.Action) localObject1).title;
//                paramString.remoteInputs = ((NotificationCompat.Action) localObject1).getRemoteInputs();
//                if (paramString.remoteInputs != null) {
//                    localObject1 = paramString.remoteInputs;
//                    j = localObject1.length;
//                    i = 0;
//                    while (i < j) {
//                        localObject2 = localObject1[i];
//                        Log.e("russell", "input: " + localObject2);
//                        i += 1;
//                    }
//                }
//                paramContext.add(paramString);
//            }
//        } catch (Exception paramNotification) {
//            paramNotification.printStackTrace();
//        }
//        return (List<Action>) paramContext;
//    }

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

//    private Bundle[] getBundleArrayFromBundle(Bundle paramBundle, String paramString) {
//        Object localObject2 = null;
//        Parcelable[] arrayOfParcelable = paramBundle.getParcelableArray(paramString);
//        Object localObject1 = localObject2;
//        if (!(arrayOfParcelable instanceof Bundle[])) {
//            localObject1 = localObject2;
//            if (arrayOfParcelable != null) {
//                localObject1 = Arrays.copyOf((Object[]) arrayOfParcelable, arrayOfParcelable.length,[Landroid.os.Bundle.class)
//                ;
//                paramBundle.putParcelableArray(paramString, (Parcelable[]) (Parcelable[]) localObject1);
//            }
//        }
//        return (Bundle) (Bundle[]) (Bundle[]) localObject1;
//    }

    public static NotificationManager getInstance() {
        if (sInstance == null)
            sInstance = new NotificationManager();
        NotificationManager localNotificationManager = sInstance;
        return localNotificationManager;
    }

//    private void getMultipleNotificationsFromInboxView(RemoteViews paramRemoteViews, Object paramObject) {
//        getNotificationStringFromRemoteViews(paramRemoteViews);
//    }

//    private HashMap getNotificationStringFromRemoteViews(RemoteViews paramRemoteViews) {
//        HashMap localHashMap = new HashMap();
//        Object localObject1 = null;
//        do
//            try {
//                Object localObject2 = RemoteViews.class.getDeclaredField("mActions");
//                if (localObject2 == null)
//                    continue;
//                ((Field) localObject2).setAccessible(true);
//                localObject1 = ((Field) localObject2).get(paramRemoteViews);
//                continue;
//                paramRemoteViews = ((ArrayList) localObject1).iterator();
//                while (paramRemoteViews.hasNext()) {
//                    localObject2 = paramRemoteViews.next();
//                    localObject1 = Parcel.obtain();
//                    ((Parcelable) localObject2).writeToParcel((Parcel) localObject1, 0);
//                    ((Parcel) localObject1).setDataPosition(0);
//                    if (((Parcel) localObject1).readInt() != 2)
//                        continue;
//                    int i = ((Parcel) localObject1).readInt();
//                    localObject2 = ((Parcel) localObject1).readString();
//                    if (localObject2 == null)
//                        continue;
//                    if (((String) localObject2).equals("setText")) {
//                        ((Parcel) localObject1).readInt();
//                        localHashMap.put(Integer.valueOf(i), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel) localObject1));
//                    }
//                    ((Parcel) localObject1).recycle();
//                }
//            } catch (Exception paramRemoteViews) {
//                paramRemoteViews.printStackTrace();
//                return localHashMap;
//            }
//        while (localObject1 != null);
//        return (HashMap) (HashMap) localHashMap;
//    }

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

//    private boolean isPersistent(Notification paramNotification, String paramString) {
//        int i;
//        if (((paramNotification.flags & 0x20) == 32) || ((paramNotification.flags & 0x2) == 2)) {
//            i = 1;
//            if (i == 0) {
//                paramNotification = PreferenceManager.getDefaultSharedPreferences(this.mContext);
//                if (paramNotification.getBoolean(paramString + ".showpersistent", false))
//                    break label73;
//            }
//        }
//        label73:
//        do {
//            return false;
//            i = 0;
//            break;
//        }
//        while (paramNotification.getBoolean(paramString + ".catch_all_notifications", true));
//        return false;
//    }

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

//    private NotificationCompat.Action parseLegacyWearableAction(Bundle paramBundle) {
//        int i = paramBundle.getInt("icon");
//        Object localObject = paramBundle.getCharSequence("title");
//        Parcelable localParcelable = paramBundle.getParcelable("action_intent");
//        paramBundle = paramBundle.getParcelable("extras");
//        localObject = new NotificationCompat.Action.Builder(i, (CharSequence) localObject, (PendingIntent) localParcelable);
//        if (paramBundle != null) {
//            ((NotificationCompat.Action.Builder) localObject).addExtras((Bundle) paramBundle);
//            paramBundle = parseLegacyRemoteInputBundles(getBundleArrayFromBundle((Bundle) paramBundle, "android.support.wearable.remoteInputs"));
//            if (paramBundle != null) {
//                i = 0;
//                while (i < paramBundle.length) {
//                    ((NotificationCompat.Action.Builder) localObject).addRemoteInput(paramBundle[i]);
//                    i += 1;
//                }
//            }
//        }
//        return (NotificationCompat.Action) ((NotificationCompat.Action.Builder) localObject).build();
//    }

//    private void parseNotification(Notification paramNotification, String paramString1, int paramInt, String paramString2, String paramString3, boolean paramBoolean) {
//        if ((paramNotification != null) && (!isPersistent(paramNotification, paramString1)) && (!shouldIgnore(paramString1))) {
//            paramString2 = getPrivacy(this.mContext, paramString1);
//            if ((Build.VERSION.SDK_INT >= 16) && (paramString2.equals("none")))
//                getActionsFromNotification(this.mContext, paramNotification, paramString1);
//        }
//    }

    private boolean shouldIgnore(String paramString) {
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        KeyguardManager localKeyguardManager = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        return ((localSharedPreferences.getBoolean("collectonunlock", true)) || (localKeyguardManager.inKeyguardRestrictedInputMode()) || (localSharedPreferences.getBoolean("widgetlocker", false))) && (!localSharedPreferences.getBoolean(paramString + "." + "ignoreapp", false)) && (!paramString.equals("com.android.providers.downloads"));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private String toString(Notification.Action paramAction) {
        if (paramAction == null)
            return "[INVALID ACTION]";
        StringBuilder localStringBuilder = new StringBuilder("{\n");
        localStringBuilder.append(paramAction.title);
        localStringBuilder.append("\n\nREMOTE_INPUT:\n");
        try {
            android.app.RemoteInput[] arrayOfRemoteInput = paramAction.getRemoteInputs();
            if ((arrayOfRemoteInput == null) || (arrayOfRemoteInput.length == 0))
                localStringBuilder.append("null");
            while (true) {
                localStringBuilder.append("\n\nEXTRA:\n" + from(paramAction.getExtras()));
                localStringBuilder.append("\n}");
                int j = arrayOfRemoteInput.length;
                int i = 0;
                while (i < j) {
                    android.app.RemoteInput localRemoteInput = arrayOfRemoteInput[i];
                    localStringBuilder.append(String.format("label:%s  key:%s  choices:%s;", new Object[]{localRemoteInput.getLabel(), localRemoteInput.getResultKey(), toString(localRemoteInput.getChoices())}));
                    i += 1;
                }
            }
        } catch (NoSuchMethodError ex) {
            ex.printStackTrace();
        }
        return localStringBuilder.toString();
    }

//    private String toString(NotificationCompat.Action paramAction) {
//        if (paramAction == null)
//            return "[INVALID ACTION]";
//        StringBuilder localStringBuilder = new StringBuilder("{V4");
//        localStringBuilder.append("\n\nTITLE:" + paramAction.getTitle());
//        localStringBuilder.append("\n\nREMOTE_INPUT:\n");
//        android.support.v4.app.RemoteInput[] arrayOfRemoteInput = paramAction.getRemoteInputs();
//        if ((arrayOfRemoteInput == null) || (arrayOfRemoteInput.length == 0))
//            localStringBuilder.append("null ");
//        while (true) {
//            localStringBuilder.append("\n\nEXTRA:\n" + from(paramAction.getExtras()));
//            localStringBuilder.append("\n}");
//            return localStringBuilder.toString();
//            int j = arrayOfRemoteInput.length;
//            int i = 0;
//            while (i < j) {
//                android.support.v4.app.RemoteInput localRemoteInput = arrayOfRemoteInput[i];
//                localStringBuilder.append(String.format("label:%s  key:%s  choices:%s;", new Object[]{localRemoteInput.getLabel(), localRemoteInput.getResultKey(), toString(localRemoteInput.getChoices())}));
//                i += 1;
//            }
//        }
//    }

    private String toString(Object[] paramArrayOfObject) {
        if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
            return "null";
        return String.valueOf(Arrays.asList(paramArrayOfObject));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public NotificationAction extractWearNotification(Notification paramNotification, String packageName, String tag, NotificationListenerService notificationListenerService, int id) {
        log("\n\n\n\n\n\n\nextractWearNotification\n");
        List<NotificationCompat.Action> localList = new NotificationCompat.WearableExtender(paramNotification).getActions();
        NotificationAction localNotificationAction = new NotificationAction();
        localNotificationAction.tag = tag;
        localNotificationAction.packageName = packageName;
        localNotificationAction.mIntent = paramNotification.contentIntent;
        Object localObject = getReplyAction(localList);
        if (localObject != null) {
            localNotificationAction.remoteInputs.addAll(Arrays.asList(((NotificationCompat.Action) localObject).getRemoteInputs()));
            localNotificationAction.mIntent = ((NotificationCompat.Action) localObject).getActionIntent();
        }
        localObject = new ParsedNotification(paramNotification);
        if (paramNotification.actions != null) {
            printNotificationLog(paramNotification, packageName, id, tag, notificationListenerService, localList, localNotificationAction, (ParsedNotification) localObject);
        }
        return localNotificationAction;
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

//    public NotificationCompat.WearableExtender getWearableOptions(Notification paramNotification) {
//        NotificationCompat.WearableExtender localWearableExtender = new NotificationCompat.WearableExtender(paramNotification);
//        Log.e("russell", "showWearableExtender from NotificationResponse");
//        Bundle params = NotificationCompat.getExtras(paramNotification);
//        if (params != null) {
//            Parcelable[] actions = params.getParcelableArray("android.support.wearable.actions");
//            if ((actions != null) && (actions.length > 0)) {
//                localWearableExtender.clearActions();
//                int i = 0;
//                while (i < actions.length) {
//                    localWearableExtender.addAction(parseLegacyWearableAction((Bundle) actions[i]));
//                    i += 1;
//                }
//            }
//        }
//        return localWearableExtender;
//    }

    public void init(Context paramContext) {
        this.mContext = paramContext;
    }

//    public void onNotificationPosted(Notification paramNotification, String paramString1, int paramInt, String paramString2, String paramString3, boolean paramBoolean) {
//        if (!isPersistent(paramNotification, paramString1)) {
//            parseNotification(paramNotification, paramString1, paramInt, paramString2, paramString3, paramBoolean);
//            if (paramNotification.bigContentView == null)
//                break label41;
//        }
//        label41:
//        for (paramNotification = paramNotification.bigContentView; ; paramNotification = paramNotification.contentView) {
//            getMultipleNotificationsFromInboxView(paramNotification, null);
//            return;
//        }
//    }

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