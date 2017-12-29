package com.wind.idcard;

import com.wind.common.Const;
import com.wind.common.RegexUtil;

import java.util.Map;

/**
 * 身份证前6位【ABCDEF】为行政区划数字代码（简称数字码）说明（参考《GB/T 2260-2007 中华人民共和国行政区划代码》）：
 * 该数字码的编制原则和结构分析，它采用三层六位层次码结构，按层次分别表示我国各省（自治区，直辖市，特别行政区）、市（地区，自治州，盟）、
 * 县（自治县、县级市、旗、自治旗、市辖区、林区、特区）。
 * 数字码码位结构从左至右的含义是：
 * 第一层为AB两位代码表示省、自治区、直辖市、特别行政区；
 * 第二层为CD两位代码表示市、地区、自治州、盟、直辖市所辖市辖区、县汇总码、省（自治区）直辖县级行政区划汇总码，其中：
 * ——01~20、51~70表示市，01、02还用于表示直辖市所辖市辖区、县汇总码；
 * ——21~50表示地区、自治州、盟；
 * ——90表示省（自治区）直辖县级行政区划汇总码。
 * 第三层为EF两位表示县、自治县、县级市、旗、自治旗、市辖区、林区、特区，其中：
 * ——01~20表示市辖区、地区（自治州、盟）辖县级市、市辖特区以及省（自治区）直辖县级行政区划中的县级市，01通常表示辖区汇总码；
 * ——21~80表示县、自治县、旗、自治旗、林区、地区辖特区；
 * ——81~99表示省（自治区）辖县级市。
 *
 *
 * --15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。
 * --18位身份证号码：第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。
 *
 *
 * 身份证工具类
 * @author wind
 */
public class IdCardUtil {

    static {

    }

    /**
     *
     * @param idCard
     * @return
     */
    public static Map<String, String> takeInfo(String idCard){
        Map<String, String> map = null;
        if(RegexUtil.isIdCard(idCard)){
            if(idCard != null && idCard.length() == Const.IDCARD_LEN18){

            }else if(idCard != null && idCard.length() == Const.IDCARD_LEN15){

            }
        }

        return map;
    }

    /**
     * 前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
     * 第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
     * 第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     * @param idCard
     * @return
     */
    private static Map<String, String> getLen18Info(String idCard){
        Map<String, String> map = null;

        return map;
    }

    /**
     * --15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。
     * @param idCard
     * @return
     */
    private static Map<String, String> getLen15Info(String idCard){
        Map<String, String> map = null;

        return map;
    }

}
