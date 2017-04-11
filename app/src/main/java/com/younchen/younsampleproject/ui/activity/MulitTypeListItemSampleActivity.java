package com.younchen.younsampleproject.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.ui.adapter.VideoAdapter;
import com.younchen.younsampleproject.ui.adapter.VideoInfo;
import com.younchen.younsampleproject.ui.widget.UltimateRecyclerView;

public class MulitTypeListItemSampleActivity extends AppCompatActivity {

    private UltimateRecyclerView listView;
    private VideoAdapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mulit_type_list_item_sample);
//        listView = (UltimateRecyclerView) findViewById(R.id.listView);
//        handler = new Handler();
//
//
//        listView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        adapter = new VideoAdapter(this);
//        listView.addOnScrollListener(new VideoListScrollListener(listView.get(), adapter));
//        loadDate();
//        listView.setLoadMoreView(R.layout.layout_load_more);
//        listView.setAdapter(adapter);
//        listView.reenableLoadmore();
//
//        listView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
//            @Override
//            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadDate();
//                    }
//                }, 1000);
//            }
//        });
//
//
//        listView.enableDefaultSwipeRefresh(true);
//        listView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                adapter.clear();
//            }
//        });
    }

    private void loadDate() {
        for (int i = 0; i < 10; i++) {
            adapter.add(new VideoInfo());
        }
    }


}
