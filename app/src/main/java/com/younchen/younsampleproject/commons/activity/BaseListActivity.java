package com.younchen.younsampleproject.commons.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;

/**
 * Created by 龙泉 on 2016/7/25.
 */
public abstract class BaseListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActivityItemAdapter adapter = new ActivityItemAdapter(BaseListActivity.this);
        initAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }

    public abstract void initAdapter(ActivityItemAdapter adapter);
}
