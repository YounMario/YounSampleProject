package com.younchen.younsampleproject.http.vollely;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/11/3.
 */

public class VolleyFragment extends BaseFragment {

    @BindView(R.id.txt_result)
    TextView mTextView;
    @BindView(R.id.btn_request)
    Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_volley, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequest();
            }
        });
    }

    private void doRequest() {

    }

    @Override
    public void onBackKeyPressed() {

    }
}
