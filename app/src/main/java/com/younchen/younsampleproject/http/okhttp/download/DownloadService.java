package com.younchen.younsampleproject.http.okhttp.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.younchen.younsampleproject.R;
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

    private NotificationManagerCompat mNotificationManager;

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

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
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
        info.setCallback(new DownloadCallBack(this, info, position, mNotificationManager));
        DownloadModel.getInstance().cancel(info);
    }

    private void download(Intent intent) {
        String url = intent.getStringExtra(KEY_DOWNLOAD_URL);
        int position = intent.getIntExtra(KEY_POSITION, -1);
        DownLoadInfo downLoadInfo = DownloadModel.getInstance().createDownloadInfo(url);
        downLoadInfo.setIndex(position);
        downLoadInfo.setCallback(new DownloadCallBack(this, downLoadInfo, position, mNotificationManager));
        DownloadModel.getInstance().download(downLoadInfo);
    }

    class DownloadCallBack implements CallBack {

        private int mIndex;
        private Intent mIntent;
        private long mLastTime;
        private LocalBroadcastManager mLocalBroadCastManager;

        private NotificationManagerCompat mNotificationManagerCompat;
        private NotificationCompat.Builder mNotificationBuilder;

        private DownLoadInfo mDownloadInfo;

        public DownloadCallBack(Context context, DownLoadInfo info, int index, NotificationManagerCompat notificationManagerCompat) {
            this.mIndex = index;
            mLocalBroadCastManager = LocalBroadcastManager.getInstance(context);
            mIntent = new Intent(ACTION_DOWNLOAD_LOCAL_BROADCAST);
            mNotificationManagerCompat = notificationManagerCompat;
            mNotificationBuilder = new NotificationCompat.Builder(context);
            mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mDownloadInfo = info;
        }

        private void updateNotification() {
            mNotificationManagerCompat.notify(mIndex + 10000, mNotificationBuilder.build());
        }

        @Override
        public void onStart(long current, long total) {
            mDownloadInfo.setState(DownLoadInfo.START);
            mDownloadInfo.setDownloadedSize(current);
            mDownloadInfo.setContentLength(total);
            mDownloadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, mDownloadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);
            mNotificationBuilder
                    .setContentTitle(mDownloadInfo.getDownloadName())
                    .setContentText("download start")
                    .setProgress(100, 0, true)
                    .setTicker("download start:" + mDownloadInfo.getDownloadName());
            updateNotification();
        }

        @Override
        public void onProgress(long current, long total, int percent) {
            long timeNow = System.currentTimeMillis();
            if (timeNow - mLastTime > 500) {
                mDownloadInfo.setState(DownLoadInfo.DOWNLOADING);
                mDownloadInfo.setDownloadedSize(current);
                mDownloadInfo.setContentLength(total);
                mDownloadInfo.setProgress((int) (current * 100 / total));
                mDownloadInfo.setIndex(mIndex);

                mIntent.putExtra(KEY_DOWNLOAD_INFO, mDownloadInfo);
                mLocalBroadCastManager.sendBroadcast(mIntent);
                mLastTime = timeNow;

                mNotificationBuilder.setContentText("Downloading");
                mNotificationBuilder.setProgress(100, percent, false);
                updateNotification();
            }
        }

        @Override
        public void onFinish() {
            mDownloadInfo.setState(DownLoadInfo.FINISHED);
            mDownloadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, mDownloadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);

            mNotificationBuilder.setContentText("Download Complete");
            mNotificationBuilder.setProgress(0, 0, false);
            mNotificationBuilder.setTicker(mDownloadInfo.getDownloadName() + "downloadComplete");
            updateNotification();
        }

        @Override
        public void pause() {
            mDownloadInfo.setState(DownLoadInfo.PAUSE);
            mDownloadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, mDownloadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);

            mNotificationBuilder.setContentText("Download Paused");
            mNotificationBuilder.setTicker(mDownloadInfo.getDownloadName() + " download Paused");
            mNotificationBuilder.setProgress(100, mDownloadInfo.getProgress(), false);
            updateNotification();
        }

        @Override
        public void cancel() {
            mDownloadInfo.setState(DownLoadInfo.CANCEL);
            mDownloadInfo.setIndex(mIndex);
            mIntent.putExtra(KEY_DOWNLOAD_INFO, mDownloadInfo);
            mLocalBroadCastManager.sendBroadcast(mIntent);

            mNotificationBuilder.setContentText("Download Canceled");
            mNotificationBuilder.setTicker(mDownloadInfo.getDownloadName() + " download Canceled");
            updateNotification();

            mNotificationManagerCompat.cancel(mIndex + 10000);
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
