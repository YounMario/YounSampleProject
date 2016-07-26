package com.younchen.younsampleproject.commons.activity;

import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;
import com.younchen.younsampleproject.sys.pic.AidlSampleActivity;
import com.younchen.younsampleproject.sys.pic.IPCSampleListActivity;

/**
 * Created by 龙泉 on 2016/7/25.
 */
public class SysActivity extends BaseListActivity{

    @Override
    public void initAdapter(ActivityItemAdapter adapter) {
        adapter.add(new ActivityBean(IPCSampleListActivity.class,"aidl"));
    }
}
