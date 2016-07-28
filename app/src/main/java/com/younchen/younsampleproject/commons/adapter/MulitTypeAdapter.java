package com.younchen.younsampleproject.commons.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.younchen.younsampleproject.commons.holder.ViewHolder;

/**
 * Created by 龙泉 on 2016/7/27.
 */
public abstract class MulitTypeAdapter<T> extends BaseAdapter<T>{

    protected MulitTypeAdapterSupport support;

    public MulitTypeAdapter(Context context,MulitTypeAdapterSupport support) {
        super(context, -1);
        this.support = support;
    }

    public void setMulitTypeAdapterSupport(MulitTypeAdapterSupport support){
        this.support = support;
    }

    @Override
    public int getItemViewType(int position) {
        return support.getType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutId = support.layoutId(viewType);
        return ViewHolder.get(parent,layoutId);
    }

}
