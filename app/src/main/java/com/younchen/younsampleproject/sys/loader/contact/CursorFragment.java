package com.younchen.younsampleproject.sys.loader.contact;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.sys.loader.contact.adapter.CustomCursorAdapter;


/**
 * Created by Administrator on 2017/6/21.
 */

public abstract class CursorFragment<T extends CustomCursorAdapter> extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private View mRootView;
    private ListView mListView;
    private T mAdapter;
    private int mLoaderId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
        initData();
        initView(mRootView);
        getLoaderManager().initLoader(getLoaderId(), null, this);
    }

    protected void initData() {
    }

    private void initView(View mRootView) {
        mListView = (ListView) mRootView.findViewById(R.id.contact_list_view);
        mAdapter = createAdapter();
        mListView.setAdapter(mAdapter);
    }

    protected abstract T createAdapter();


    @Override
    public void onBackKeyPressed() {

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = createLoader();
        mAdapter.configureLoader(loader);
        return loader;
    }

    protected Loader<Cursor> createLoader() {
        return new CursorLoader(getActivity(), null, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public int getLoaderId() {
        return mLoaderId;
    }

    public void setLoaderId(int mLoaderId) {
        this.mLoaderId = mLoaderId;
    }
}
