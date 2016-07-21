package com.younchen.younsampleproject.commons.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.commons.holder.BaseHolder;

import java.util.LinkedList;

/**
 * Created by 龙泉 on 2016/7/21.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected LinkedList<T> data;
    protected Context context;
    protected int layoutId;


    public BaseAdapter(Context context, int layoutId) {
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,false);
        return new BaseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        covert(baseHolder, data.get(position));
    }

    public abstract void covert(BaseHolder holder, T item);

    @Override
    public int getItemCount() {
        return data.size();
    }
}
