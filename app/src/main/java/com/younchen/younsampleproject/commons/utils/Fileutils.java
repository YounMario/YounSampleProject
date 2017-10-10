package com.younchen.younsampleproject.commons.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/19.
 */
public class FileUtils {

    /**
     * 文件排序
     */
    public static void SortFilePath(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }
        });
    }


    /**
     * 写文件
     *
     * @param data
     * @param path
     * @return
     */
    public static boolean writeFile(byte[] data, String path) throws Exception {
        ByteArrayInputStream bai = new ByteArrayInputStream(data);
        FileOutputStream fOutput = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fOutput = new FileOutputStream(file, false);
            byte[] buffer = new byte[4096];
            int length;

            while (true) {
                length = bai.read(buffer);
                if (length == -1) {
                    break;
                }
                fOutput.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                bai.close();
                if (fOutput != null)
                    fOutput.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * 读取数据
     *
     * @param path
     * @return
     */
    public static byte[] readFile(String path) throws Exception {
        FileInputStream fis = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int hasRead = -1;
            while ((hasRead = fis.read(buf)) != -1) {
                bao.write(buf, 0, hasRead);
            }
            return bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (bao != null) {
                    bao.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //

    /**
     * 递归获得文件夹大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;

        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 检测SD卡是否可用
     *
     * @return
     */
    public static boolean getSDCardMount() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保持图片
     *
     * @param path
     * @param bitmap
     */
    public static void saveBitmap(String path, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }

        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

        try {
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查找相关子字符串
     *
     * @param path        文件路径
     * @param filename    文件名
     * @param compareText 比较字符
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static boolean readFileCompareText(String path, String filename,
                                              String compareText) throws IOException {
        boolean result = false;
        String lineTxt = "";
        String allString = "";
        File file = new File(path + File.separator + filename);
        if (file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file));
            BufferedReader bufferedReader = new BufferedReader(read);

            while ((lineTxt = bufferedReader.readLine()) != null) {
                allString = allString + lineTxt;
            }
            result = Pattern.compile(compareText).matcher(allString).find();
        }

        return result;
    }


    /**
     * 从输入流中读取count字节保存到文件。如果文件存在，则清除内容并从开始位置保存数据；如果文件不存在，则创建文件并保存数据。
     *
     * @param input
     * @param count
     * @param file
     * @return 如果未保存count字节返回false
     * @throws IOException
     */
    public static boolean saveFile(InputStream input, int count, File file)
            throws IOException {
        FileOutputStream fOutput = new FileOutputStream(file, false);
        byte[] buffer = new byte[1024 * 10];
        int total = 0, length;
        try {
            while ((length = input.read(buffer, 0,
                    Math.min(count - total, buffer.length))) != -1
                    && total < count) {
                fOutput.write(buffer, 0, length);
                total += length;
            }
        } finally {
            fOutput.close();
        }
        return total == count;
    }

    /**
     * 读取输入流中的全部数据并保存到文件,文件不存在则自动创建
     *
     * @param input
     * @param file  要保存的文件
     * @throws IOException
     */
    public static void saveFile(InputStream input, File file)
            throws IOException {
        FileOutputStream fOutput = new FileOutputStream(file, false);
        byte[] buffer = new byte[1024 * 10];
        int length;
        try {
            while ((length = input.read(buffer, 0, buffer.length)) != -1) {
                fOutput.write(buffer, 0, length);
            }
        } finally {
            fOutput.close();
        }
    }

    /**
     * 将字节数组保存到文件。如果文件存在，则清除内容并从开始位置保存数据；如果文件不存在，则创建文件并保存数据。
     *
     * @param file
     * @param data
     * @param start
     * @param length
     * @throws IOException
     */
    public static void saveFile(File file, byte[] data, int start, int length)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(file, false);
        try {
            fos.write(data, start, length);
        } finally {
            fos.close();
        }
    }

    /**
     * 解压缩并存储到文件(zlib解压)
     *
     * @param input
     * @param count
     * @param output
     * @return
     * @throws IOException
     */
    public static void saveDecompressFile(InputStream input, int count,
                                          FileOutputStream output) throws IOException {
//        byte[] compressData = BytesUtil.readBytes(input, count);
//        if (compressData == null) {
//            return;
//        }
//
//        ByteArrayInputStream bais = new ByteArrayInputStream(compressData);
//        InflaterInputStream inflater = new InflaterInputStream(bais);
//        byte[] buffer = new byte[1024 * 10];
//        int length;
//        while ((length = inflater.read(buffer)) != -1) {
//            output.write(buffer, 0, length);
//        }
//
//        bais.close();
    }

    /**
     * delete file include its subfiles.
     *
     * @param file
     */
    public static void deleteFileTree(File file) {
        try {
            if (file == null) {
                return;
            }
            if (!file.exists()) {
                return;
            }
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files.length == 0) {
                    file.delete();
                } else {
                    for (int i = 0; i < files.length; i++) {
                        deleteFileTree(files[i]);
                    }
                    file.delete();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 将流写入文件
     *
     * @param dataIns
     * @param target
     * @throws IOException
     */
    public static void writeToFile(InputStream dataIns, File target)
            throws IOException {
        final int BUFFER = 1024;
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(target));
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = dataIns.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, count);
        }
        bos.close();
    }

    /**
     * 从字节数组中写入文件
     *
     * @param data
     * @param target
     * @throws IOException
     */
    public static void writeToFile(byte[] data, File target) throws IOException {
        FileOutputStream fo = null;
        ReadableByteChannel src = null;
        FileChannel out = null;
        try {
            src = Channels.newChannel(new ByteArrayInputStream(data));
            fo = new FileOutputStream(target);
            out = fo.getChannel();
            out.transferFrom(src, 0, data.length);
        } finally {
            if (fo != null) {
                fo.close();
            }
            if (src != null) {
                src.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 复制文件
     *
     * @param source - 源文件
     * @param target - 目标文件
     */
    public static void copyFile(File source, File target) {

        FileInputStream fi = null;
        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {
            fi = new FileInputStream(source);

            fo = new FileOutputStream(target);

            in = fi.getChannel();// 得到对应的文件通道

            out = fo.getChannel();// 得到对应的文件通道

            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得文件的大小KB,MB
     *
     * @param size
     * @return
     */
    public static String CountSize(long size) {
        final double baseKB = 1024.00, baseMB = 1024 * 1024.00;
        String strSize = "";
        if (size < baseKB) {
            strSize = size + " B";
        }
        if (size > baseKB && size < baseMB) {
            BigDecimal b = new BigDecimal((size / baseKB));
            float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            strSize = String.valueOf(f1 + "KB");
        } else if (size > baseMB) {
            BigDecimal b = new BigDecimal((size / baseMB));
            float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            strSize = String.valueOf(f1 + "MB");
        }
        return strSize;
    }

    /**
     * 获取图片
     *
     * @param dst
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static void fileCopy(File file, String newPath) throws Exception {
        if (file.exists()) {
            FileInputStream fin = new FileInputStream(file);
            FileOutputStream fout = new FileOutputStream(newPath);
            byte[] temp = new byte[1024];
            int c;
            while ((c = fin.read(temp)) != -1) {
                fout.write(temp, 0, c);
            }
            fin.close();
            fout.close();
        }

    }

    /**
     * 按路径保存图片
     *
     * @param bm
     * @param picUrl
     */
    public static void saveBitmap(Bitmap bm, String picUrl) {

        try {
            FileOutputStream out = new FileOutputStream(picUrl);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File getSdCardFileOrDir(String fileName) {
        String path = Environment.getExternalStorageDirectory() + File.separator + fileName;
        File file = new File(path);
        return file;
    }

    public static boolean isExist(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        return new File(filePath).exists();
    }

    public static String addSlash(final String path) {
        if (TextUtils.isEmpty(path)) {
            return File.separator;
        }

        if (path.charAt(path.length() - 1) != File.separatorChar) {
            return path + File.separatorChar;
        }

        return path;
    }

    public static boolean copyFile(String fromPath, String toPath) {
        /**
         * 拷贝文件
         *
         */
        boolean result = false;
        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromPath);
            to = new FileOutputStream(toPath);
            byte[] buffer = new byte[4096];
            int bytes_read;
            while ((bytes_read = from.read(buffer)) != -1)
                to.write(buffer, 0, bytes_read);
            result = true;
        } catch (IOException e) {
            if (e != null) {

            }
            result = false;
        } finally {
            if (from != null)
                try {
                    from.close();
                } catch (IOException e) {
                }
            if (to != null)
                try {
                    to.close();
                } catch (IOException e) {
                }
        }
        return result;
    }

    /**
     * @param outputPath
     * @param bytes
     * @param lastDownloadOffset
     * @param length
     */
    public static void randomWrite(String outputPath, byte[] bytes, int lastDownloadOffset, int length) {
        RandomAccessFile rFile = null;
        try {
            File file = new File(outputPath);
            rFile = new RandomAccessFile(file, "rw");
            rFile.write(bytes, lastDownloadOffset, length);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            safeClose(rFile);
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
