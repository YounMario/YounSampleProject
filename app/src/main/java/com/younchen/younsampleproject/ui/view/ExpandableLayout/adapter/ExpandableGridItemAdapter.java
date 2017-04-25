package com.younchen.younsampleproject.ui.view.ExpandableLayout.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.material.bean.Contact;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ExpandableGridItemAdapter extends BaseAdapter<Contact> {

    private Context mContext;

    public ExpandableGridItemAdapter(Context context) {
        super(context, R.layout.item_expand_grid_item);
        this.mContext = context;
    }

    @Override
    public void covert(ViewHolder holder, Contact item) {
        ImageView imageView = (ImageView) holder.getView(R.id.img_head);
        Glide.with(mContext)
                .load(item.headImageUrl)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }
}
