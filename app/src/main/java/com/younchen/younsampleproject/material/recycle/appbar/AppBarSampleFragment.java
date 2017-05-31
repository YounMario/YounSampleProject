package com.younchen.younsampleproject.material.recycle.appbar;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.material.adapter.ChatListAdapter;
import com.younchen.younsampleproject.material.bean.Contact;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/27.
 */

public class AppBarSampleFragment extends BaseFragment {

    private RecyclerView mRecycleView;
    private ArrayList<Contact> mContactList;
    private ChatListAdapter mChatListAdapter;
    private ImageView mHeadImage;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mRecycleView = (RecyclerView) mRootView.findViewById(R.id.layout_recycle);
        mHeadImage = (ImageView) mRootView.findViewById(R.id.head_image_view);
        mContactList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Contact contact = new Contact();
            contact.headImageUrl = Constants.HEAD_IMG[i % 5];
            contact.name = Constants.NAME[i % 5];
            contact.message = Constants.MESSAGE[i % 5];
            mContactList.add(contact);
        }
        mChatListAdapter = new ChatListAdapter(getActivity());
        mChatListAdapter.setData(mContactList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(mChatListAdapter);
        Glide.with(getActivity())
                .load("")
                .centerCrop()
                .into(mHeadImage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_material_action_bar_demo, container, false);
        return view;
    }

    @Override
    public void onBackKeyPressed() {

    }
}
