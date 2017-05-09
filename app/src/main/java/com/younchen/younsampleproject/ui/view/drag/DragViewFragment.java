package com.younchen.younsampleproject.ui.view.drag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.ui.view.adapter.DragViewAdapter;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DragViewFragment extends BaseFragment {

    private static String TAG = "DragViewFragment";

    private RecyclerView mRecycleView;
    private DragViewAdapter mDragViewAdapter;
    private View mRemoveArea;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_drag_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycle_list);
        mRemoveArea = view.findViewById(R.id.remove_area);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        mDragViewAdapter = new DragViewAdapter(getActivity(), new DragViewAdapter.ItemDragListener() {
            @Override
            public void onDragStart(View v, int position) {
                YLog.i(TAG, "onDragStart view:" + v + " item position:" + position);
                mRemoveArea.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDragging(View v, int position, float x, float y) {
                YLog.i(TAG, "onDragging view:" + v + " item position:" + position + " location x:" + x +" location y:" + y);
            }


            @Override
            public void onDragEnd(View v, int position) {
                YLog.i(TAG, "onDragEnd view:" + v + " item position:" + position);
            }

            @Override
            public void onDrop(View v, int position) {
                YLog.i(TAG, "onDrop  view:" + v + " item position:" + position);
            }
        });
        mDragViewAdapter.setData(Constants.APPS);
        mRecycleView.setAdapter(mDragViewAdapter);
    }

    @Override
    public void onBackKeyPressed() {

    }
}
