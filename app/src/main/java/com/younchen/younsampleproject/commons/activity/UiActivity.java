package com.younchen.younsampleproject.commons.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;
import com.younchen.younsampleproject.ui.layout.activity.CustomLayoutActivity;

public class UiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        recyclerView = (RecyclerView) findViewById(R.id.recycleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActivityItemAdapter adapter = new ActivityItemAdapter(UiActivity.this);
        adapter.add(new ActivityBean(CustomLayoutActivity.class,"Pullable Layout"));
        recyclerView.setAdapter(adapter);
    }
}
