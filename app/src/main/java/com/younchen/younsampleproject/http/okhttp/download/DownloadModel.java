package com.younchen.younsampleproject.http.okhttp.download;

import android.os.Handler;
import android.os.Looper;

import com.younchen.younsampleproject.commons.utils.FileUtils;
import com.younchen.younsampleproject.commons.utils.MD5Utils;
import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadResponse;
import com.younchen.younsampleproject.http.okhttp.bean.ResponseDelivery;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yinlongquan on 2017/9/20.
 */

public class DownloadModel {

    private static DownloadModel mIns;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;


    private ConcurrentHashMap<String, ProgressResponseBody.ProgressListener> mDownloadListenerMap;
    private HashMap<String, Call> mDownloading;
    private ResponseDelivery mResponseDelivery;
    private DownloadInfoProvider mDownloadInfoProvider;

    private File mSavePath = FileUtils.getSdCardFileOrDir("youn_sample");

    public static DownloadModel getInstance() {
        if (mIns == null) {
            mIns = new DownloadModel();
        }
        return mIns;
    }

    private DownloadModel() {
        mHandler = new Handler(Looper.getMainLooper());
        mDownloadListenerMap = new ConcurrentHashMap<>();
        mDownloading = new HashMap<>();
        mResponseDelivery = new ResponseDelivery(mHandler);
        mDownloadInfoProvider = new DownloadInfoProvider();

        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String tag = (String) chain.request().tag();
                        ProgressResponseBody.ProgressListener progressListener = mDownloadListenerMap.get(tag);
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();
        if (!mSavePath.exists()) {
            if (!mSavePath.mkdir()) {
                throw new RuntimeException("create save path error");
            }
        }
    }

    public void download(final String url, final CallBack callBack) {
        Observable.just(url).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return !mDownloading.containsKey(s);
            }
        }).flatMap(new Func1<String, Observable<DownLoadInfo>>() {
            @Override
            public Observable<DownLoadInfo> call(String s) {
                DownLoadInfo downLoadInfo = createDownloadInfo(s);
                downLoadInfo.setCallback(callBack);
                return download(downLoadInfo);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Observable download(final DownLoadInfo info) {
        final String tag = getTag(info.getUrl());
        final Request request = new Request.Builder().url(info.getUrl()).tag(tag).build();
        final DownLoadResponse downLoadResponse = new DownLoadResponse(mResponseDelivery, callBack);
        mDownloadListenerMap.put(tag, downLoadResponse);
        Call download = mOkHttpClient.newCall(request);
        mDownloading.put(tag, download);
        download.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downLoadResponse.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int lastDownloadOffset = mDownloadInfoProvider.getLastDownloadOffset(tag);
                byte[] bytes = response.body().bytes();
                FileUtils.randomWrite(info.getOutputPath(), bytes, lastDownloadOffset, bytes.length);
                mDownloadInfoProvider.saveLastDownloadOffset(tag, lastDownloadOffset + bytes.length);
            }
        });
    }


    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownLoadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownLoadInfo.TOTAL_ERROR;
    }

    private DownLoadInfo createDownloadInfo(String url) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        downLoadInfo.setUrl(url);
        downLoadInfo.setOutputPath(mSavePath + File.separator + url);
        long getContentLength = getContentLength(url);
        downLoadInfo.setContentLength(getContentLength);
        return downLoadInfo;
    }

    private String getTag(String url) {
        return MD5Utils.getStringMd5(url);
    }


    public void pause(String tag) {
        Call download = mDownloading.get(tag);
        if (download != null && !download.isCanceled()) {
            download.cancel();
        }
    }

    public void cancel(String tag) {
        pause(tag);
        mDownloadInfoProvider.clearLastDownload(tag);
    }
}
