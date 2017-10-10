package com.younchen.younsampleproject.commons.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by yinlongquan on 2017/10/10.
 */

public class NioFileUtils {

    public static byte[] readFile(String path) {
        BufferedSource bufferedSource = null;
        try {
            Source source = Okio.source(new File(path));
            bufferedSource = Okio.buffer(source);
            return bufferedSource.readByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            safeClose(bufferedSource);
        }
        return null;
    }


    public static void writeToFile(String outputPath, byte[] bytes, int start, int offset) {
        BufferedSink bufferedSink = null;
        try {
            Sink sink = Okio.sink(new File(outputPath));
            bufferedSink = Okio.buffer(sink);
            bufferedSink.write(bytes, start, offset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            safeClose(bufferedSink);
        }
    }

    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
