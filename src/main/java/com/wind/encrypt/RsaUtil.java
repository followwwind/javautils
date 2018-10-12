package com.wind.encrypt;

import com.wind.common.StringUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @Title: RsaUtil
 * @Package com.ancda.payservice.util
 * @Description: rsa加密算法
 * @author huanghy
 * @date 2018/7/12 10:24
 * @version V1.0
 */
public class RsaUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final int KEY_SIZE = 2048;
    public static final String SIGN_MD5 = "MD5withRSA";
    public static final String SIGN_RSA2 = "SHA256WithRSA";
    public static final String UTF8 = "utf-8";

    /**
     * 生成rsa密钥对
     *
     * @return
     */
    public static Map<String, String> genKey(int size) {
        Map<String, String> keyMap = new HashMap<>(2);
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(size);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            keyMap.put(PUBLIC_KEY, Base64.encodeBase64String(publicKey.getEncoded()));
            keyMap.put(PRIVATE_KEY, Base64.encodeBase64String(privateKey.getEncoded()));
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyMap;
    }

    /**
     * 生成签名
     * @param privateKey
     * @param content
     * @return
     */
    public static String genSign(String privateKey, String content, String signType, String charset){
        String sign = "";
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(signType);
            signature.initSign(key);
            if(StringUtil.isNotBlank(charset)){
                signature.update(content.getBytes(charset));
            }else{
                signature.update(content.getBytes());
            }
            byte[] result = signature.sign();
            sign = Base64.encodeBase64String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }


    /**
     * 获取签名内容
     * @param params
     * @return
     */
    public static String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        } else {
            params.remove("sign");
            params.remove("sign_type");
            StringBuilder content = new StringBuilder();
            List<String> keys = new ArrayList<>(params.keySet());
            Collections.sort(keys);
            for(int i = 0; i < keys.size(); ++i) {
                String key = keys.get(i);
                String value = params.get(key);
                content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
            }

            return content.toString();
        }
    }

    /**
     * 校验签名
     * @param publicKey
     * @param params
     * @param sign
     * @param charset
     * @return
     */
    public static boolean checkRsa(String publicKey, Map<String, String> params, String sign, String charset){
        String content = getSignCheckContent(params);
        return rsa256CheckContent(publicKey, content, sign, charset);
    }
    

    /**
     * SHA256WithRSA签名校验
     * @param publicKey
     * @param content
     * @param sign
     * @param charset
     * @return
     */
    public static boolean rsa256CheckContent(String publicKey, String content, String sign, String charset){
        return checkRsa(publicKey, content, sign, SIGN_RSA2, charset);
    }


    /**
     * rsa签名校验
     * @param publicKey
     * @param content
     * @param sign
     * @param signType
     * @param charset
     * @return
     */
    public static boolean checkRsa(String publicKey, String content, String sign, String signType, String charset){
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        //为了数据的完整性
        boolean bool = false;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance(signType);
            signature.initVerify(key);
            if(StringUtil.isNotBlank(charset)){
                signature.update(content.getBytes(charset));
            }else{
                signature.update(content.getBytes());
            }
            bool = signature.verify(Base64.decodeBase64(sign));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bool;
    }
}
