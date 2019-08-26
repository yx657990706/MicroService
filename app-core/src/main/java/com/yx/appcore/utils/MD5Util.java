package com.yx.appcore.utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E','F'};

    private static final String CHAR_SET = "ISO-8859-1";
    /**
     * The jce MD5 message digest generator.
     */
    private static MessageDigest md5;

    public static final String encodeString(String string) throws RuntimeException {
        return byteToHex(digestString(string, null));
    }

    /**
     * Retrieves a hexidecimal character sequence representing the MD5 digest of
     * the specified character sequence, using the specified encoding to first
     * convert the character sequence into a byte sequence. If the specified
     * encoding is null, then ISO-8859-1 is assumed
     *
     * @param string   the string to encode.
     * @param encoding the encoding used to convert the string into the byte sequence
     *                 to submit for MD5 digest
     * @return a hexidecimal character sequence representing the MD5 digest of
     * the specified string
     * @throws RuntimeException if an MD5 digest algorithm is not available through the
     *                          java.security.MessageDigest spi or the requested encoding is
     *                          not available
     */
    public static final String encodeString(String string, String encoding) throws RuntimeException {
        return byteToHex(digestString(string, encoding));
    }

    /**
     * Retrieves a byte sequence representing the MD5 digest of the specified
     * character sequence, using the specified encoding to first convert the
     * character sequence into a byte sequence. If the specified encoding is
     * null, then ISO-8859-1 is assumed.
     *
     * @param string   the string to digest.
     * @param encoding the character encoding.
     * @return the digest as an array of 16 bytes.
     * @throws RuntimeException if an MD5 digest algorithm is not available through the
     *                          java.security.MessageDigest spi or the requested encoding is
     *                          not available
     */
    public static byte[] digestString(String string, String encoding) throws RuntimeException {

        byte[] data;

        if (encoding == null) {
            encoding = CHAR_SET;
        }

        try {
            data = string.getBytes(encoding);
        } catch (UnsupportedEncodingException x) {
            throw new RuntimeException(x.toString());
        }

        return digestBytes(data);
    }

    /**
     * Retrieves a byte sequence representing the MD5 digest of the specified
     * byte sequence.
     *
     * @param data the data to digest.
     * @return the MD5 digest as an array of 16 bytes.
     * @throws RuntimeException if an MD5 digest algorithm is not available through the
     *                          java.security.MessageDigest spi
     */
    public static final byte[] digestBytes(byte[] data) throws RuntimeException {

        synchronized (MD5Util.class) {
            if (md5 == null) {
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e.toString());
                }
            }

            return md5.digest(data);
        }
    }

    private static final char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    public static String byteToHex(byte[] b) {

        int len = b.length;
        char[] s = new char[len * 2];

        for (int i = 0, j = 0; i < len; i++) {
            int c = ((int) b[i]) & 0xff;

            s[j++] = HEXCHAR[c >> 4 & 0xf];
            s[j++] = HEXCHAR[c & 0xf];
        }

        return new String(s);
    }

    public static String getFileMd5(String filename) throws Exception {
        return getFileMd5(filename, null);
    }

    public static String getFileMd5(String filename, String encoding) throws Exception {
        File f = new File(filename);

        if (!f.exists()) {
            return "";
        }
        MessageDigest digest;
        try (InputStream is = new FileInputStream(f)) {
            byte[] buffer = new byte[1024];
            digest = MessageDigest.getInstance("MD5");

            int count;
            while ((count = is.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            is.close();
        }
        byte[] md5sum = digest.digest();
        String output = new String(encodeHex(md5sum)).toLowerCase();
        return output;
    }

    public static String getFileMd5(File file) throws Exception {
        return getFileMd5(file, null);
    }

    public static String getMultipartFileMd5(MultipartFile file, String attachStr, String encoding) throws Exception {
        encoding = encoding == null ? CHAR_SET : encoding;
        InputStream is = file.getInputStream();
        byte[] buffer = new byte[1024];
        MessageDigest digest = MessageDigest.getInstance("MD5");

        int count;
        while ((count = is.read(buffer)) > 0) {
            digest.update(buffer, 0, count);
        }
        is.close();
        digest.update(file.getOriginalFilename().getBytes(CHAR_SET), 0, file.getOriginalFilename().getBytes(CHAR_SET).length);
        digest.update(attachStr.getBytes(encoding), 0,
                attachStr.getBytes(encoding).length);
        byte[] md5sum = digest.digest();
        String output = new String(encodeHex(md5sum)).toLowerCase();

        return output;
    }

    public static String getFileMd5(File file, String encoding) throws Exception {
        MessageDigest digest;
        try (InputStream is = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            digest = MessageDigest.getInstance("MD5");

            int count;
            while ((count = is.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            is.close();
        }
        byte[] md5sum = digest.digest();
        String output = new String(encodeHex(md5sum)).toLowerCase();

        return output;
    }

    public static String getStringMD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] str = null;
        try {
            byte[] strTemp = s.getBytes("UTF-8");
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                // 将每个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new String(str);
    }

    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        int j = 0;
        for (int i = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

    public static String toMD5(String text, String charset) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        try {
            msgDigest.update(text.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("System doesn't support your EncodingException.");
        }
        byte[] bytes = msgDigest.digest();
        String md5Str = new String(encodeHex(bytes));
        return md5Str;
    }

    public static String toMD5(String text) {
        return toMD5(text, "utf-8");
    }

    public static String getFileMd5(String filename, String attachStr, String encoding) throws Exception {
        File f = new File(filename);
        if (!f.exists()) {
            return "";
        }
        MessageDigest digest;
        try (InputStream is = new FileInputStream(f)) {
            byte[] buffer = new byte[1024];
            digest = MessageDigest.getInstance("MD5");

            int count;
            while ((count = is.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            is.close();
        }
        byte[] md5sum = digest.digest();
        String output = new String(encodeHex(md5sum)).toLowerCase();

        return output;
    }

    public static String getKeyFileMd5(String filename, String attachStr, String encoding) throws Exception {
        File f = new File(filename);
        if (!f.exists()) {
            return "";
        }
        MessageDigest digest;
        try (InputStream is = new FileInputStream(f)) {
            byte[] buffer = new byte[1024];
            digest = MessageDigest.getInstance("MD5");
            digest.update(attachStr.getBytes(CHAR_SET), 0, attachStr.getBytes(CHAR_SET).length);
            int count;
            while ((count = is.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            is.close();
        }
        byte[] md5sum = digest.digest();
        String output = new String(encodeHex(md5sum)).toLowerCase();
        return output;
    }

    public static void main(String[] args) {
        String a = "q2w456e565我的加载";
        System.out.println("===>>toMD5:"+toMD5(a));
        System.out.println("===>>getStringMD5:"+MD5Util.getStringMD5(a));
    }
}

