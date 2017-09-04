package com.younchen.younsampleproject.sys.process.data;

import android.app.ActivityManager;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v4.app.ActivityManagerCompat;

import com.younchen.younsampleproject.sys.process.bean.ProcessItem;

import java.util.List;

/**
 * Created by yinlongquan on 2017/8/18.
 */

public class ProcessData extends LiveData<List<ProcessItem>> {

    private Context mContext;
    //RTApiClient ???

    public ProcessData(Context context) {
        mContext = context;
    }

    @Override
    protected void onActive() {

    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
