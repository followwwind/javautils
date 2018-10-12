package com.wind.crypt;

import com.wind.encrypt.CryptoUtil;
import com.wind.encrypt.RsaUtil;
import org.junit.Test;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @Title: CryptTest
 * @Package com.wind.crypt
 * @Description: TODO
 * @author wind
 * @date 2018/10/11 10:34
 * @version V1.0
 */
public class CryptTest {

    @Test
    public void testPBKDF2(){
        try {
            String[] arr = CryptoUtil.createHash("123456");
            System.err.println("password: " + arr[0]);
            System.err.println("salt: " + arr[1]);
            System.out.println(CryptoUtil.validatePassword("123456", arr[0], arr[1]));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRsa(){
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVtcGrsBopnFqv/AE2UjXKu5C2tewoRxhvsDdLt1FJNrF8+52Qtqbn428p26yK8kMhiwdAwDw7AfoRrJpsO2ZwsIeMKVGX0hir+ttevlSVaA/i0NF+7B0RCqL8+g5bcTqcCK9kw0dAXcz6nxqT1sVcVCuwJKpTiUnGUd40gkYEtjSD21ijYBXrrx3iXZLrSCI7mMM1RFgF4zPikqXRTOe+wPjprjtgmzyY+vkiu/8f9OiLi1kwhOgHPqskihfjSVaK8O0h6P0QPzzkkRQa3+fafWmuTj95R180KDuZ4IJ9686zZzIX7TGG2Ry933VxItw5lRAlbCal+90BFrtdqraHAgMBAAECggEBAI3kleoG39UedymjHPcCVi+PNKqnpGvMbpG1H8OovOc6amC+DmoRZAIWos62gUO0OAI7xiUNzkhTKFPGFxqL9hzKg75JjybpHy8pdO/IT1zII35jUpwMZ8Q2I6LH0gHDQLyQ0sQa/ZY5EUVctRD4F1jaAgpRxgmk7oKEJ6n26ywcnm8U86S8YVAVjQjtoYbxlTq3L0yPVhGEjF8r0SMC1APSKO0EJ3Euwx16UHYoodfHlIzBcZsVRwPxfJwwxtHZWPyECmkEH8PoUKjge/CYik4THizCJsCOf3/oilYkEcICeuBaxvwlEYzk3WhSQIFLbxjhzpsnI7BcEp0AzV/uOOECgYEAxTcTbb+FzFwO4xLqwOYHdhSDdbNp+NfLHP6fIsaypTWhBix2va75C4n+05zd4qn8aO03tNFUy3KZqRq5XNBPfxR/y9Q88zx0G8a+nJCChY6Ul3u6Wf/9eaYJO4saxUsb8FNVg7nle5IBv4qa63Y/ppO9hIzp//caAQhWixCP5XECgYEAwlWysYrPXIFRJvSlOipl8yA4Kl+bFMgHjguHM1au/HtTXy4r2ruaNAIqaM6G/Tke67zRGHtEdnA3uRQoj8Wx8udG873BTTD3IY6DPPeM/IU7arKaaQJS4p1sc9QsQDE9olChvA4EbFP2ECNGJrKaTxAoXilmpUcaQrSIJP00f3cCgYA4IxBNuim83SuDqZvXIuNW0koFU/fDVLHFZBkqTgMGEfjvB/MY1Vig1zwJQmrKnXZy66titf98Fff1cdz3tXgbhVtHSve6iSfHzE/vwxbUK5zSbe7CtaKSYRfZsiQBqvqd8yqxX1YaUZpbynmEstk1cnKM64ukR9NIHfZ8iU9ckQKBgQCM76vFmL7j/qEFGH3PnjoLxb0V9fo5awwXlwK5V89WKnZ/W7PQUSf3Oe7ZzZYWhVuIaYpXL+ap4p526ki0ZBK278YENQTAX5eKzZkeGQY1iMZbjiXvrBDCaprhselZsJxbYBC7morYqVeVwo84t2SznCs6htn7WYKRB+6IrN1q5wKBgEuEFoNgkA1i1BWPg+ymuguqhmNprRIpX/graxtXNKogLzG5QpI8vDj8umoeg6RpMMHcK6YE0ZDwmCc0x4teUMjzLjhPjGGHEtncSHDM9WmnYIsj2cMhoyIa7UjqbmlGc0tGxJm6vZFAu745NSTKgDmLa8g+wrEqy2lhEvINXrXF";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlbXBq7AaKZxar/wBNlI1yruQtrXsKEcYb7A3S7dRSTaxfPudkLam5+NvKdusivJDIYsHQMA8OwH6EayabDtmcLCHjClRl9IYq/rbXr5UlWgP4tDRfuwdEQqi/PoOW3E6nAivZMNHQF3M+p8ak9bFXFQrsCSqU4lJxlHeNIJGBLY0g9tYo2AV668d4l2S60giO5jDNURYBeMz4pKl0UznvsD46a47YJs8mPr5Irv/H/Toi4tZMIToBz6rJIoX40lWivDtIej9ED885JEUGt/n2n1prk4/eUdfNCg7meCCfevOs2cyF+0xhtkcvd91cSLcOZUQJWwmpfvdARa7Xaq2hwIDAQAB";
        System.out.println(privateKey);
        System.out.println(publicKey);
        String content = String.valueOf(System.currentTimeMillis());
        System.out.println(content);
        String sign = RsaUtil.genSign(privateKey, content, RsaUtil.SIGN_RSA2, RsaUtil.UTF8);
        System.out.println(sign);
        boolean flag = RsaUtil.checkRsa(publicKey, content, sign, RsaUtil.SIGN_RSA2, RsaUtil.UTF8);
        System.out.println(flag);
    }
}
