package com.wind.encrypt;

import com.wind.common.Const;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
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
    public static String encrypt(String str, String key){
        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance(key);
            byte[] input = str.getBytes(Const.UTF8);
            //128位
            byte[] output = md.digest(input);

            s = Hex.encodeHexString(output);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return s;
    }

    public static void main(String[] args) {
        /*String s = encrypt("hello", MD5_KEY);*/
        String s = encrypt("hello", SHA_KEY);
        System.out.println(s);
        System.out.println(s.length());
    }

}
