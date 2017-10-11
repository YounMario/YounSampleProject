package com.younchen.younsampleproject.com.square.io.okio;

import com.younchen.younsampleproject.commons.utils.FileUtils;
import com.younchen.younsampleproject.commons.utils.NioFileUtils;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * Created by yinlongquan on 2017/10/10.
 */

public class OkIoTest {

    @Test
    public void testNioWriteFile() {
        NioFileUtils.writeToFile("E:\\abc.txt", "abcd".getBytes(), 0, 4);
    }

    @Test
    public void testReadFile() {
        byte[] b = NioFileUtils.readFile("E:\\abc.txt");
        System.out.println(new String(b));
        System.out.println(b.length);
    }

    @Test
    public void testWriteFile() throws IOException {
        FileUtils.writeFile("zxv".getBytes(), "E:\\abc.txt");
    }

    @Test
    public void testRandomAccessWrite() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\abc.txt", "rw");
        FileChannel channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 1024 *5);
        String content = "adafdasf";
        mappedByteBuffer.put(content.getBytes(), 0, content.length());
    }

    @Test
    public void testGetFileLength() {
        File file = new File("E:\\abc.txt");
        System.out.println(" file size:" + file.length());
        System.out.println("free space:" + file.getFreeSpace());
        System.out.println("total space:" + file.getTotalSpace());
        System.out.println("" + file.getUsableSpace());
    }

    @Test
    public void testRenameFile() {
        FileUtils.rename("E:\\abc.txt", "E:\\def.txt");
    }
}
