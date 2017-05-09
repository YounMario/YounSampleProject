package com.younchen.younsampleproject.ui.view.ExpandableLayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.ui.view.adapter.ExpandableGridItemAdapter;


/**
 * Created by Administrator on 2017/4/25.
 */

public class ExpandableGridLayoutFragment extends BaseFragment {

    private TextView txtMore;

    private ExpandableGridItemAdapter mGridItemAdapter;
    private RecyclerView mRecycleView;
    private ExpandableLayout mExpandLayout;
    private CustomSpaceItemDecoration mGridItemSpaceDecoration;

    private ViewDragHelper mViewDragHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_expanedable_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = (RecyclerView) view.findViewById(R.id.content_list_view);
        mGridItemAdapter = new ExpandableGridItemAdapter(getActivity());
        mGridItemAdapter.setData(Constants.DEFAULT_CONTACT_DATA_LIST);

        CustomGridLayoutManager gridLayoutManager = new CustomGridLayoutManager(getActivity(), 3);
        gridLayoutManager.setScrollEnabled(false);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.setAdapter(mGridItemAdapter);
        mExpandLayout = (ExpandableLayout) view.findViewById(R.id.expend_layout);
        txtMore = (TextView) view.findViewById(R.id.txt_more);

        mGridItemSpaceDecoration = new CustomSpaceItemDecoration(3);
        mRecycleView.addItemDecoration(mGridItemSpaceDecoration);



        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandLayout.toggle();
            }
        });

        mExpandLayout.setExpandListener(new ExpandableLayout.ExpandListener() {
            @Override
            public void onExpand() {
                txtMore.setText("Collapse");
            }

            @Override
            public void onHide() {
                txtMore.setText("More");
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mExpandLayout != null) {
            mExpandLayout.refresh();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExpandLayout != null) {
            mExpandLayout.refresh();
        }
    }

    @Override
    public void onBackKeyPressed() {

    }
}
