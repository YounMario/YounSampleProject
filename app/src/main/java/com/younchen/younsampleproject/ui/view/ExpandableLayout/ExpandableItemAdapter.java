package com.younchen.younsampleproject.ui.view.ExpandableLayout;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.material.bean.Contact;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ExpandableItemAdapter extends BaseAdapter<Contact> {

    private Context mContext;

    public ExpandableItemAdapter(Context context) {
        super(context, R.layout.item_expand_message);
        mContext = context;
    }

    @Override
    public void covert(ViewHolder holder, Contact item) {
        ImageView imageView = (ImageView) holder.getView(R.id.img_head);
        Glide.with(mContext)
                .load(item.headImageUrl)
                .centerCrop()
                .crossFade()
                .into(imageView);
        holder.setText(R.id.txt_contact_name, item.name);
        holder.setText(R.id.txt_content, item.message);
        holder.setText(R.id.txt_expand_content, "hello the world");
        final ExpandableLayout expandableLayout = (ExpandableLayout) holder.getView(R.id.expand_layout);
        expandableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();
            }
        });
    }
}
