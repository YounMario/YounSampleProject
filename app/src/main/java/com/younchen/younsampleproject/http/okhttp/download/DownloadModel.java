package com.younchen.younsampleproject.http.okhttp.download;

import android.os.Handler;
import android.os.Looper;

import com.younchen.younsampleproject.http.okhttp.CallBack;
import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadInfo;
import com.younchen.younsampleproject.http.okhttp.bean.DownLoadResponse;
import com.younchen.younsampleproject.http.okhttp.bean.ResponseDelivery;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinlongquan on 2017/9/20.
 */

public class DownloadModel {

    private static DownloadModel mIns;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;


    private ConcurrentHashMap<String, ProgressResponseBody.ProgressListener> mDownloadListenerMap;
    private ResponseDelivery mResponseDelivery;

    public static DownloadModel getInstance() {
        if (mIns == null) {
            mIns = new DownloadModel();
        }
        return mIns;
    }

    private DownloadModel() {
        mHandler = new Handler(Looper.getMainLooper());
        mDownloadListenerMap = new ConcurrentHashMap<>();
        mResponseDelivery = new ResponseDelivery(mHandler);
        //mOkHttpClient = new OkHttpClient();

        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        String tag = (String) chain.request().tag();
                        ProgressResponseBody.ProgressListener progressListener = mDownloadListenerMap.get(tag);
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();
    }

    public void download(DownLoadInfo info, String tag, CallBack callBack) {
        final Request request = new Request.Builder().url(info.getUrl()).tag(tag).build();
        final DownLoadResponse downLoadResponse = new DownLoadResponse(mResponseDelivery, callBack);
        mDownloadListenerMap.put(tag, downLoadResponse);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downLoadResponse.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void cancel(String stringMd5) {

    }
}
