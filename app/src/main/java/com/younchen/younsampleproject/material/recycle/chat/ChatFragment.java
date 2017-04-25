package com.younchen.younsampleproject.material.recycle.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.material.adapter.ChatListAdapter;
import com.younchen.younsampleproject.material.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class ChatFragment extends BaseFragment {


    private ChatListAdapter mChatListAdapter;
    private List<Contact> mContactList;
    private RecyclerView mRecycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_material_desgin_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mRecycleView = (RecyclerView) mRootView.findViewById(R.id.contact_list);
        mContactList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.headImageUrl = Constants.HEAD_IMG[i % 5];
            contact.name = Constants.NAME[i % 5];
            contact.message = Constants.MESSAGE[i % 5];
            mContactList.add(contact);
        }
        mChatListAdapter = new ChatListAdapter(getActivity());
        mChatListAdapter.setData(mContactList);
        mRecycleView.setAdapter(mChatListAdapter);
    }

    @Override
    public void onBackKeyPressed() {

    }
}
