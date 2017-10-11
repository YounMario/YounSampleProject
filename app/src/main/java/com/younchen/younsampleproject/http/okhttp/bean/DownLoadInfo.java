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
    private DownLoadResponse response;
    private Call mDownloadTask;

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

    public void setResponse(DownLoadResponse response) {
        this.response = response;
    }

    public DownLoadResponse getResponse() {
        return response;
    }
}
