package com.younchen.younsampleproject.commons.list;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yinlongquan on 2017/8/25.
 */

public class ListPositionHelper {

    private RecyclerView.LayoutManager mLayoutManager;
    private int mPreLastVisiblePosition = 0;
    private OrientationHelper mOrientationHelper;
    private RecyclerView mRecycleView;
    private final int NO_POSITION = -1;

    private ListPositionHelper(RecyclerView recyclerView) {
        mLayoutManager = mRecycleView.getLayoutManager();
        mRecycleView = recyclerView;
        mOrientationHelper = mLayoutManager.canScrollVertically() ? OrientationHelper.createVerticalHelper(mLayoutManager) :
                OrientationHelper.createHorizontalHelper(mLayoutManager);
    }

    public static ListPositionHelper create(RecyclerView mRecycleView) {
        return new ListPositionHelper(mRecycleView);
    }


    public int getPreLastVisiblePosition() {
        return mPreLastVisiblePosition;
    }

    public int getFirstVisiblePosition() {
        View child = findOneVisibleChild(0, mLayoutManager.getChildCount(), false);
        return child == null ? NO_POSITION : mRecycleView.getChildAdapterPosition(child);
    }

    public int getFirstCompletelyVisiblePosition() {
        View child = findOneVisibleChild(0, mLayoutManager.getChildCount() - 1, true);
        return child == null ? NO_POSITION : mRecycleView.getChildAdapterPosition(child);
    }


    public int getLastVisiblePosition() {
        View child = findOneVisibleChild(mLayoutManager.getChildCount() - 1, -1, false);
        return child == null ? NO_POSITION : mRecycleView.getChildAdapterPosition(child);
    }

    public int getLastCompletelyVisibleItemPosition() {
        View child = findOneVisibleChild(mLayoutManager.getChildCount() - 1, -1, true);
        return child == null ? NO_POSITION : mRecycleView.getChildAdapterPosition(child);
    }

    public void setPreLastVisiblePosition(int lastVisiblePosition) {
        this.mPreLastVisiblePosition = lastVisiblePosition;
    }

    View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible) {
        int listStart = mOrientationHelper.getStartAfterPadding();
        int listEnd = mOrientationHelper.getEndAfterPadding();
        int next = toIndex > fromIndex ? 1 : -1;
        for (int i = fromIndex; i < toIndex; i += next) {
            final View child = mLayoutManager.getChildAt(i);
            int viewStart = mOrientationHelper.getDecoratedStart(child);
            int viewEnd = mOrientationHelper.getDecoratedEnd(child);
            if (viewStart < listEnd && viewEnd > listStart) {
                if (completelyVisible) {
                    if (viewStart >= listStart && viewEnd <= listEnd) {
                        return child;
                    }
                } else {
                    return child;
                }
            }
        }
        return null;
    }
}
