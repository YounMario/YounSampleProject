package com.younchen.younsampleproject.commons.activity;

import android.os.Bundle;

import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;
import com.younchen.younsampleproject.ui.layout.activity.CustomLayoutActivity;

public class UiActivity extends BaseListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initAdapter(ActivityItemAdapter adapter) {
        adapter.add(new ActivityBean(CustomLayoutActivity.class,"Pullable Layout"));
    }
}
