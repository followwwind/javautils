package com.wind.common;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author wind
 */
public class RegexUtil {

    /**
     * 校验邮箱
     * @param email 邮箱
     * @return
     */
    public static boolean isEmail(String email){
        boolean flag = false;
        if(email != null){
            flag = email.matches(Const.EMAIL);
        }

        return flag;
    }

    /**
     * 校验手机号码
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum){
        boolean flag = false;
        if(phoneNum != null){
            flag = phoneNum.matches(Const.PHONE_NUM);
        }
        return flag;
    }

    /**
     * 校验中文
     * @param chinese
     * @return
     */
    public static boolean isChinese(String chinese){
        boolean flag = false;
        if(chinese != null){
            flag = chinese.matches(Const.CHINESE);
        }
        return flag;
    }

    /**
     * 判断是否为数字
     * @param value
     * @return
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile(Const.NUMBER);
        return pattern.matcher(value).matches();
    }


    public static boolean isNumber(String value){
        Pattern pattern = Pattern.compile(Const.NUMBER);
        return pattern.matcher(value).matches();
    }

    /**
     * 判断是否为浮点数
     * @param value
     * @return
     */
    public static boolean isFloat(String value) {
        Pattern pattern = Pattern.compile(Const.FLOAT);
        return pattern.matcher(value).matches();
    }

    /**
     * 校验身份证
     * @param id
     * @return
     */
    public static boolean isIdCard(String id){
        Pattern pattern = Pattern.compile(Const.IDCARD);
        return pattern.matcher(id).matches();

    }



    public static void main(String[] args) {
        System.out.println(isPhoneNum("18771933975"));
    }
}
