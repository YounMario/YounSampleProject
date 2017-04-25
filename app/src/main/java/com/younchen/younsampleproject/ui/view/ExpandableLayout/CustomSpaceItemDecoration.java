package com.younchen.younsampleproject.ui.view.ExpandableLayout;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/4/25.
 */

public class CustomSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;  //位移间距
    private int itemHeight;

    public CustomSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }


    interface OnMeasureItemHeightListener{

         void onMeasureHeight(int height);
    }

}
