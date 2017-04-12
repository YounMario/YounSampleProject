package com.younchen.younsampleproject.ui.window;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.ui.window.service.FloatWindowService;

/**
 * Created by Administrator on 2017/4/12.
 */

public class FloatWindowFragment extends BaseFragment {

    private View mBtnShowWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_float_window, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        FloatWindowService.start(getActivity());
    }

    private void init(View view) {
        mBtnShowWindow = view.findViewById(R.id.btn_show_window);
        mBtnShowWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowService.startShowFloatWindow(getActivity());
            }
        });
    }

    @Override
    public void onBackKeyPressed() {

    }
}
