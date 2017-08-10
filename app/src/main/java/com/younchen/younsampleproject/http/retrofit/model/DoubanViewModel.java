package com.younchen.younsampleproject.http.retrofit.model;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.younchen.younsampleproject.http.retrofit.data.DoubanBookData;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class DoubanViewModel extends ViewModel {

    private Context mContext;
    private DoubanBookData mDoubanBookData;

    public DoubanViewModel(Context context) {
        this.mContext = context;
        mDoubanBookData = new DoubanBookData(context);
    }

    public DoubanBookData getData() {
        return mDoubanBookData;
    }


}
