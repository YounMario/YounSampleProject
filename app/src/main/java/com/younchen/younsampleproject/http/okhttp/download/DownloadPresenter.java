package com.younchen.younsampleproject.http.okhttp.download;

import android.content.Context;

import com.younchen.younsampleproject.commons.utils.FileUtils;
import com.younchen.younsampleproject.commons.utils.MD5Utils;
import com.younchen.younsampleproject.commons.utils.StringUtils;
import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;

import java.io.File;


/**
 * Created by yinlongquan on 2017/9/20.
 */

public class DownloadPresenter {

    public static final int NORMAL = 0;
    public static final int DOWNLOADING = 1;
    public static final int PAUSE = 2;
    private DownLoadView mDownLoadView;

    private int mState = NORMAL;

    public DownloadPresenter(Context context, DownLoadView downloadView) {
        mDownLoadView = downloadView;
    }

    public void startDownload(String mDownloadUrl) {
        if (getState() == DownloadPresenter.DOWNLOADING) {
            pauseDownload(mDownloadUrl);
        } else if (getState() == DownloadPresenter.PAUSE) {
            resumeDownload(mDownloadUrl);
        } else if (getState() == DownloadPresenter.NORMAL) {
            download(mDownloadUrl);
        }
    }

    private void download(String downLoadUrl) {
        setStatus(DOWNLOADING);
        DownloadModel.getInstance().download(downLoadUrl, new CallBack() {

            @Override
            public void onStart(long current, long total) {
                mDownLoadView.downloadStart();
            }

            @Override
            public void onProgress(long current, long total, int percent) {
                mDownLoadView.progress(percent);
            }

            @Override
            public void onFinish() {
                mDownLoadView.downloadFinish();
            }

            @Override
            public void onFail(Throwable throwable) {
                mDownLoadView.downloadFail(throwable);
            }
        });
    }

    public void cancelDownload() {
        setStatus(NORMAL);
        mDownLoadView.downloadCanceled();
    }


    private void pauseDownload(String url) {
        setStatus(PAUSE);
        mDownLoadView.downloadPause();
        DownloadModel.getInstance().pause(url);
    }

    public void cancelAll() {
        DownloadModel.getInstance().cancel();
    }

    private void resumeDownload(String url) {
        setStatus(DOWNLOADING);
        mDownLoadView.downloadResume();
        download(url);
    }

    private int getState() {
        return mState;
    }

    private void setStatus(int state) {
        mState = state;
    }

}
