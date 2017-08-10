package com.younchen.younsampleproject.architecture.lifecycles.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.younchen.younsampleproject.architecture.lifecycles.bean.LocationLiveData;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class LocationViewModel extends ViewModel {

    private LocationLiveData locationLiveData;

    public LocationViewModel(Context context) {
        locationLiveData = new LocationLiveData(context);
    }

    //once owner activity is finished .the framework will call
    //this to release resources
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LocationLiveData getLocationData() {
        return locationLiveData;
    }
}
