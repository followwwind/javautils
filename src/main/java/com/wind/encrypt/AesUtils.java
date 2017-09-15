package com.wind.encrypt;

import com.wind.common.ByteUtils;
import com.wind.common.Const;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * AES加密工具类
 * AES已经变成目前对称加密中最流行算法之一；AES可以使用128、192、和256位密钥，并且用128位分组加密和解密数据
 */

public class AesUtils {


    /**
     * des加密
     * @param str 待加密对象
     * @param key 密钥 长度为8的倍数
     * @return
     */
    public static byte[] encrypt(String str,String key) {
        byte[] bytes = null;

        try {
            Cipher cipher = init(Cipher.ENCRYPT_MODE, key);
            bytes = cipher.doFinal(str.getBytes(Const.UTF8));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    /**
     * des解密
     * @param bytes 待解密对象
     * @param key 密钥 长度为8的倍数
     * @return
     */
    public static String decrypt(byte[] bytes,String key) {
        String str = null;
        try {
            Cipher cipher = init(Cipher.DECRYPT_MODE, key);
            str = new String(cipher.doFinal(bytes), Const.UTF8);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str;
    }




    private static Cipher init(int mode, String key){
        Cipher cipher = null;
        try {
            KeyGenerator kGen = KeyGenerator.getInstance(Const.AES_KEY);

            kGen.init(128, new SecureRandom(key.getBytes()));

            cipher = Cipher.getInstance(Const.AES_KEY);
            cipher.init(mode, new SecretKeySpec(kGen.generateKey().getEncoded(), Const.AES_KEY));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    public static void main(String[] args) {
        String str = "hello world";
        String key = "123";
        byte[] bytes = encrypt(str, key);

        String s = decrypt(bytes, key);
        System.out.println(s);
    }

}
