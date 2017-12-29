package com.wind.encrypt;

import com.wind.common.ByteUtil;
import com.wind.common.Const;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具类
 * MD5的全称是Message-Digest Algorithm 5（信息-摘要算法）
 * 默认为128bit
 */
public class Md5Util {

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

            s = ByteUtil.byteToHex(output);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return s;
    }



    public static void main(String[] args) {
        String s = encrypt("hello", Const.MD5_KEY);
//        String s = encrypt("hello", Const.SHA_KEY);
        System.out.println(s);
        System.out.println(s.length());
    }

}
