package org.cls.javautils.encrypt;

import org.cls.javautils.common.Constants;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具类
 * MD5的全称是Message-Digest Algorithm 5（信息-摘要算法）
 * 默认为128bit
 * @author wind
 */
public class Md5Util {
	
	public static final String MD5_KEY = "MD5";

    public static final String SHA_KEY = "SHA1";

    /**
     * md5加密
     * @param str
     * @param key  MD5  SHA1
     * @return
     */
    public static String getMD5Str(String str, String key){
        String s = "";
        try {
            MessageDigest md = MessageDigest.getInstance(key);
            s = Hex.encodeHexString(md.digest(str.getBytes(Constants.UTF8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获得字符串的md5大写值
     *
     * @param str 待加密的字符串
     * @return md5加密后的大写字符串
     */
    public static String getMD5UpperStr(String str) {
        return getMD5Str(str, MD5_KEY).toUpperCase();
    }

    /**
     * 获得文件的md5值
     * @param file 文件对象
     * @return 文件的md5
     */
    public static String getFileMD5Str(File file) {
        String r = "";
        FileInputStream in = null;
        FileChannel ch = null;
        try {
            in = new FileInputStream(file);
            ch = in.getChannel();
            ByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance(MD5_KEY);
            md5.update(byteBuffer);
            r = Hex.encodeHexString(md5.digest());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ch != null) {
                try {
                    ch.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return r;
    }

    /**
     * 获得加盐md5，算法过程是原字符串md5后连接加盐字符串后再进行md5
     *
     * @param str  待加密的字符串
     * @param salt 盐
     * @return 加盐md5
     */
    public static String getMD5AndSalt(String str, String salt) {
        return getMD5Str(getMD5Str(str, MD5_KEY).concat(salt), MD5_KEY);
    }

    public static void main(String[] args) {
        /*String s = encrypt("hello", MD5_KEY);*/
        String s = getMD5Str("hello", SHA_KEY);
        System.out.println(s);
        System.out.println(s.length());
    }

}
