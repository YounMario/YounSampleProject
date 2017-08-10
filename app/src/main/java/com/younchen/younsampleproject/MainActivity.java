package com.younchen.younsampleproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.younchen.younsampleproject.commons.activity.BaseActivity;
import com.younchen.younsampleproject.commons.fragment.LauncherFragment;

public class MainActivity extends BaseActivity {

    private LauncherFragment launcherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launcherFragment = (LauncherFragment) Fragment.instantiate(this, LauncherFragment.class.getName(), null);
        launcherFragment.show(this);
    }

    @Override
    public int getFragmentLayoutContainerId() {
        return R.id.main_layout_container;
    }
}
