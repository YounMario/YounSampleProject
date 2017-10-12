package com.younchen.younsampleproject.http.okhttp.download;


/**
 * Created by yinlongquan on 2017/10/10.
 */
public class DownloadInfoProvider {


    public long getLastDownloadOffset(String tag) {
        return SharedPreferenceUtils.getInstance().getLong("last_download_" + tag);
    }

    public void saveLastDownloadOffset(String tag, long lastOffset) {
        SharedPreferenceUtils.getInstance().putLong("last_download_" + tag, lastOffset);
    }

    public void clearLastDownload(String tag) {
        SharedPreferenceUtils.getInstance().putLong("last_download_" + tag, 0);
    }

    public void saveContentLength(String tag, long contentLength) {
        SharedPreferenceUtils.getInstance().putLong(tag, contentLength);
    }

    public long getContentLength(String tag) {
        return SharedPreferenceUtils.getInstance().getLong(tag);
    }
}
