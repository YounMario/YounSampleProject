package com.younchen.younsampleproject.commons.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.commons.fragment.Frag;
import com.younchen.younsampleproject.commons.holder.ViewHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 龙泉 on 2016/7/21.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected List<T> data;
    protected Context context;
    protected int layoutId;


    public BaseAdapter(Context context, int layoutId) {
        data = new LinkedList<>();
        this.context = context;
        this.layoutId = layoutId;
    }

    public void add(T item) {
        data.add(item);
        notifyItemChanged(getItemCount() - 1);
    }

    public void addFirst(T item) {
        data.add(0, item);
        notifyItemChanged(0);
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public T getItem(int postion) {
        return data.get(postion);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        covert(holder, data.get(position), position);
    }

    public abstract void covert(ViewHolder holder, T item, int position);


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
