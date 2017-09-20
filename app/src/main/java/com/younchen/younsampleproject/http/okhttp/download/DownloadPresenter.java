package com.younchen.younsampleproject.http.okhttp.download;

import android.content.Context;

import com.younchen.younsampleproject.commons.utils.FileUtils;

import java.io.File;


/**
 * Created by yinlongquan on 2017/9/20.
 */

public class DownloadPresenter {

    public static final int NORMAL = 0;
    public static final int DOWNLOADING = 1;
    public static final int PAUSE = 2;
    private Context mContext;
    private DownLoadView mDownLoadView;

    private File mSavePath = FileUtils.getSdCardFileOrDir("youn_sample");
    private String mCurrentDownloadUrl;

    private int mState = NORMAL;

    public DownloadPresenter(Context context, DownLoadView downloadView) {
        mContext = context;
        mDownLoadView = downloadView;
        if (!mSavePath.exists()) {
            if (!mSavePath.mkdir()) {
                throw new RuntimeException("create save path error");
            }
        }
    }

    public void startDownload(String mDownloadUrl) {
        mCurrentDownloadUrl = mDownloadUrl;
        if (getState() == DownloadPresenter.DOWNLOADING) {
            pauseDownload();
        } else if (getState() == DownloadPresenter.PAUSE) {
            resumeDownload();
        } else if (getState() == DownloadPresenter.NORMAL) {
            download();
        }
    }

    private void download() {
        setStatus(DOWNLOADING);
        mDownLoadView.downloadStart();
    }

    public void cancelDownload() {
        setStatus(NORMAL);
        mDownLoadView.downloadCanceled();
    }


    private void pauseDownload() {
        setStatus(PAUSE);
        mDownLoadView.downloadPause();
    }

    private void resumeDownload() {
        setStatus(DOWNLOADING);
        mDownLoadView.downloadResume();
    }

    private int getState() {
        return mState;
    }

    private void setStatus(int state) {
        mState = state;
    }

}
