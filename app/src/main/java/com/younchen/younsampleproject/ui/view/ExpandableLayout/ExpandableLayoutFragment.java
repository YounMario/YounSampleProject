package com.younchen.younsampleproject.ui.view.ExpandableLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.material.bean.Contact;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ExpandableLayoutFragment extends BaseFragment {

    private ExpandableLayout mExpandleLayout;
    private TextView mContentTxt;
    private TextView mMessageTxt;
    private TextView mUserTxt;
    private RecyclerView mRecycleView;
    private ExpandableItemAdapter mItemAdapter;
    private ArrayList<Contact> mContactList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_expand_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExpandleLayout = (ExpandableLayout) view.findViewById(R.id.expand_layout);
        mContentTxt = (TextView) view.findViewById(R.id.txt_expand_content);
        mUserTxt = (TextView) view.findViewById(R.id.txt_contact_name);
        mMessageTxt = (TextView) view.findViewById(R.id.txt_content);
        mRecycleView = (RecyclerView) view.findViewById(R.id.list_view);
        mContentTxt.setText("Hello here is world");
        mUserTxt.setText("Mio");
        mMessageTxt.setText("The message from haven!");

        mExpandleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandleLayout.toggle();
            }
        });

        mExpandleLayout.setExpandListener(new ExpandableLayout.ExpandListener() {
            @Override
            public void onExpand() {

            }

            @Override
            public void onHide() {

            }
        });

        mContactList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.headImageUrl = Constants.HEAD_IMG[i % 5];
            contact.name = Constants.NAME[i % 5];
            mContactList.add(contact);
        }
        mItemAdapter = new ExpandableItemAdapter(getActivity());
        mItemAdapter.setData(mContactList);
        mRecycleView.setAdapter(mItemAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onBackKeyPressed() {

    }
}
