package com.younchen.younsampleproject.sys.topquery;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/9/14.
 */

public class TopQueryFragment extends BaseFragment {

    @BindView(R.id.btn_start_query_service)
    Button mStartServerBtn;

    @BindView(R.id.btn_start_top_query)
    Button mStartTopQuery;

    @BindView(R.id.btn_stop_top_query)
    Button mStopTopQuery;


    @BindView(R.id.btn_permission_page)
    Button mPermissionBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.top_query_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mStartServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopAppQueryService.start(getContext());
            }
        });

        mPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(intent);
            }
        });

        mStartTopQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopAppQueryService.startMonitor(getContext());
            }
        });

        mStopTopQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopAppQueryService.stopMonitor(getContext());
            }
        });
    }

    @Override
    public void onBackKeyPressed() {

    }
}
