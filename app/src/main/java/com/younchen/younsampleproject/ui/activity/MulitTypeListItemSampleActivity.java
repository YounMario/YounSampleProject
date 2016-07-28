package com.younchen.younsampleproject.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.MulitTypeAdapter;
import com.younchen.younsampleproject.commons.adapter.MulitTypeAdapterSupport;
import com.younchen.younsampleproject.ui.adapter.MulitSampleAdapter;
import com.younchen.younsampleproject.ui.bean.ChatMessage;

public class MulitTypeListItemSampleActivity extends AppCompatActivity {

    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulit_type_list_item_sample);
        listView = (RecyclerView) findViewById(R.id.listView);

        MulitTypeAdapter adapter = new MulitSampleAdapter(this);
        adapter.add(new ChatMessage(ChatMessage.TYPE_TEXT, "fuck up"));
        adapter.add(new ChatMessage(ChatMessage.TYPE_IMG, "lets it be"));

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);

    }
}
