package com.wind.encrypt;

import com.wind.common.Const;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * DES加密工具类
 * ES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数  密匙长度是56位
 * @author wind
 */
public class DesUtil {

    public final static String DES_KEY = "DES";

    /**
     * des加密
     * @param str 待加密对象
     * @param key 密钥 长度为8的倍数
     * @return
     */
    public static String encrypt(String str,String key) {
        String encryptedData = null;

        try {
            Cipher cipher = init(Cipher.ENCRYPT_MODE, key);
            // 加密，并把字节数组编码成字符串 sun.misc.BASE64Encoder不支持jdk9
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(str.getBytes()));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return encryptedData;
    }

    /**
     * des解密
     * @param str 待解密对象
     * @param key 密钥 长度为8的倍数
     * @return
     */
    public static String decrypt(String str,String key) {
        String decryptedData = null;
        try {
            Cipher cipher = init(Cipher.DECRYPT_MODE, key);
            // 把字符串解码为字节数组，并解密
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(str)));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decryptedData;
    }

    /**
     * 初始化加解密对象
     * @param mode
     * @param key 密钥 长度为8的倍数
     * @return
     */
    private static Cipher init(int mode, String key){
        Cipher cipher = null;
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_KEY);
            SecretKey secretKey = keyFactory.generateSecret(desKey);

            // 加解密对象
            cipher = Cipher.getInstance(DES_KEY);
            cipher.init(mode, secretKey, sr);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    public static void main(String[] args) {
        String key = "12345678";
        String str = "world";

        String enStr = encrypt(str, key);
        System.out.println(enStr);

        String deStr = decrypt(enStr, key);
        System.out.println(deStr);
    }


}
