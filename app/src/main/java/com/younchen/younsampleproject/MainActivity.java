package com.younchen.younsampleproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.younchen.younsampleproject.commons.activity.BaseListActivity;
import com.younchen.younsampleproject.commons.activity.RxJavaActivity;
import com.younchen.younsampleproject.commons.activity.SysActivity;
import com.younchen.younsampleproject.commons.activity.UiActivity;
import com.younchen.younsampleproject.commons.adapter.ActivityItemAdapter;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.adapter.StringItemAdapter;
import com.younchen.younsampleproject.commons.bean.ActivityBean;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.fragment.Frag;
import com.younchen.younsampleproject.commons.fragment.FragmentInflater;
import com.younchen.younsampleproject.commons.holder.ViewHolder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleView = (RecyclerView) findViewById(R.id.main_recycle_list);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        StringItemAdapter stringItemAdapter = new StringItemAdapter(this);
        List<Frag> fragList = null;
        try {
            fragList = FragmentInflater.inflate(getPackageName(), new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    try {
                        return BaseFragment.class.isAssignableFrom(Class.forName(filename));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return false;
                };
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragList != null) {
            for (int i = 0; i < fragList.size(); i++) {
                stringItemAdapter.add(fragList.get(i).getName());
            }
            mRecycleView.setAdapter(stringItemAdapter);
        }
    }


}
