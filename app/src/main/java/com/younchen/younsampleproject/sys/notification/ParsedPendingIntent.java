package com.younchen.younsampleproject.sys.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.younchen.younsampleproject.commons.utils.ReflectHelper;


public final class ParsedPendingIntent {

    private static Class<?> sClassActivityManagerNative = null;
    private static Class<?> sClassActivityManagerProxy = null;
    private static Class<?> sClassIIntentSender = null;
    private static Class<?> sClassIIntentSenderStubProxy = null;

    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // 在小于4.2的系统上，因为PendingIntent类里面没有getIntent方法可以调用，所以需要用到这个类
            try {
                sClassActivityManagerNative = Class.forName("android.app.ActivityManagerNative");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                sClassActivityManagerProxy = Class.forName("android.app.ActivityManagerProxy");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                sClassIIntentSender = Class.forName("android.content.IIntentSender");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                sClassIIntentSenderStubProxy = Class.forName("android.content.IIntentSender$Stub$Proxy");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private PendingIntent mPendingIntent = null;
    private Intent mIntent = null;
    private String mDescription;
    private NotificationCompat.Action mAction = null;

    public ParsedPendingIntent(PendingIntent pi) {
        super();
        init(pi, null);
    }

    public ParsedPendingIntent(PendingIntent pi, String title) {
        super();
        init(pi, title);
    }

    public ParsedPendingIntent(NotificationCompat.Action action) {
        super();
        PendingIntent pi = null;
        CharSequence title = null;
        if (action != null) {
            pi = action.actionIntent;
            title = action.title;
        }
        mAction = action;
        init(pi, title == null ? "" : title.toString());
    }

    private static Intent reflectGetIntent(PendingIntent pi) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Object obj = ReflectHelper.invokeMethod(pi, "getIntent", null, null);
            if (obj instanceof Intent) {
                return (Intent) obj;
            }
        } else {
            if (sClassActivityManagerNative != null && sClassIIntentSender != null
                    && sClassIIntentSenderStubProxy != null && sClassActivityManagerProxy != null) {
                Object mTarget = ReflectHelper.getFieldValue(pi, "mTarget");
                if (mTarget != null && mTarget.getClass() == sClassIIntentSenderStubProxy) {
                    Object activityManager = ReflectHelper.invokeStaticMethod(sClassActivityManagerNative, "getDefault", null, null);
                    if (activityManager != null && activityManager.getClass() == sClassActivityManagerProxy) {
                        Object obj = ReflectHelper.invokeMethod(activityManager, "getIntentForIntentSender",
                                new Class[]{sClassIIntentSender,},
                                new Object[]{mTarget,});
                        if (obj instanceof Intent) {
                            return (Intent) obj;
                        }
                    }
                }
            }
        }
        return null;
    }

    public final String getDescription() {
        return mDescription;
    }

    public final NotificationCompat.Action getNotificationAction() {
        return mAction;
    }

    private void init(PendingIntent pi, String title) {
        if (pi == null) {
            return;
        }
        if (title == null) {
            title = "";
        }

        mPendingIntent = pi;
        mIntent = reflectGetIntent(pi);
        mDescription = title;
    }

    public final Intent getIntent() {
        return mIntent;
    }

    public final String getAction() {
        String action = null;
        if (mIntent != null) {
            action = mIntent.getAction();
        }
        if (action == null) {
            action = "";
        }
        return action;
    }

    public final String getTargetPackage() {
        if (mIntent != null && mIntent.getComponent() != null) {
            return mIntent.getComponent().getPackageName();
        }
        return "";
    }

    public final String getTargetClass() {
        if (mIntent != null && mIntent.getComponent() != null) {
            return mIntent.getComponent().getClassName();
        }
        return "";
    }

    public final void send() throws PendingIntent.CanceledException {
        mPendingIntent.send();
    }

    public final void trySendActivity(Context context, String packageName) {
//        if (context == null){
//            context = App.getInstance();
//        }
//        if (context != null && mPendingIntent != null) {
//            try {
//                mPendingIntent.send();
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//                if (mIntent != null) {
//                    if (!KCommons.startActivity(context, mIntent)) {
//                        // 尝试打开App的主界面
//                        ComponentName componentName = mIntent.getComponent();
//                        if (componentName != null) {
//                            Intent intent = PackageUtil.getAppIntentWithPackageName(context, componentName.getPackageName());
//                            if (intent != null) {
//                                // 如果这里也打开失败，那么就无解了
//                                if (!KCommons.startActivity(context, intent)){
//                                    OpLog.toFile("ParsedPendingIntent","trySendActivity faild 1 -> componetName = " + componentName);
//                                }
//                            }
//                        } else {
//                            if (packageName != null) {
//                                Intent intent = PackageUtil.getAppIntentWithPackageName(context, packageName);
//                                if (intent != null) {
//                                    // 如果这里也打开失败，那么就无解了
//                                    if (!KCommons.startActivity(context, intent)){
//                                        OpLog.toFile("ParsedPendingIntent","trySendActivity faild 2 ->  packageName = " + packageName);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            OpLog.toFile("ParsedPendingIntent","trySendActivity faild 3 -> context = " + context + ", mPendingIntent = " + mPendingIntent);
//        }
    }

    public final void trySendService(Context context) {
        if (context != null && mPendingIntent != null) {
            try {
                mPendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
                if (mIntent != null) {
                    try {
                        context.startService(mIntent);
                    } catch (RuntimeException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public final void trySendBroadcast(Context context) {
        if (context != null && mPendingIntent != null) {
            try {
                mPendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
                if (mIntent != null) {
                    try {
                        context.sendBroadcast(mIntent);
                    } catch (RuntimeException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
