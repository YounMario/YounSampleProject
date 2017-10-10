package com.younchen.younsampleproject.http.okhttp;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by yinlongquan on 2017/9/20.
 */

public class ProgressResponseBody extends ResponseBody {

    private ResponseBody mResponseBody;
    private ProgressListener mProgressListener;
    private BufferedSource mDataSource;


    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        mResponseBody = responseBody;
        mProgressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mDataSource == null) {
            mDataSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mDataSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytes = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytes += bytesRead != -1 ? bytesRead : 0;
                if (null != mProgressListener) {
                    mProgressListener.onProgress(totalBytes, contentLength(), (int) (totalBytes * 1.0 * 100 / contentLength()));
                }
                if (totalBytes == contentLength() && mProgressListener != null) {
                    mProgressListener.onFinish();
                }
                return bytesRead;
            }
        };
    }

    public interface ProgressListener {

        void onProgress(long current, long total, int percent);

        void onPreDownload(long length);

        void onFail(Exception ex);

        void onFinish();
    }
}
