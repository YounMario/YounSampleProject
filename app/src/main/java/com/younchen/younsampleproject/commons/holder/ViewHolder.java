package com.younchen.younsampleproject.commons.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 龙泉 on 2016/7/21.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private View rootView;
    private SparseArray<View> mViews;

    public static ViewHolder get(ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    public ViewHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        mViews = new SparseArray<>();
    }


    public View getView(int id) {
        if (mViews.get(id) == null) {
            View itemView = rootView.findViewById(id);
            mViews.put(id, itemView);
        }
        return mViews.get(id);
    }

    public void setText(int viewId, String content) {
        TextView textView = (TextView) getView(viewId);
        textView.setText(content);
    }

    public void setImage(int viewId,int resId){
        ImageView imageView = (ImageView) getView(viewId);
        imageView.setImageResource(resId);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        rootView.setOnClickListener(onClickListener);
    }
}
