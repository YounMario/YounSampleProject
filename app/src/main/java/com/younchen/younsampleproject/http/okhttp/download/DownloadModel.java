package com.younchen.younsampleproject.http.okhttp.download;

import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.commons.utils.FileUtils;
import com.younchen.younsampleproject.commons.utils.MD5Utils;
import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;
import com.younchen.younsampleproject.http.okhttp.bean.DownloadListener;
import com.younchen.younsampleproject.http.okhttp.bean.ResponseDelivery;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by yinlongquan on 2017/9/20.
 */

public class DownloadModel {

    private static final String TAG = "DownloadModel";
    private static DownloadModel mIns;
    private OkHttpClient mOkHttpClient;


    private ConcurrentHashMap<String, ProgressResponseBody.ProgressListener> mDownloadListenerMap;
    private HashMap<String, DownLoadInfo> mDownloading;
    private DownloadInfoProvider mDownloadInfoProvider;

    private File mSavePath = FileUtils.getSdCardFileOrDir("youn_sample");

    public static DownloadModel getInstance() {
        if (mIns == null) {
            mIns = new DownloadModel();
        }
        return mIns;
    }

    private DownloadModel() {
        mDownloadListenerMap = new ConcurrentHashMap<>();
        mDownloading = new HashMap<>();
        mDownloadInfoProvider = new DownloadInfoProvider();

        mOkHttpClient = new OkHttpClient();
        if (!mSavePath.exists()) {
            if (!mSavePath.mkdir()) {
                throw new RuntimeException("create save path error");
            }
        }
    }

    public void download(final String url, final CallBack callBack) {
        if (mDownloading.containsKey(getTag(url))) {
            YLog.i(TAG, " download already started");
            return;
        }
        if (FileUtils.isExist(getSavePath(url))) {
            YLog.i(TAG, "already downloaded");
            callBack.onFinish();
            return;
        }
        DownLoadInfo info = createDownloadInfo(url, callBack);
        realDownload(info);
    }

    private void realDownload(final DownLoadInfo info) {
        final long start = info.getDownloadedSize();
        YLog.i(TAG, " start real download begin:" + start + " out put path :" + info.getTempOutputPath());
        final String tag = getTag(info.getUrl());
        final Request request = new Request.Builder().url(info.getUrl()).header("RANGE", "bytes=" + start + "-").tag(tag).build();
        final DownloadListener downloadListener = new DownloadListener(info.getCallBack());

        final Call download = mOkHttpClient.newCall(request);
        info.setDownloadTask(download);
        mDownloading.put(tag, info);
        download.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                info.getCallBack().onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    downloadListener.onFail(new RuntimeException("response body is null"));
                    return;
                }
                YLog.i(TAG, " writing sdcard start..........");
                RandomAccessFile randomAccessFile = null;
                FileChannel channelOut = null;
                InputStream inputStream = null;

                long total = mDownloadInfoProvider.getContentLength(tag);
                if (total == 0) {
                    total = responseBody.contentLength();
                    YLog.i(TAG, " content length:" + total);
                    if (total > 0) {
                        mDownloadInfoProvider.saveContentLength(tag, total);
                    }
                }

                YLog.i(TAG, " start index :" + start + " total :" + total);
                try {
                    inputStream = responseBody.byteStream();
                    randomAccessFile = new RandomAccessFile(info.getTempOutputPath(), "rw");
                    channelOut = randomAccessFile.getChannel();
                    MappedByteBuffer mappedByteBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, start, total - start);
                    byte[] buffer = new byte[4096];
                    int readLength;
                    long downloadSize = start;
                    downloadListener.onPreDownload(total);
                    while ((readLength = inputStream.read(buffer)) != -1) {
                        downloadSize += readLength;
                        info.setDownloadedSize(downloadSize);
                        int percentage = (int) (downloadSize * 100 * 1.0 / total);
                        YLog.i(TAG, " current download :" + downloadSize + " total:" + total + " percentage:" + percentage);
                        downloadListener.onProgress(downloadSize, total, percentage);
                        mappedByteBuffer.put(buffer, 0, readLength);
                    }
                    renameOutputPath(info);
                    mDownloadInfoProvider.clearLastDownload(tag);
                    downloadListener.onFinish();
                    mDownloading.remove(tag);
                } catch (Exception ex) {
                    mDownloading.remove(tag);
                    YLog.i(TAG, " error to write file :" + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    FileUtils.safeClose(inputStream);
                    FileUtils.safeClose(randomAccessFile);
                    FileUtils.safeClose(channelOut);
                }
            }
        });
    }

    private void renameOutputPath(DownLoadInfo tempOutputPath) {
        String tempFile = tempOutputPath.getTempOutputPath();
        String outPutFile = tempFile.replace(".temp", "");
        FileUtils.rename(tempFile, outPutFile);
    }


    private DownLoadInfo createDownloadInfo(String url, CallBack callBack) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        downLoadInfo.setUrl(url);
        downLoadInfo.setCallback(callBack);
        loadDownloadedInfo(downLoadInfo);
        return downLoadInfo;
    }

    private void loadDownloadedInfo(DownLoadInfo downLoadInfo) {
        getRealFileName(downLoadInfo);
        YLog.i(TAG, " real output file:" + downLoadInfo.getOutputPath());
        if (FileUtils.isExist(downLoadInfo.getTempOutputPath())) {
            YLog.i(TAG, " temp output file:" + downLoadInfo.getTempOutputPath());
            long lastDownloadedOffset = mDownloadInfoProvider.getLastDownloadOffset(getTag(downLoadInfo.getUrl()));
            YLog.i(TAG, "last downloaded offset:" + lastDownloadedOffset);
            downLoadInfo.setDownloadedSize(lastDownloadedOffset);
        }
    }

    private void getRealFileName(DownLoadInfo downLoadInfo) {
        String outputFileName = getSavePath(downLoadInfo.getUrl());
        downLoadInfo.setOutputPath(outputFileName);
        downLoadInfo.setTempOutputPath(downLoadInfo.getOutputPath() + ".temp");
    }

    private String getSavePath(String url) {
        return mSavePath + File.separator + MD5Utils.getStringMd5(url);
    }


    private String getTag(String url) {
        return MD5Utils.getStringMd5(url);
    }


    public void pause(String url) {
        String tag = getTag(url);
        DownLoadInfo downloadInfo = mDownloading.get(tag);
        if (downloadInfo != null && downloadInfo.getDownloadTask() != null && !downloadInfo.getDownloadTask().isCanceled()) {
            YLog.i(TAG, "pause download URL:" + url);
            if (!FileUtils.isExist(downloadInfo.getOutputPath())) {
                mDownloadInfoProvider.saveLastDownloadOffset(tag, downloadInfo.getDownloadedSize());
            }
            mDownloading.remove(getTag(url));
            downloadInfo.getDownloadTask().cancel();
        }
    }

    public void cancel() {
        mOkHttpClient.dispatcher().cancelAll();
    }
}
