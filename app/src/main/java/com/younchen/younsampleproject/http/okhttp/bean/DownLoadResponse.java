package com.younchen.younsampleproject.http.okhttp.bean;

import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public class DownLoadResponse implements ProgressResponseBody.ProgressListener {

    private static final String TAG = "DownLoadResponse";
    private ResponseDelivery mResponseDelivery;
    private DownLoadStatus mDownloadStatue;

    public DownLoadResponse(ResponseDelivery delivery, CallBack callBack) {
        mResponseDelivery = delivery;
        mDownloadStatue = new DownLoadStatus(callBack);
    }


    @Override
    public void onProgress(long current, long total, int percent) {
        mDownloadStatue.setStatus(DownLoadStatus.PROGRESS);
        mDownloadStatue.setPercent(percent);
        mDownloadStatue.setLength(total);
        mDownloadStatue.setCurrent(current);
        YLog.i(TAG, "onProgress current:" + String.valueOf(current) + " total:" + total + " percent:" + percent);
        mResponseDelivery.post(mDownloadStatue);
    }

    @Override
    public void onPreDownload(long length) {
        mDownloadStatue.setStatus(DownLoadStatus.START);
        mDownloadStatue.setLength(length);
        mResponseDelivery.post(mDownloadStatue);
    }

    @Override
    public void onFail(Exception ex) {
        mDownloadStatue.setStatus(DownLoadStatus.FAIL);
        mDownloadStatue.setException(ex);
        mResponseDelivery.post(mDownloadStatue);
    }

    @Override
    public void onFinish() {
        mDownloadStatue.setStatus(DownLoadStatus.FINISH);
        mResponseDelivery.post(mDownloadStatue);
    }
}
