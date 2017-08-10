package com.younchen.younsampleproject.sys.loader.contact;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.younchen.younsampleproject.sys.loader.contact.adapter.ContactAdapter;

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
