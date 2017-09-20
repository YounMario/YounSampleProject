package com.younchen.younsampleproject.http.okhttp.download;

import android.support.annotation.NonNull;

import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;

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


    private ConcurrentHashMap<String, ProgressResponseBody.ProgressListener> mDownloadListenerMap;

    public static DownloadModel getInstance() {
        if (mIns == null) {
            mIns = new DownloadModel();
        }
        return mIns;
    }

    private DownloadModel() {
        mDownloadListenerMap = new ConcurrentHashMap<>();
        mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String tag = (String) chain.request().tag();
                ProgressResponseBody.ProgressListener progressListener = mDownloadListenerMap.get(tag);
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener)).build();
            }
        }).build();
    }
    public void download(String url, String tag, @NonNull final ProgressResponseBody.ProgressListener listener) {
        Request request = new Request.Builder().url(url).tag(tag).build();
        mDownloadListenerMap.put(tag, listener);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFialre(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
