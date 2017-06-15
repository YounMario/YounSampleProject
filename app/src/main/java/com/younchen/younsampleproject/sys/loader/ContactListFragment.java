package com.younchen.younsampleproject.sys.loader;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.sys.loader.adapter.ContactAdapter;

/**
 * Created by Administrator on 2017/6/15.
 */

public class ContactListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    ContactAdapter mAdapter;
    ListView mRecycleView;
    ContactLoader mContactLoader;
    private int DEFAULT_LOADER_ID = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }


    private void initData() {
        getActivity().getLoaderManager().initLoader(DEFAULT_LOADER_ID, null, this);
    }

    private void initView(View view) {
        mRecycleView = (ListView) view.findViewById(R.id.contact_list_view);
        mAdapter = new ContactAdapter(getActivity());
        mRecycleView.setAdapter(mAdapter);

    }

    @Override
    public void onBackKeyPressed() {

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mContactLoader = new ContactLoader(getActivity());
        return mContactLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
