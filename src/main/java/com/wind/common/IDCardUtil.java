package com.wind.common;


import com.beust.jcommander.ParameterException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中华人民共和国身份证号码工具累
 */
public class IDCardUtil {

    /**
     * 根据身份证号获取用户年龄
     *
     * @param card 身份证号
     * @return 返回结果 身份证不合法则返回为空
     */
    public static String getAge(String card) throws ParameterException {

        // 如果身份证为空或者长度小于18 则返回空
        if (StringUtil.isBlank(card) || card.length() < 18) {
            throw new ParameterException("身份证号码不正确");
        }

        //身份证第七位到第十四位表示出生年月日
        int birthYear = Integer.parseInt(card.substring(6, 10));
        int birthMonth = Integer.parseInt(card.substring(10, 12));
        int birthDay = Integer.parseInt(card.substring(12, 14));

        //当前年月日
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        int age = nowYear - birthYear;

        //当前月小于出生月 或 当前月等于出生月但当前日期小于出生日期  年龄减一
        if (nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)) {
            age--;
        }

        return age + "";
    }

    /**
     * 根据身份证号获取用户性别
     *
     * @param card 身份证号号码
     * @return M - 男性   F - 女性
     */
    public static String getGender(String card) {

        // 如果身份证为空或者长度小于18 则返回空
        if (StringUtil.isBlank(card) || card.length() < 18) {
            throw new ParameterException("身份证号码不正确");
        }

        //第十七位奇数是男性，偶数是女性
        if (Integer.parseInt(card.substring(16, 17)) % 2 == 0) {
            return "F";
        } else {
            return "M";
        }
    }

    /**
     * 18位身份证校验规则  严格校验
     * <p>
     * 第十八位数字的计算方法为：
     * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2.将这17位数字和系数相乘的结果相加。
     * 3.用加出来和除以11，看余数是多少？
     * 4余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     *
     * @param idCard 身份证号码
     * @return true -> 校验通过。false --> 校验失败
     */
    public static boolean isIdCardLegal(String idCard) {

        if (StringUtil.isBlank(idCard)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");

        Matcher matcher = pattern.matcher(idCard);

        int[] prefix = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

        int[] suffix = new int[]{1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        if (matcher.matches()) {
            if (cityMap.get(idCard.substring(0, 2)) == null) {
                return false;
            }
            int idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (int i = 0; i < 17; i++) {
                idCardWiSum += Integer.valueOf(idCard.substring(i, i + 1)) * prefix[i];
            }

            int idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            String idCardLast = idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod == 2) {
                if (idCardLast.equalsIgnoreCase("x")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast.equals(suffix[idCardMod] + "")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 身份证号脱敏处理
     *
     * @param idCard 身份证号
     * @return
     */
    public static String hideCard(String idCard) throws ParameterException {

        // 为空校验
        if (StringUtil.isBlank(idCard))
            throw new ParameterException("身份证号码不正确");

        int length = idCard.length();

        if (length < 15)
            throw new ParameterException("身份证号码不正确");
        // 身份证号前四位+**********+身份证号最后四位
        return idCard.substring(0, 4) + "**********" + idCard.substring(length - 4, length);
    }


    /**
     * 身份证省份对应编码
     */
    private static Map<String, String> cityMap = new HashMap<String, String>() {
        {
            put("11", "北京");
            put("12", "天津");
            put("13", "河北");
            put("14", "山西");
            put("15", "内蒙古");

            put("21", "辽宁");
            put("22", "吉林");
            put("23", "黑龙江");

            put("31", "上海");
            put("32", "江苏");
            put("33", "浙江");
            put("34", "安徽");
            put("35", "福建");
            put("36", "江西");
            put("37", "山东");

            put("41", "河南");
            put("42", "湖北");
            put("43", "湖南");
            put("44", "广东");
            put("45", "广西");
            put("46", "海南");

            put("50", "重庆");
            put("51", "四川");
            put("52", "贵州");
            put("53", "云南");
            put("54", "西藏");

            put("61", "陕西");
            put("62", "甘肃");
            put("63", "青海");
            put("64", "宁夏");
            put("65", "新疆");

            put("71", "台湾");
            put("81", "香港");
            put("82", "澳门");
        }
    };
}