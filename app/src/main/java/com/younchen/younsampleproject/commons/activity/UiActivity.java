package com.younchen.younsampleproject.commons.activity;

import android.os.Bundle;

import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;
import com.younchen.younsampleproject.ui.activity.AnimationActivity;
import com.younchen.younsampleproject.ui.activity.MulitTypeListItemSampleActivity;
import com.younchen.younsampleproject.ui.activity.CustomLayoutActivity;
import com.younchen.younsampleproject.ui.activity.ScrollerViewSampleActivity;
import com.younchen.younsampleproject.ui.activity.SvgIconSampleActivity;

public class UiActivity extends BaseListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initAdapter(ActivityItemAdapter adapter) {
        adapter.add(new ActivityBean(CustomLayoutActivity.class,"Pullable Layout"));
        adapter.add(new ActivityBean(MulitTypeListItemSampleActivity.class,"mulit type list"));
        adapter.add(new ActivityBean(ScrollerViewSampleActivity.class,"scroller layout sample"));
        adapter.add(new ActivityBean(AnimationActivity.class ,"animation sample"));
        adapter.add(new ActivityBean(SvgIconSampleActivity.class,"draw view sample"));
    }
}
