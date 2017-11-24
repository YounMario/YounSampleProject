package com.younchen.younsampleproject.kotlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/10/24.
 */

public class KotlinSampleFragment extends BaseFragment {

    @BindView(R.id.btn_start)
    Button btnStart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_kotlin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KotlinActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onBackKeyPressed() {

    }
}
