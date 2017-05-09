package com.younchen.younsampleproject.commons.adapter;

import android.content.Context;
import android.view.View;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.activity.BaseActivity;
import com.younchen.younsampleproject.commons.fragment.Frag;
import com.younchen.younsampleproject.commons.holder.ViewHolder;

/**
 * Created by Administrator on 2017/4/11.
 */

public class FragAdapter extends BaseAdapter<Frag> {

    private BaseActivity mActivity;
    private OnItemClickListener mOnItemClickListener;

    public FragAdapter(Context context) {
        super(context, R.layout.layout_item_string);
        this.mActivity = (BaseActivity) context;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public void covert(ViewHolder holder, final Frag item, int position) {
        holder.setText(R.id.content, item.getSimpleName());
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClicked(item);
                }
            }
        });
    }


    public interface OnItemClickListener {

        void onItemClicked(Frag item);
    }

}
