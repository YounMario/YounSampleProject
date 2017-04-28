package com.younchen.younsampleproject.material.item;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.younchen.younsampleproject.material.widget.RePositionRelativeLayout;

/**
 * Created by Administrator on 2017/4/28.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;

    public GridItemDecoration(int spanCount) {
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // item position
        if ((view instanceof RePositionRelativeLayout)) {
            RePositionRelativeLayout rePositionRelativeLayout = (RePositionRelativeLayout) view;
            int position = parent.getChildAdapterPosition(view);
            int col = position % spanCount;
            if (col == 0) {
                rePositionRelativeLayout.setGravity(Gravity.START);
            } else if (col == spanCount - 1) {
                rePositionRelativeLayout.setGravity(Gravity.END);
            } else {
                rePositionRelativeLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            }
        }
    }

}
