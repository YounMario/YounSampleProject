package com.younchen.younsampleproject.http.okhttp.bean;

import com.younchen.younsampleproject.http.okhttp.CallBack;

import java.io.Serializable;

import okhttp3.Call;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public class DownLoadInfo implements Serializable {


    private String mUrl;
    private String mOutputPath;
    private String mTempOutputPath;

    private long mDownloadSize;
    private CallBack mCallBack;
    private Call mDownloadTask;
    private String mTag;
    private long mContentLength;
    private int mIndex = -1;

    public static final int NORMAL = 0;
    public static final int START = 2323;
    public static final int DOWNLOADING = 183;
    public static final int PAUSE = 957;
    public static final int FINISHED = 15611;
    public static final int FAIL = 2213;
    public static final int CANCEL = 1323;
    private int state = NORMAL;
    private int mProgress;
    private String mDownloadName;


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

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setName(String downloadName) {
        this.mDownloadName = downloadName;
    }

    public String getDownloadName() {
        return mDownloadName;
    }
}