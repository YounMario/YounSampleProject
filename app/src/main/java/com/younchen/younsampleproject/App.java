package com.younchen.younsampleproject;


import android.support.multidex.MultiDexApplication;

/**
 * Created by Administrator on 2017/4/11.
 */

public class App extends MultiDexApplication {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }
}
