package com.younchen.younsampleproject.architecture.lifecycles;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.younchen.younsampleproject.commons.log.YLog;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class MyLocationListener implements LifecycleObserver {

    private static final String TAG = "MyLocationListener";

    public MyLocationListener(Context context, Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        YLog.i(TAG, "onStart ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        YLog.i(TAG, "onResume ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        YLog.i(TAG, "onPause ");
    }

}
