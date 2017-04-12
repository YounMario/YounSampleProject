package com.younchen.younsampleproject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.younchen.younsampleproject.commons.activity.BaseActivity;
import com.younchen.younsampleproject.commons.activity.BaseListActivity;
import com.younchen.younsampleproject.commons.activity.RxJavaActivity;
import com.younchen.younsampleproject.commons.activity.SysActivity;
import com.younchen.younsampleproject.commons.activity.UiActivity;
import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.adapter.FragAdapter;
import com.younchen.younsampleproject.commons.adapter.StringItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.fragment.Frag;
import com.younchen.younsampleproject.commons.fragment.FragmentInflater;
import com.younchen.younsampleproject.commons.fragment.LauncherFragment;
import com.younchen.younsampleproject.commons.holder.ViewHolder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

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
