package com.wind.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 字符串工具类
 * @author wind
 */
public class StringUtil {

    private static AtomicLong next = new AtomicLong(1);

    /**
     * 判断字符串是否为null或空字符串
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        return str != null && "".equals(str.trim());
    }

    /**
     * 获取32位的UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成一个13位数的唯一id
     * @return
     */
    public static long getPKNum(){
        return next.getAndIncrement() + System.currentTimeMillis();
    }

    /**
     * 生成一个13位数的唯一字符串id
     * @return
     */
    public static String getPkStr(){
        return String.valueOf(getPKNum());
    }

    /**
     * url编码
     * @param str
     * @return
     */
    public static String encodeUrl(String str){
        String s = null;
        try {
            s = URLEncoder.encode(str, Const.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return s;
    }

    /**
     * url解码
     * @param str
     * @return
     */
    public static String decodeUrl(String str){
        String s = null;
        try {
            s = URLDecoder.decode(str, Const.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * 字符串分隔 StringTokenizer效率是三种分隔方法中最快的
     * @param str
     * @param sign
     * @return
     */
    public static String[] split(String str, String sign){
        if(str == null){
            return new String[]{};
        }
        StringTokenizer token = new StringTokenizer(str,sign);
        String[] strArr = new String[token.countTokens()];
        int i = 0;
        while(token.hasMoreElements()){
            strArr[i] = token.nextElement().toString();
            i++;
        }
        return strArr;
    }

    /**
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     *
     * @param src
     * 要截取的字符串
     * @param startIdx
     * 开始坐标（包括该坐标)
     * @param endIdx
     * 截止坐标（包括该坐标）
     * @return
     */
    public static String substring(String src, int startIdx, int endIdx) {
        byte[] b = src.getBytes();
        StringBuilder sb = new StringBuilder();
        for (int i = startIdx; i <= endIdx; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }

    /**
     * 将数据库列名翻译成java驼峰命名的类成员字段名
     * @param colName 数据库列名
     * @param flag 首字母小写为false， 大写为true
     * @return
     */
    public static String getCamelCase(String colName, boolean flag){
        String str = colName;
        if(colName != null){
            String[] strs = StringUtil.split(colName, Const.UNDERLINE);
            str = "";
            for(int i = 0; i < strs.length; i++){
                String s = strs[i];
                if(i == 0){
                    if(flag){
                        s = getFirst(s, true);
                    }
                    str += s;
                }else{
                    int len = s.length();
                    if(len == 1){
                        str += s.toUpperCase();
                    }else{
                        str += (s.substring(0, 1).toUpperCase() + s.substring(1));
                    }
                }
            }
        }
        return str;
    }

    /**
     * 将单词首字母变大小写
     * @param str
     * @param flag true变大写， false变小写
     * @return
     */
    public static String getFirst(String str, boolean flag){
        String s = "";
        if(str != null && str.length() > 1){
            String first;
            if(flag){
                first = str.substring(0, 1).toUpperCase();
            }else{
                first = str.substring(0, 1).toLowerCase();
            }

            s += (first + str.substring(1));
        }

        return s;
    }




    public static void main(String[] args) {
        System.out.println(getFirst("a", true));
    }

}
