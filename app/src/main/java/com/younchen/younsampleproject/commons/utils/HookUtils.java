package com.younchen.younsampleproject.commons.utils;


import android.content.ComponentName;
import android.content.Intent;

import com.younchen.younsampleproject.commons.log.YLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yinlongquan on 2017/8/17.
 */

public class HookUtils {


    private static final String TAG = "HookUtils";
    private static HookUtils mHookUtils;

    private String className = "com.younchen.younsampleproject.sys.loader.contact.CleanDetailActivity";

    public static void init() {
        if (mHookUtils == null) {
            mHookUtils = new HookUtils();
        }
        mHookUtils.hookAms();
    }

    private void hookAms() {

        //一路反射，直到拿到IActivityManager的对象
        try {
            YLog.i(TAG, " hook ams");
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);
            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue);


            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");
            AmsInvocationHandler handler = new AmsInvocationHandler(iActivityManagerObject);
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);
            //现在替换掉这个对象
            mInstance.set(defaultValue, proxy);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class AmsInvocationHandler implements InvocationHandler {

        private Object iActivityManagerObject;

        private AmsInvocationHandler(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("startActivity".contains(method.getName())) {
                Intent intent = getIntent(args);
                if (ifMatchedInterceptedActivity(intent)) {
                    YLog.i(TAG, " start activity invoke");
                    replaceTargetActivityParams(args);
                    return method.invoke(iActivityManagerObject, args);
                }
            } else if ("finishActivity".contains(method.getName())) {
                Intent intent = getIntent(args);
                if (ifMatchedInterceptedActivity(intent)) {
                    YLog.i(TAG, " start activity invoke");
                }
            }
            return method.invoke(iActivityManagerObject, args);
        }
    }

    private void replaceTargetActivityParams(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Intent) {
                Intent raw = (Intent) arg;
                raw.setComponent(new ComponentName("com.younchen.younsampleproject",
                        "com.younchen.younsampleproject.NotFound404Activity"));
            }
        }
    }


    private boolean ifMatchedInterceptedActivity(Intent intent) {
        return !(intent == null || intent.getComponent() == null) && className.equals(intent.getComponent().getClassName());
    }

    private Intent getIntent(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        for (Object arg : args) {
            if (arg instanceof Intent) {
                return (Intent) arg;
            }
        }
        return null;
    }
}
