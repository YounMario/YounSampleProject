package com.younchen.younsampleproject.commons.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.bean.ActivityBean;
import com.younchen.younsampleproject.commons.holder.ViewHolder;

/**
 * Created by 龙泉 on 2016/7/21.
 */
public class ActivityItemAdapter extends BaseAdapter<ActivityBean> {

    public ActivityItemAdapter(Context context) {
        super(context, R.layout.layout_item_string);
    }

    @Override
    public void covert(ViewHolder holder, final ActivityBean item) {
        holder.setText(R.id.content, item.getDescription());
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, item.getActivity());
                context.startActivity(intent);
            }
        });
    }
}
