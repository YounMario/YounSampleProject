package com.younchen.younsampleproject.commons.list;

import android.support.v7.widget.RecyclerView;

/**
 * Created by yinlongquan on 2017/8/25.
 */

public class ListAttach {

    private RecyclerView mRecycleView;
    private ListCallBack mCallBack;
    private ListPositionHelper mListPositionHelper;

    private boolean mIsLoadMoreEnable;

    private ListAttach(RecyclerView mRecycleView, ListCallBack listCallBack) {
        this.mRecycleView = mRecycleView;
        this.mCallBack = listCallBack;
        init();
    }

    private void init() {
        if (mRecycleView == null) {
            throw new IllegalArgumentException("Recycle view must not null");
        }
        if (mCallBack == null) {
            throw new IllegalArgumentException("Call back must not null");
        }
        mListPositionHelper = ListPositionHelper.create(mRecycleView);
        mRecycleView.addOnScrollListener(mOnScrollListener);
        mIsLoadMoreEnable = true;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int preLastVisiblePosition = mListPositionHelper.getPreLastVisiblePosition();
            int lastVisiblePosition = mListPositionHelper.getLastVisiblePosition();

            if (preLastVisiblePosition == lastVisiblePosition) {

            } else {
                boolean isSlideUp = preLastVisiblePosition < lastVisiblePosition;
                if (isSlideUp) {
                    if (!mCallBack.isLoading() && mIsLoadMoreEnable) {
                        int lastCompletelyVisibleItemPosition = mListPositionHelper.getLastCompletelyVisibleItemPosition();
                        if (lastCompletelyVisibleItemPosition == lastVisiblePosition)
                            mCallBack.onLoadMore();
                    }
                }
            }
            mListPositionHelper.setPreLastVisiblePosition(lastVisiblePosition);
        }
    };


    public static ListAttach create(RecyclerView mRecycleView, ListCallBack listCallBack) {
        return new ListAttach(mRecycleView, listCallBack);
    }

    public boolean isLoadMoreEnable() {
        return mIsLoadMoreEnable;
    }

    public void setIsLoadMoreEnable(boolean enable) {
        mIsLoadMoreEnable = enable;
    }
}
