package com.younchen.younsampleproject.http.okhttp.bean;

import com.younchen.younsampleproject.http.okhttp.CallBack;

import okhttp3.Call;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public class DownLoadInfo {


    private String mUrl;
    private String mOutputPath;
    private String mTempOutputPath;

    private long mDownloadSize;
    private CallBack mCallBack;
    private Call mDownloadTask;
    private String mTag;
    private long mContentLength;

    public static final int NORMAL = 0;
    public static final int DOWNLOADING = 183;
    public static final int PAUSE = 957;
    public static final int FINISHED = 15611;
    private int state = NORMAL;


    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getOutputPath() {
        return mOutputPath;
    }

    public void setOutputPath(String outputPath) {
        this.mOutputPath = outputPath;
    }

    public void setCallback(CallBack callBack) {
        mCallBack = callBack;
    }

    public CallBack getCallBack() {
        return mCallBack;
    }

    public void setDownloadedSize(long downloaded) {
        mDownloadSize = downloaded;
    }

    public long getDownloadedSize() {
        return mDownloadSize;
    }

    public String getTempOutputPath() {
        return mTempOutputPath;
    }

    public void setTempOutputPath(String tempOutput) {
        mTempOutputPath = tempOutput;
    }

    public void setDownloadTask(Call download) {
        mDownloadTask = download;
    }

    public Call getDownloadTask() {
        return mDownloadTask;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        return mTag;
    }

    public long getContentLength() {
        return mContentLength;
    }

    public void setContentLength(long contentLength) {
        this.mContentLength = contentLength;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}