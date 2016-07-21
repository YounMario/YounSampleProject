package com.younchen.younsampleproject.commons.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 龙泉 on 2016/7/21.
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private View rootView;
    private SparseArray<View> mViews;

    public BaseHolder(View itemView) {
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

    public void setText(int viewId,String content){
        TextView textView = (TextView) getView(viewId);
        textView.setText(content);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        rootView.setOnClickListener(onClickListener);
    }
}
