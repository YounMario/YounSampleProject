package com.younchen.younsampleproject.sys.loader;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.utils.PermissionsUtil;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by Administrator on 2017/6/9.
 */

public class DataLoaderFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DEFAULT_LOADER_ID = 1;

    private View mRootView;
    private ListView mListView;
    private SimpleCursorAdapter mCursorAdapter;
    private BroadcastReceiver mReadContactsPermissionGrantedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //do refresh list here
        }
    };

    private final int READ_CONTACTS_PERMISSION_REQUEST_CODE = 100;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_common_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
        registerPermissionIfNeeded();
    }

    private void initData() {
        getLoaderManager().initLoader(DEFAULT_LOADER_ID, null, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void registerPermissionIfNeeded() {
        if (!PermissionsUtil.hasPermission(getActivity(), READ_CONTACTS)) {
            requestPermissions(new String[]{READ_CONTACTS},
                    READ_CONTACTS_PERMISSION_REQUEST_CODE);
            PermissionsUtil.registerPermissionReceiver(getActivity(),
                    mReadContactsPermissionGrantedReceiver, READ_CONTACTS);
        } else {
            initData();
            initView(mRootView);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_CONTACTS_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initData();
                initView(mRootView);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        PermissionsUtil.unregisterPermissionReceiver(getActivity(),
                mReadContactsPermissionGrantedReceiver);
        super.onStop();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        // Create an empty adapter we will use to display the loaded data.
        mCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.CONTACT_STATUS},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        mListView = (ListView) view.findViewById(R.id.common_list_view);
        mListView.setAdapter(mCursorAdapter);
    }

    @Override
    public void onBackKeyPressed() {

    }

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.CONTACT_PRESENCE,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.Contacts.LOOKUP_KEY,
    };

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        Uri baseUri;
        baseUri = ContactsContract.Contacts.CONTENT_URI;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
        return new CursorLoader(getActivity(), baseUri,
                CONTACTS_SUMMARY_PROJECTION, select, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        mCursorAdapter.swapCursor(null);
    }
}
