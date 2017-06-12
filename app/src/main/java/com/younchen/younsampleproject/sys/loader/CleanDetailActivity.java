package com.younchen.younsampleproject.sys.loader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.sys.loader.adapter.CleanDetailAdapter;
import com.younchen.younsampleproject.sys.loader.bean.CleanContactItem;
import com.younchen.younsampleproject.sys.loader.bean.ContactItem;
import com.younchen.younsampleproject.sys.loader.bean.QueryEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CleanDetailActivity extends AppCompatActivity {

    private static String KEY_CONTACT_ITEM = "key_contact_list";

    private RecyclerView mRecycleView;
    private Button mCleanBtn;

    private List<ContactItem> mContactList;
    private CleanDetailAdapter mCleanDetailAdapter;

    private String mCleanBtnTextPrefix;

    public static void start(Context context, CleanContactItem item) {
        Intent starter = new Intent(context, CleanDetailActivity.class);
        starter.putExtra(KEY_CONTACT_ITEM, item);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_detail);
        initData();
        initView();
    }

    private void initView() {
        mCleanBtn = (Button) findViewById(R.id.btn_clean);
        mRecycleView = (RecyclerView) findViewById(R.id.contact_list);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mCleanDetailAdapter = new CleanDetailAdapter(this);
        mRecycleView.setAdapter(mCleanDetailAdapter);
        if (mContactList != null) {
            mCleanDetailAdapter.setData(mContactList);
        }
        mCleanBtn.setText(mCleanBtnTextPrefix);
        mCleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCleanDetailAdapter.cleanSelectedContact();
            }
        });

        mCleanDetailAdapter.setActionListener(new CleanDetailAdapter.ActionListener() {
            @Override
            public void onCheckChanged(int position, boolean isChecked) {
                updateCleanBtn();
            }

            @Override
            public void onClearFinished() {
                updateCleanBtn();
                EventBus.getDefault().post(new QueryEvent());
            }
        });
    }

    private void updateCleanBtn() {
        String text;
        if (mCleanDetailAdapter.getSelectedItemCount() == 0) {
            text = mCleanBtnTextPrefix;
        } else {
            text = mCleanBtnTextPrefix + "(" + mCleanDetailAdapter.getSelectedItemCount() + ")";
        }
        mCleanBtn.setText(text);
    }

    private void initData() {
        Intent intent = getIntent();
        CleanContactItem item = (CleanContactItem) intent.getSerializableExtra(KEY_CONTACT_ITEM);
        if (item != null) {
            mContactList = item.getContacts();
        }
        mCleanBtnTextPrefix = "Clean Selected ";
    }

}
