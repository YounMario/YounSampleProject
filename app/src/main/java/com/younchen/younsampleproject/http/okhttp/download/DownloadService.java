package com.younchen.younsampleproject.http.okhttp.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;


/**
 * Created by yinlongquan on 2017/10/12.
 */

public class DownloadService extends Service {

    public static final String ACTION_DOWNLOAD_LOCAL_BROADCAST = "download_broad_cast";

    public static final String ACTION_DOWNLOAD = "action_download";
    public static final String ACTION_CANCEL = "action_cancel";
    public static final String ACTION_PAUSE = "action_pause";

    private static final String KEY_POSITION = "download_position";
    private static final String KEY_DOWNLOAD_URL = "download_url";

    public static final String KEY_DOWNLOAD_INFO = "key_download_info";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startDownload(Context context, int position, String url) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(KEY_DOWNLOAD_URL, url);
        intent.putExtra(KEY_POSITION, position);
        context.startService(intent);
    }

    public static void cancelDownload(Context context, int position, String url) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_CANCEL);
        intent.putExtra(KEY_POSITION, position);
        intent.putExtra(KEY_DOWNLOAD_URL, url);
        context.startService(intent);
    }

    public static void pauseDownload(Context context, String url) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_PAUSE);
        intent.putExtra(KEY_DOWNLOAD_URL, url);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_DOWNLOAD:
                download(intent);
                break;
            case ACTION_CANCEL:
                cancel(intent);
                break;
            case ACTION_PAUSE:
                pause(intent);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void pause(Intent intent) {
        String url = intent.getStringExtra(KEY_DOWNLOAD_URL);
        DownloadModel.getInstance().pause(url);
    }

    private void cancel(Intent intent) {
        String url = intent.getStringExtra(KEY_DOWNLOAD_URL);
        int position = intent.getIntExtra(KEY_POSITION, -1);
        DownLoadInfo info = DownloadModel.getInstance().createDownloadInfo(url);
        info.setIndex(position);
        info.setCallback(new DownloadCallBack(this, position));
        DownloadModel.getInstance().cancel(info);
    }

    private void download(Intent intent) {
        String url = intent.getStringExtra(KEY_DOWNLOAD_URL);
        int position = intent.getIntExtra(KEY_POSITION, -1);
        DownloadModel.getInstance().download(url, position, new DownloadCallBack(this, position));
    }

    class DownloadCallBack implements CallBack {

        private int mIndex;
        private Intent mIntent;
        private LocalBroadcastManager mLocalBroadCastManager;

        public DownloadCallBack(Context context, int index) {
            this.mIndex = index;
            mLocalBroadCastManager = LocalBroadcastManager.getInstance(context);
            mIntent = new Intent(ACTION_DOWNLOAD_LOCAL_BROADCAST);
        }

        @Override
        public void onStart(long current, long total) {
            DownLoadInfo downLoadInfo = new DownLoadInfo();
            downLoadInfo.setState(DownLoadInfo.PREPARE);
            downLoadInfo.setDownloadedSize(current);
            downLoadInfo.setContentLength(total);
            downLoadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, downLoadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);
        }

        @Override
        public void onProgress(long current, long total, int percent) {
            DownLoadInfo downLoadInfo = new DownLoadInfo();
            downLoadInfo.setState(DownLoadInfo.DOWNLOADING);
            downLoadInfo.setDownloadedSize(current);
            downLoadInfo.setContentLength(total);
            downLoadInfo.setProgress((int) (current * 100 / total));
            downLoadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, downLoadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);
        }

        @Override
        public void onFinish() {
            DownLoadInfo downLoadInfo = new DownLoadInfo();
            downLoadInfo.setState(DownLoadInfo.FINISHED);
            downLoadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, downLoadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);
        }

        @Override
        public void pause() {
            DownLoadInfo downLoadInfo = new DownLoadInfo();
            downLoadInfo.setState(DownLoadInfo.PAUSE);
            downLoadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, downLoadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);
        }

        @Override
        public void cancel() {
            DownLoadInfo downLoadInfo = new DownLoadInfo();
            downLoadInfo.setState(DownLoadInfo.CANCEL);
            downLoadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, downLoadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);
        }

        @Override
        public void onFail(Throwable throwable) {
            DownLoadInfo downLoadInfo = new DownLoadInfo();
            downLoadInfo.setState(DownLoadInfo.FAIL);
            downLoadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, downLoadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);
        }
    }

}
