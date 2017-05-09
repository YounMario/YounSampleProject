package com.younchen.younsampleproject.material.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.material.bean.Contact;

/**
 * Created by Administrator on 2017/4/23.
 */

public class ContactAdapter extends BaseAdapter<Contact> {

    private Context mContext;

    public ContactAdapter(Context context) {
        super(context, R.layout.item_contact_style);
        this.mContext = context;
    }

    @Override
    public void covert(ViewHolder holder, Contact item, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.img_head);
        Glide.with(mContext)
                .load(item.headImageUrl)
                .centerCrop()
                .crossFade()
                .into(imageView);
        holder.setText(R.id.txt_contact_name, item.name);
    }
}
