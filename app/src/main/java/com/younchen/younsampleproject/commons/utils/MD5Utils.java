/**
 * @brief     Package com.ijinshan.browser.utils
 * @author    zuoyi
 * @since     1.0.0.0
 * @version   1.0.0.0
 * @date      2013-04-23
 */

package com.younchen.younsampleproject.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================

/**
 * @class MD5Util
 * @brief about md5 utils\n
 * @author zuoyi
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2013-04-23
 * @par Applied: External
 */
public class MD5Utils {

    protected static char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    protected static MessageDigest fileMessageDigest = null;
    protected static MessageDigest stringMessageDigest = null;
    static {
        try {
            stringMessageDigest = MessageDigest.getInstance("MD5");
            fileMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Utils.class.getName()
                    + "failure");
            nsaex.printStackTrace();
        }
    }

    /**
     * generate the String's md5 value
     * 
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }


    public static String getFileMD5String(File file) {
         InputStream fis = null;
         boolean result = false;
         try {
             fis = new FileInputStream(file);
             byte[] buffer = new byte[1024];
             int numRead = 0;
             while ((numRead = fis.read(buffer)) > 0) {
                 fileMessageDigest.update(buffer, 0, numRead);
             }
             fis.close();
             fis = null;
             result = true;
         } catch (Exception e) {
         }
         finally {
             if (fis != null) {
                 try {
                     fis.close();
                     fis = null;
                 } catch (IOException e) {
                 }
             }
         }
         if(!result){
             return null;
         }

         return bufferToHex(fileMessageDigest.digest());
     }

    public synchronized static String getMD5String(byte[] bytes) {
        stringMessageDigest.update(bytes);
        return bufferToHex(stringMessageDigest.digest());
    }

    public static String getStreamMD5(InputStream in) {
        if (in == null)
            return null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                stringMessageDigest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            return null;
        }
        return bufferToHex(stringMessageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuilder buffer = new StringBuilder(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], buffer);
        }
        return buffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuilder buffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
                                              // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        buffer.append(c0);
        buffer.append(c1);
    }

    public static String getStringMd5(String plainText) {

        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());

        } catch (Exception e) {

            return null;

        }

        return encodeHex(md.digest());

    }


    public static String encodeHex(byte[] data) {

        if (data == null) {

            return null;
        }

        final String HEXES = "0123456789abcdef";
        int len = data.length;
        StringBuilder hex = new StringBuilder(len * 2);

        for (int i = 0; i < len; ++i) {

            hex.append(HEXES.charAt((data[i] & 0xF0) >>> 4));
            hex.append(HEXES.charAt((data[i] & 0x0F)));
        }

        return hex.toString();
    }
}
