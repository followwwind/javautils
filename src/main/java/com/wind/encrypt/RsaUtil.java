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
 * @Description: TODO
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




    public static void main(String[] args) {
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVtcGrsBopnFqv/AE2UjXKu5C2tewoRxhvsDdLt1FJNrF8+52Qtqbn428p26yK8kMhiwdAwDw7AfoRrJpsO2ZwsIeMKVGX0hir+ttevlSVaA/i0NF+7B0RCqL8+g5bcTqcCK9kw0dAXcz6nxqT1sVcVCuwJKpTiUnGUd40gkYEtjSD21ijYBXrrx3iXZLrSCI7mMM1RFgF4zPikqXRTOe+wPjprjtgmzyY+vkiu/8f9OiLi1kwhOgHPqskihfjSVaK8O0h6P0QPzzkkRQa3+fafWmuTj95R180KDuZ4IJ9686zZzIX7TGG2Ry933VxItw5lRAlbCal+90BFrtdqraHAgMBAAECggEBAI3kleoG39UedymjHPcCVi+PNKqnpGvMbpG1H8OovOc6amC+DmoRZAIWos62gUO0OAI7xiUNzkhTKFPGFxqL9hzKg75JjybpHy8pdO/IT1zII35jUpwMZ8Q2I6LH0gHDQLyQ0sQa/ZY5EUVctRD4F1jaAgpRxgmk7oKEJ6n26ywcnm8U86S8YVAVjQjtoYbxlTq3L0yPVhGEjF8r0SMC1APSKO0EJ3Euwx16UHYoodfHlIzBcZsVRwPxfJwwxtHZWPyECmkEH8PoUKjge/CYik4THizCJsCOf3/oilYkEcICeuBaxvwlEYzk3WhSQIFLbxjhzpsnI7BcEp0AzV/uOOECgYEAxTcTbb+FzFwO4xLqwOYHdhSDdbNp+NfLHP6fIsaypTWhBix2va75C4n+05zd4qn8aO03tNFUy3KZqRq5XNBPfxR/y9Q88zx0G8a+nJCChY6Ul3u6Wf/9eaYJO4saxUsb8FNVg7nle5IBv4qa63Y/ppO9hIzp//caAQhWixCP5XECgYEAwlWysYrPXIFRJvSlOipl8yA4Kl+bFMgHjguHM1au/HtTXy4r2ruaNAIqaM6G/Tke67zRGHtEdnA3uRQoj8Wx8udG873BTTD3IY6DPPeM/IU7arKaaQJS4p1sc9QsQDE9olChvA4EbFP2ECNGJrKaTxAoXilmpUcaQrSIJP00f3cCgYA4IxBNuim83SuDqZvXIuNW0koFU/fDVLHFZBkqTgMGEfjvB/MY1Vig1zwJQmrKnXZy66titf98Fff1cdz3tXgbhVtHSve6iSfHzE/vwxbUK5zSbe7CtaKSYRfZsiQBqvqd8yqxX1YaUZpbynmEstk1cnKM64ukR9NIHfZ8iU9ckQKBgQCM76vFmL7j/qEFGH3PnjoLxb0V9fo5awwXlwK5V89WKnZ/W7PQUSf3Oe7ZzZYWhVuIaYpXL+ap4p526ki0ZBK278YENQTAX5eKzZkeGQY1iMZbjiXvrBDCaprhselZsJxbYBC7morYqVeVwo84t2SznCs6htn7WYKRB+6IrN1q5wKBgEuEFoNgkA1i1BWPg+ymuguqhmNprRIpX/graxtXNKogLzG5QpI8vDj8umoeg6RpMMHcK6YE0ZDwmCc0x4teUMjzLjhPjGGHEtncSHDM9WmnYIsj2cMhoyIa7UjqbmlGc0tGxJm6vZFAu745NSTKgDmLa8g+wrEqy2lhEvINXrXF";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlbXBq7AaKZxar/wBNlI1yruQtrXsKEcYb7A3S7dRSTaxfPudkLam5+NvKdusivJDIYsHQMA8OwH6EayabDtmcLCHjClRl9IYq/rbXr5UlWgP4tDRfuwdEQqi/PoOW3E6nAivZMNHQF3M+p8ak9bFXFQrsCSqU4lJxlHeNIJGBLY0g9tYo2AV668d4l2S60giO5jDNURYBeMz4pKl0UznvsD46a47YJs8mPr5Irv/H/Toi4tZMIToBz6rJIoX40lWivDtIej9ED885JEUGt/n2n1prk4/eUdfNCg7meCCfevOs2cyF+0xhtkcvd91cSLcOZUQJWwmpfvdARa7Xaq2hwIDAQAB";
        System.out.println(privateKey);
        System.out.println(publicKey);
        String content = String.valueOf(System.currentTimeMillis());
        System.out.println(content);
        String sign = genSign(privateKey, content, SIGN_RSA2, UTF8);
        System.out.println(sign);
        boolean flag = checkRsa(publicKey, content, sign, SIGN_RSA2, UTF8);
        System.out.println(flag);
    }

}
