package com.younchen.younsampleproject.http.okhttp.download;

import android.content.Context;
import android.content.SharedPreferences;

import com.younchen.younsampleproject.App;

/**
 * Created by yinlongquan on 2017/10/11.
 */

public class SharedPreferenceUtils {

    private static SharedPreferenceUtils mContentManager;
    private SharedPreferences mShardPreferences;


    public static SharedPreferenceUtils getInstance() {
        if (mContentManager == null) {
            mContentManager = new SharedPreferenceUtils();
        }
        return mContentManager;
    }

    public SharedPreferenceUtils() {
        String spName = App.getInstance().getPackageName() + "_preferences";
        mShardPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mShardPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return mShardPreferences.getLong(key, 0);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mShardPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mShardPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = mShardPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }
}
