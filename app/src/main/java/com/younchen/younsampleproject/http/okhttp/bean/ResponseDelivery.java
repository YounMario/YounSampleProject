package com.younchen.younsampleproject.http.okhttp.bean;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.younchen.younsampleproject.http.okhttp.CallBack;

import java.util.concurrent.Executor;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public class ResponseDelivery {

    private Executor mEventPoster;

    public ResponseDelivery(final Handler handler) {
        mEventPoster = new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                handler.post(command);
            }
        };
    }

    void post(DownLoadStatus downLoadStatus) {
        mEventPoster.execute(new DownloadDeliveryRunnable(downLoadStatus));
    }

    private class DownloadDeliveryRunnable implements Runnable {

        private DownLoadStatus mDownloadStatus;
        private CallBack mCallBack;

        DownloadDeliveryRunnable(DownLoadStatus downLoadStatus) {
            mDownloadStatus = downLoadStatus;
            mCallBack = downLoadStatus.getCallBack();
        }

        @Override
        public void run() {
            switch (mDownloadStatus.getStatus()) {
                case DownLoadStatus.START:
                    mCallBack.onStart(mDownloadStatus.getCurrent(), mDownloadStatus.getLength());
                    break;
                case DownLoadStatus.PROGRESS:
                    mCallBack.onProgress(mDownloadStatus.getCurrent(), mDownloadStatus.getLength(), mDownloadStatus.getPercent());
                    break;
                case DownLoadStatus.FINISH:
                    mCallBack.onFinish();
                    break;
                case DownLoadStatus.FAIL:
                    mCallBack.onFail(mDownloadStatus.getException());
                    break;
            }
        }
    }
}
