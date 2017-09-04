package com.younchen.younsampleproject.sys.process.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.younchen.younsampleproject.sys.process.data.ProcessData;

/**
 * Created by yinlongquan on 2017/8/18.
 */

public class ProcessViewModel extends ViewModel {

    private Context mContext;
    private ProcessData mProcessData;


    public ProcessViewModel(Context context) {
        this.mContext = context;
        mProcessData = new ProcessData(context);
    }

    public ProcessData getData() {
        return mProcessData;
    }

}
