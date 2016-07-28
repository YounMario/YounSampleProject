package com.younchen.younsampleproject.commons.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.holder.ViewHolder;

/**
 * Created by 龙泉 on 2016/7/21.
 */
public class StringItemAdapter extends BaseAdapter<String> {

    public StringItemAdapter(Context context) {
        super(context, R.layout.layout_item_string);
    }

    @Override
    public void covert(ViewHolder holder, final String item) {
        holder.setText(R.id.content,item);
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"clicked content:"+ item,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
