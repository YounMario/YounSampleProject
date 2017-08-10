package com.younchen.younsampleproject.sys.loader.contact;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.utils.PermissionsUtil;
import com.younchen.younsampleproject.sys.loader.contact.adapter.CleanItemAdapter;
import com.younchen.younsampleproject.sys.loader.contact.bean.CleanContactItem;
import com.younchen.younsampleproject.sys.loader.contact.bean.QueryEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CONTACTS;

/**
 * Created by Administrator on 2017/6/9.
 */

public class DataLoaderFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<CleanContactItem>> {

    private static final int DEFAULT_LOADER_ID = 1;

    private View mRootView;
    private RecyclerView mListView;
    private CleanItemAdapter mCleanItemAdapter;

    private final int READ_CONTACTS_PERMISSION_REQUEST_CODE = 100;
    private ContactCleanLoader mContactLoader;


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
        EventBus.getDefault().register(this);
    }

    private void initData() {
        getLoaderManager().initLoader(DEFAULT_LOADER_ID, null, this);
    }


    private void registerPermissionIfNeeded() {
        if (!PermissionsUtil.hasPermission(getActivity(), READ_CONTACTS) ||
                !PermissionsUtil.hasPermission(getActivity(), WRITE_CONTACTS) ||
                !PermissionsUtil.hasPermission(getActivity(), Manifest.permission.READ_CALL_LOG) ||
                !PermissionsUtil.hasPermission(getActivity(), Manifest.permission.WRITE_CALL_LOG) ||
                !PermissionsUtil.hasPermission(getActivity(), "android.permission.READ_PRIVILEGED_PHONE_STATE") ||
                !PermissionsUtil.hasPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
            requestPermissions(new String[]{READ_CONTACTS, WRITE_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, "android.permission.READ_PRIVILEGED_PHONE_STATE"},
                    READ_CONTACTS_PERMISSION_REQUEST_CODE);
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
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(QueryEvent event) {
        getLoaderManager().restartLoader(DEFAULT_LOADER_ID, null, this);
    }

    private void initView(View view) {
        mListView = (RecyclerView) view.findViewById(R.id.common_list_view);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCleanItemAdapter = new CleanItemAdapter(getActivity());
        mCleanItemAdapter.setItemClickedListener(new CleanItemAdapter.OnItemClickedListener() {
            @Override
            public void onItemSelected(int position, CleanContactItem item) {
                if (item.count > 0) {
                    CleanDetailActivity.start(getActivity(), item);
                }
            }
        });
        mListView.setAdapter(mCleanItemAdapter);
    }


    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public Loader<List<CleanContactItem>> onCreateLoader(int id, Bundle args) {
        mContactLoader = new ContactCleanLoader(getActivity());
        return mContactLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<CleanContactItem>> loader, List<CleanContactItem> data) {
        mCleanItemAdapter.setData(data);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        mCleanItemAdapter.setData(null);
    }
}
