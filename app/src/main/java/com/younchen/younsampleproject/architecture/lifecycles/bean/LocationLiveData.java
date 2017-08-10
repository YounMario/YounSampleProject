package com.younchen.younsampleproject.architecture.lifecycles.bean;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.*;
import android.os.Bundle;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class LocationLiveData extends LiveData<Location> {

    private final LocationManager mLocationManager;
    private SimpleLocationListener mSimpleLocationListener;

    public LocationLiveData(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
        mSimpleLocationListener = new SimpleLocationListener();
    }

    @Override
    protected void onActive() {
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mSimpleLocationListener);
    }

    @Override
    protected void onInactive() {
        mLocationManager.removeUpdates(mSimpleLocationListener);
    }

    private class SimpleLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            setValue(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
