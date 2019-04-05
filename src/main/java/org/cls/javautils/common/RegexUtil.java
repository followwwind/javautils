package org.cls.javautils.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author wind
 */
public class RegexUtil {

    private final static String PHONE_NUMBER = "^(13[0-9]|14[579]|15[0-3,5-9]|16[0-9]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    private final static String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private final static String HK_PHONE_NUMBER = "^(5|6|8|9)\\d{7}$";

    private final static String POSITIVE_INTEGER = "^\\d+$";

    private final static String MONEY = "^(\\d+(?:\\.\\d+)?)$";

    private final static String ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";

    /**
     * 校验邮箱
     * @param str
     * @return
     */
    public static boolean checkEmail(String str){
        return checkStr(EMAIL, str);
    }

    /**
     * 校验身份证
     * @param str
     * @return
     */
    public static boolean checkIdCard(String str){
        return checkStr(ID_CARD, str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     *
     * @param str
     * @return
     */
    public static boolean checkPhone(String str){
        return checkStr(PHONE_NUMBER, str);
    }

    /**
     * 校验香港
     * @param str
     * @return
     */
    public static boolean checkHKPhone(String str){
        return checkStr(HK_PHONE_NUMBER, str);
    }

    /**
     * 校验正整数
     * @param str
     * @return
     */
    public static boolean checkPlusInteger(String str){
        return checkStr(POSITIVE_INTEGER, str);
    }

    /**
     * 校验money
     * @param str
     * @return
     */
    public static boolean checkMoney(String str){
        return checkStr(MONEY, str);
    }

    /**
     *
     * @param regex
     * @param str
     * @return
     */
    private static boolean checkStr(String regex, String str){
        if(regex == null || str == null){
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
