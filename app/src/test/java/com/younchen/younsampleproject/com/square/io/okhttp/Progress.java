package com.younchen.younsampleproject.com.square.io.okhttp;

import com.younchen.younsampleproject.http.okhttp.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinlongquan on 2017/10/10.
 */

public class Progress {

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_5mb.mp4")
                .build();

        final ProgressResponseBody.ProgressListener progressListener = new ProgressResponseBody.ProgressListener() {
            @Override
            public void onProgress(long current, long total, int percent) {
                System.out.println(current);
                System.out.println(total);
                System.out.format("%d%% done\n", percent);
            }

            @Override
            public void onPreDownload(long length) {

            }

            @Override
            public void onFail(Exception ex) {

            }

            @Override
            public void onFinish() {

            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }


}
