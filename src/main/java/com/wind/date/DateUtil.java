package com.wind.date;

import com.wind.common.Const;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类集合
 * @author wind
 */
public class DateUtil {


    /**
     * 格式化字符串日期，转换成Date
     * @param date  字符串日期
     * @param pattern 默认 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parseDate(String date, String pattern){
        pattern = pattern == null ? Const.DATE_TIME : pattern;
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date d = null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 日期按照指定格式转换成字符串
     * @param date  日期
     * @param pattern 默认 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateStr(Date date, String pattern){
        pattern = pattern == null ? Const.DATE_TIME : pattern;
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }


    /**
     * 返回当前时间day天之后（day>0）或day天之前（day<0）的时间
     * @param date 日期
     * @param day
     * @return
     */
    public static Date getDateD(Date date, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 返回当前时间month个月之后（month>0）或month个月之前（month<0）的时间
     *
     * @param date
     * @param month
     * @return
     */
    public static Date getDateM(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 返回当前时间year年之后（year>0）或year年之前（year<0）的时间
     *
     * @param date
     * @param year
     * @return
     */
    public static Date getDateY(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间的天数差
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date start, Date end){
        return Integer.valueOf(String.valueOf((end.getTime() - start.getTime())/(1000*3600*24)));
    }


    public static void main(String[] args) {
        Date date = getDateD(new Date(), 1);
        System.out.println(date);
    }



}
