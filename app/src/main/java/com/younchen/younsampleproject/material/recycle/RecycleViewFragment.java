package com.younchen.younsampleproject.material.recycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.material.adapter.ContactAdapter;
import com.younchen.younsampleproject.material.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/23.
 */

public class RecycleViewFragment extends BaseFragment {

    private ContactAdapter contactAdapter;
    private List<Contact> mContactList;
    private RecyclerView mRecycleView;

    private String[] mHeadImageUrls =  {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969703963&di=99bc83e5726598b331b0cad3fe6b8e11&imgtype=0&src=http%3A%2F%2Fimg.dongman.fm%2Fpublic%2Fa983685029e9ff06ba781768668f3d79.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969765741&di=012bf5f1dd4e9c6fc176adc8c71e99f6&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fvideo%2F43%2F4334a0b86c5f4717c02c40435424b9b2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969824907&di=22edb4eff39599e79e525fa77a80a42a&imgtype=0&src=http%3A%2F%2Fimg.szhk.com%2FImage%2F2015%2F11%2F12%2F1447297124469.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969852317&di=ed6b4bbcdc2b0e448d1b41005d872bb8&imgtype=0&src=http%3A%2F%2Fi3.hoopchina.com.cn%2Fblogfile%2F201602%2F28%2FBbsImg145663906833985_512x512.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969929362&di=88ce895af1f244b1f6750fb112c77f91&imgtype=0&src=http%3A%2F%2Facg.ad2iction.com%2Fwp-content%2Fuploads%2F2016%2F06%2F1010-550x309.jpg"
    };

    private String[] mNames = {
            "秃头披风侠",
            "一拳",
            "夏萝莉",
            "音浪",
            "合伙"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_material_desgin_contact, container, false);
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
            contact.headImageUrl = mHeadImageUrls[i % 5];
            contact.name = mNames[i % 5];
            mContactList.add(contact);
        }
        contactAdapter = new ContactAdapter(getActivity());
        contactAdapter.setData(mContactList);
        mRecycleView.setAdapter(contactAdapter);
    }

    @Override
    public void onBackKeyPressed() {

    }
}
