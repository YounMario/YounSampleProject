package com.younchen.younsampleproject.http.okhttp.bean;

import com.younchen.younsampleproject.http.okhttp.CallBack;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public class DownLoadStatus {

    public static final int START = 0;
    public static final int FAIL = -1;
    public static final int PROGRESS = 2;
    public static final int FINISH = 3;
    private int mStatus;
    private int mPercent;
    private long mLength;
    private long mCurrent;
    private Exception mException;
    private CallBack mCallBack;

    public DownLoadStatus(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setPercent(int percent) {
        this.mPercent = percent;
    }

    public void setLength(long length) {
        this.mLength = length;
    }

    public void setCurrent(long current) {
        this.mCurrent = current;
    }

    public void setException(Exception exception) {
        this.mException = exception;
    }

    public CallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public int getPercent() {
        return mPercent;
    }

    public long getLength() {
        return mLength;
    }

    public long getCurrent() {
        return mCurrent;
    }

    public Exception getException() {
        return mException;
    }
}
