package com.younchen.younsampleproject.com.square.io.okio;

import com.younchen.younsampleproject.commons.utils.FileUtils;
import com.younchen.younsampleproject.commons.utils.NioFileUtils;

import org.junit.Test;


/**
 * Created by yinlongquan on 2017/10/10.
 */

public class OkIoTest {

    @Test
    public void testWriteFile() {
        NioFileUtils.writeToFile("E:\\abc.txt", "abcd".getBytes(), 0, 4);
    }

    @Test
    public void testReadFile() {
        byte[] b = NioFileUtils.readFile("E:\\abc.txt");
        System.out.println(new String(b));
        System.out.println(b.length);
    }

    @Test
    public void testRandomAccessWrite() {
        FileUtils.randomWrite("E:\\abc.txt", "efg".getBytes(), 2, 2);
    }
}
