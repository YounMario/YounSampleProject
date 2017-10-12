package com.younchen.younsampleproject.http.okhttp.bean;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public class DownloadListener implements ProgressResponseBody.ProgressListener {

    private Handler mHandler;
    private CallBack mCallBack;

    public DownloadListener(CallBack callBack) {
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                DownloadEvent event = (DownloadEvent) msg.obj;
                switch (msg.what) {
                    case DownLoadStatus.PROGRESS:
                        mCallBack.onProgress(event.current, event.total, event.percent);
                        break;
                    case DownLoadStatus.FAIL:
                        mCallBack.onFail(event.ex);
                        break;
                    case DownLoadStatus.START:
                        mCallBack.onStart(event.current, event.total);
                        break;
                    case DownLoadStatus.FINISH:
                        mCallBack.onFinish();
                        break;
                }
            }
        };
    }

    @Override
    public void onProgress(long current, long total, int percent) {
        Message message = mHandler.obtainMessage();
        message.what = DownLoadStatus.PROGRESS;
        DownloadEvent downloadEvent = new DownloadEvent();
        downloadEvent.current = current;
        downloadEvent.total = total;
        downloadEvent.percent = percent;
        message.obj = downloadEvent;
        mHandler.sendMessage(message);
    }

    @Override
    public void onPreDownload(long length) {
        Message message = mHandler.obtainMessage();
        message.what = DownLoadStatus.START;
        DownloadEvent downloadEvent = new DownloadEvent();
        downloadEvent.total = length;
        message.obj = downloadEvent;
        mHandler.sendMessage(message);
    }

    @Override
    public void onFail(Exception ex) {
        Message message = mHandler.obtainMessage();
        message.what = DownLoadStatus.FAIL;
        DownloadEvent downloadEvent = new DownloadEvent();
        downloadEvent.ex = ex;
        message.obj = downloadEvent;
        mHandler.sendMessage(message);
    }

    @Override
    public void onFinish() {
        Message message = mHandler.obtainMessage();
        message.what = DownLoadStatus.FINISH;
        mHandler.sendMessage(message);
    }

    private class DownloadEvent {
        long total;
        long current;
        int percent;
        Exception ex;
    }
}
