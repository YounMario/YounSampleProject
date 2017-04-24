package com.younchen.younsampleproject.material.adapter;

import android.content.Context;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.material.bean.Contact;

/**
 * Created by Administrator on 2017/4/24.
 */

public class ChatListAdapter extends BaseAdapter<Contact>{


    public ChatListAdapter(Context context) {
        super(context, R.layout.item_chat_list_style);
    }

    @Override
    public void covert(ViewHolder holder, Contact item) {

    }
}
