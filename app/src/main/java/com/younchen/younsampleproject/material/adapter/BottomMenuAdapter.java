package com.younchen.younsampleproject.material.adapter;

import android.content.Context;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.material.bean.AppBean;

/**
 * Created by Administrator on 2017/4/28.
 */

public class BottomMenuAdapter extends BaseAdapter<AppBean> {

    public BottomMenuAdapter(Context context) {
        super(context, R.layout.item_grid_style);
    }

    @Override
    public void covert(ViewHolder holder, AppBean item) {
        holder.setText(R.id.txt_app_name, item.appName);
        holder.getView(R.id.img_icon).setBackgroundResource(R.color.colorAccent);
    }
}
