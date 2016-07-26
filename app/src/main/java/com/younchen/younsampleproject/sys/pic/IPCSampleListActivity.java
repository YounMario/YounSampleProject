package com.younchen.younsampleproject.sys.pic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.younchen.younsampleproject.commons.activity.BaseListActivity;
import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;

public class IPCSampleListActivity extends BaseListActivity {

    @Override
    public void initAdapter(ActivityItemAdapter adapter) {
        adapter.add(new ActivityBean(AidlSampleActivity.class, "aidl without aidle file"));
        adapter.add(new ActivityBean(AidlCallBackSampleActivity.class,"aidl callback sample"));
        adapter.add(new ActivityBean(MessengerSampleActivity.class, "messenger sample activity"));
    }
}
