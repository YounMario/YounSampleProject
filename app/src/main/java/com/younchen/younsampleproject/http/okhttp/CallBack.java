package com.younchen.younsampleproject.http.okhttp;

/**
 * Created by yinlongquan on 2017/9/28.
 */

public interface CallBack {

    void onStart(long current, long total);

    void onProgress(long current, long total, int percent);

    void onFinish();

    void pause();

    void cancel();

    void onFail(Throwable throwable);
}
