package com.younchen.younsampleproject.http.retrofit.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.http.retrofit.bean.DoubanBook;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class DoubanBookAdapter extends BaseAdapter<DoubanBook> {


    public DoubanBookAdapter(Context context) {
        super(context, R.layout.item_chat_list_style2);
    }

    @Override
    public void covert(ViewHolder holder, DoubanBook item, int position) {
        holder.setText(R.id.txt_title, item.getTitle());
        ImageView imageView = (ImageView) holder.getView(R.id.img_head);
        Glide.with(context)
                .load(item.getImage())
                .centerCrop()
                .crossFade()
                .into(imageView);
    }
}
