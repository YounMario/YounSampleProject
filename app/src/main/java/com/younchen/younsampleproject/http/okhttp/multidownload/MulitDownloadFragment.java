package com.younchen.younsampleproject.http.okhttp.multidownload;

import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.fragment.CommonListFragment;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;
import com.younchen.younsampleproject.http.okhttp.download.DownloadModel;
import com.younchen.younsampleproject.http.okhttp.multidownload.adapter.DownloadListAdapter;

import java.util.ArrayList;

/**
 * Created by yinlongquan on 2017/10/12.
 */

public class MulitDownloadFragment extends CommonListFragment {


    @Override
    public void onBackKeyPressed() {

    }

    @Override
    protected void init() {
        ArrayList<DownLoadInfo> downLoadInfos = new ArrayList<>();
        DownLoadInfo info1 = DownloadModel.getInstance().createDownloadInfo("http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_1mb.mp4");
        DownLoadInfo info2 = DownloadModel.getInstance().createDownloadInfo("http://www.sample-videos.com/video/mp4/360/big_buck_bunny_360p_2mb.mp4");
        DownLoadInfo info3 = DownloadModel.getInstance().createDownloadInfo("http://www.sample-videos.com/video/mp4/240/big_buck_bunny_240p_1mb.mp4");
        DownLoadInfo info4 = DownloadModel.getInstance().createDownloadInfo("http://www.sample-videos.com/video/mp4/240/big_buck_bunny_240p_2mb.mp4");

        downLoadInfos.add(info1);
        downLoadInfos.add(info2);
        downLoadInfos.add(info3);
        downLoadInfos.add(info4);
        mAdapter.setData(downLoadInfos);
    }

    @Override
    public BaseAdapter createAdapter() {
        mAdapter = new DownloadListAdapter(getContext(), mRecycleView);
        return mAdapter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.onDestroy();
    }
}
