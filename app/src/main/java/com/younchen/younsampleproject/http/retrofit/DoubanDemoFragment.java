package com.younchen.younsampleproject.http.retrofit;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.http.retrofit.adapter.DoubanBookAdapter;
import com.younchen.younsampleproject.http.retrofit.bean.DoubanBook;
import com.younchen.younsampleproject.http.retrofit.model.DoubanViewModel;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class DoubanDemoFragment extends BaseFragment {

    private DoubanViewModel mDoubanViewModel;

    @BindView(R.id.common_list_view)
    RecyclerView mRecycleView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_common_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final DoubanBookAdapter doubanBookAdapter = new DoubanBookAdapter(getActivity());
        mRecycleView.setAdapter(doubanBookAdapter);
        mDoubanViewModel = new DoubanViewModel(getActivity());
        mDoubanViewModel.getData().observe(this, new Observer<List<DoubanBook>>() {
            @Override
            public void onChanged(@Nullable List<DoubanBook> doubanBooks) {
                doubanBookAdapter.setData(doubanBooks);
            }
        });
    }

    @Override
    public void onBackKeyPressed() {

    }
}
