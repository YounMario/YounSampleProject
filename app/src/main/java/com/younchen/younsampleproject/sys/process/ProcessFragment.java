package com.younchen.younsampleproject.sys.process;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.younchen.younsampleproject.commons.fragment.CommonListFragment;
import com.younchen.younsampleproject.sys.process.adapter.ProcessItemAdapter;
import com.younchen.younsampleproject.sys.process.bean.ProcessItem;
import com.younchen.younsampleproject.sys.process.model.ProcessViewModel;

import java.util.List;

/**
 * Created by yinlongquan on 2017/8/18.
 */

public class ProcessFragment extends CommonListFragment<ProcessItem> {

    private ProcessViewModel mProcessViewModel;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected void init() {
        mProcessViewModel = new ProcessViewModel(getActivity());
        mProcessViewModel.getData().observe(this, new Observer<List<ProcessItem>>() {
            @Override
            public void onChanged(@Nullable List<ProcessItem> processItems) {
                mAdapter.setData(processItems);
            }
        });
    }

    @Override
    public void onBackKeyPressed() {
    }

    @Override
    public ProcessItemAdapter createAdapter() {
        return new ProcessItemAdapter(getActivity());
    }
}
