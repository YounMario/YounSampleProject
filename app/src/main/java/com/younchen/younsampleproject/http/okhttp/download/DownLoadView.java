package com.younchen.younsampleproject.http.okhttp.download;

/**
 * Created by yinlongquan on 2017/9/20.
 */

public interface DownLoadView {

    void progress(int progress);

    void downloadStart();

    void downloadPause();

    void downloadFinish();

    void downloadResume();

    void downloadCanceled();
}
