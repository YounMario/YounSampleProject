package com.younchen.younsampleproject.sys.process.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.sys.process.bean.ProcessItem;

/**
 * Created by yinlongquan on 2017/8/18.
 */

public class ProcessItemAdapter extends BaseAdapter<ProcessItem> {

    public ProcessItemAdapter(Context context) {
        super(context, R.layout.item_contact_style);
    }

    @Override
    public void covert(ViewHolder holder, ProcessItem item, int position) {
        holder.setText(R.id.txt_contact_name, item.getmPackageName());
        ImageView mImageView = (ImageView) holder.getView(R.id.img_head);
        mImageView.setImageBitmap(item.getIcon());
    }
}
