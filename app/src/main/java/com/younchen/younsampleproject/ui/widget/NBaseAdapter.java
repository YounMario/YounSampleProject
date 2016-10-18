package com.younchen.younsampleproject.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.holder.ViewHolder;

import java.util.LinkedList;

/**
 * Created by younchen on 2016/7/21.
 */
public abstract class NBaseAdapter<T> extends UltimateViewAdapter<ViewHolder> {

    protected LinkedList<T> data;
    protected Context context;
    protected int layoutId;

    public NBaseAdapter(Context context, int layoutId) {
        data = new LinkedList<>();
        this.context = context;
        this.layoutId = layoutId;
    }

    public void add(T item) {
        data.addLast(item);
        notifyDataSetChanged();
    }

    public void addFirst(T item) {
        data.addFirst(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public T getItem(int postion){
       return data.get(postion);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof HeadHolder) {
            coverHead((HeadHolder) holder);
        } else if (holder instanceof FooterHolder) {
            coverFooter((FooterHolder) holder);
        }else{
            covert(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder newFooterHolder(View view) {
        return new FooterHolder(view);
    }

    @Override
    public ViewHolder newHeaderHolder(View view) {
        return new HeadHolder(view);
    }

    @Override
    public long generateHeaderId(int position) {
        return position;
    }

    @Override
    public int getAdapterItemCount() {
        return data.size();
    }


    public abstract void coverHead(HeadHolder holder);

    public abstract void coverFooter(FooterHolder holder);

    public abstract void covert(ViewHolder holder, int position);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return ViewHolder.get(parent,layoutId);
    }

}
