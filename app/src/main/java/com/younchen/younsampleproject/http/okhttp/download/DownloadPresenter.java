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

    private File mSavePath = FileUtils.getSdCardFileOrDir("youn_sample");

    private int mState = NORMAL;

    public DownloadPresenter(Context context, DownLoadView downloadView) {
        mDownLoadView = downloadView;
        if (!mSavePath.exists()) {
            if (!mSavePath.mkdir()) {
                throw new RuntimeException("create save path error");
            }
        }
    }

    public void startDownload(String mDownloadUrl) {
        if (getState() == DownloadPresenter.DOWNLOADING) {
            pauseDownload();
        } else if (getState() == DownloadPresenter.PAUSE) {
            resumeDownload();
        } else if (getState() == DownloadPresenter.NORMAL) {
            download(mDownloadUrl);
        }
    }

    private void download(String downLoadUrl) {
        setStatus(DOWNLOADING);
        DownLoadInfo info = new DownLoadInfo();
        info.setUrl(downLoadUrl);
        DownloadModel.getInstance().download(info, String.valueOf(downLoadUrl.hashCode()), new CallBack() {
            @Override
            public void onStart(long length) {
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
