package com.younchen.younsampleproject.sys.loader;

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

public class ContactListFragment extends CursorFragment<ContactAdapter> implements LoaderManager.LoaderCallbacks<Cursor> {

    ContactAdapter mAdapter;
    ContactLoader mContactLoader;


    @Override
    protected ContactAdapter createAdapter() {
        mAdapter = new ContactAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    protected void initData() {
        super.initData();
        setLoaderId(2);
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
