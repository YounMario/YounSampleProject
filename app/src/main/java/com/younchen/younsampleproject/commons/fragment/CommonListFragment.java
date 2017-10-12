package com.younchen.younsampleproject.commons.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/8/18.
 */

public abstract class CommonListFragment<T> extends BaseFragment {

    @BindView(R.id.common_list_view)
    protected RecyclerView mRecycleView;

    protected BaseAdapter<T> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_common_list, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = createAdapter();
        mRecycleView.setAdapter(mAdapter);
        init();
    }

    protected void init() {

    }


    public abstract BaseAdapter<T> createAdapter();


}
