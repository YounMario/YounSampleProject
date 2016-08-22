package com.younchen.younsampleproject;

import android.os.Bundle;

import com.younchen.younsampleproject.commons.activity.BaseListActivity;
import com.younchen.younsampleproject.commons.activity.RxJavaActivity;
import com.younchen.younsampleproject.commons.activity.SysActivity;
import com.younchen.younsampleproject.commons.activity.UiActivity;
import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;

public class MainActivity extends BaseListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initAdapter(ActivityItemAdapter adapter) {
        adapter.add(new ActivityBean(UiActivity.class,"UI"));
        adapter.add(new ActivityBean(SysActivity.class,"System"));
        adapter.add(new ActivityBean(RxJavaActivity.class,"rxJava"));
    }

}
