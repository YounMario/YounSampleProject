package com.younchen.younsampleproject.http.okhttp.bean;

import com.younchen.younsampleproject.http.okhttp.CallBack;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public class DownLoadInfo {

    public static final long TOTAL_ERROR = -1;
    private String mUrl;
    private String mOutputPath;

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

    public void setContentLength(long getContentLength) {

    }

    public void setCallback(CallBack callBack) {
    }
}
