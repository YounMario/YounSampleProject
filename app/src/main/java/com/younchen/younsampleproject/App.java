package com.younchen.younsampleproject;


import android.support.multidex.MultiDexApplication;

import com.younchen.younsampleproject.commons.utils.HookUtils;

/**
 * Created by Administrator on 2017/4/11.
 */

public class App extends MultiDexApplication {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        HookUtils.init();
    }

    public static App getInstance() {
        return mInstance;
    }
}
