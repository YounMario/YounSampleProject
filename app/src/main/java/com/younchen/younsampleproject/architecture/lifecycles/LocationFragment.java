package com.younchen.younsampleproject.architecture.lifecycles;

import android.Manifest;
import android.arch.core.util.Function;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.architecture.lifecycles.bean.LocationLiveData;
import com.younchen.younsampleproject.architecture.lifecycles.viewmodel.LocationViewModel;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.commons.utils.PermissionsUtil;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class LocationFragment extends BaseFragment implements LifecycleRegistryOwner {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String TAG = "LocationFragment";

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_common_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermission();
    }

    private void checkPermission() {
        if (!PermissionsUtil.hasPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ||
                !PermissionsUtil.hasPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            init();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    throw new RuntimeException("need permission:" + permissions[i]);
                }
            }
            init();
        }
    }

    private void init() {
        LocationViewModel model = ViewModelProviders.of(this).get(LocationViewModel.class);
        model.getLocationData().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                if (location != null) {
                    YLog.i(TAG, "speed:" + location.getTime());
                }
            }
        });
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
