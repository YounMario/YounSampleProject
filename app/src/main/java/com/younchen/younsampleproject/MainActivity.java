package com.younchen.younsampleproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.younchen.younsampleproject.commons.activity.BaseActivity;
import com.younchen.younsampleproject.commons.adapter.FragAdapter;
import com.younchen.younsampleproject.commons.fragment.LauncherFragment;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecycleView;
    private FragAdapter mAdapter;
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
