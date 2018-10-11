package com.wind.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Title: DateUtil
 * @Package com.wind.common
 * @Description: 日期工具类集合
 * @author wind
 * @date 2018/10/11 9:24
 * @version V1.0
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_STR = "yyyy-MM-dd";

    public static final String DATETIME_MS = "yyyyMMddHHmmssSSS";

    public static final String DATE_SLASH_STR = "yyyy/MM/dd";

    public final static int SECOND = 1000;

    public final static int MINUTE = 60 * SECOND;

    public final static int HOUR = 60 * MINUTE;

    public final static int DAY = 24 * HOUR;


    /**
     * 格式化字符串日期，转换成Date
     * @param date  字符串日期
     * @param pattern 默认 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parse(String date, String pattern){
        String p = pattern == null ? DATE_TIME : pattern;
        DateFormat dateFormat = new SimpleDateFormat(p);
        Date d = null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            logger.warn("parse date string error, date:" + date + ", pattern:" + p, e);
        }
        return d;
    }

    /**
     * 日期按照指定格式转换成字符串
     * @param date  日期
     * @param pattern 默认 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(Date date, String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern == null ? DATE_TIME : pattern);
        return dateFormat.format(date);
    }


    /**
     * 获取日期
     * @param date
     * @return
     */
    public static Date getDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取星期
     * @param date
     * @return
     */
    public static int getWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }


    /**
     * 返回当前时间day天之后（day>0）或day天之前（day<0）的时间
     * @param date 日期
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day){
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
    public static Date addMonth(Date date, int month) {
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
    public static Date addYear(Date date, int year) {
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
     */
    public static double daysBetween(Date start, Date end) throws IllegalArgumentException{
        if(start == null || end == null){
            throw new IllegalArgumentException("The date must not be null. start:" + start + ", end:" + end);
        }
        long endTime = end.getTime();
        long startTime = start.getTime();
        return (endTime - startTime) / (DAY);
    }



    public static void main(String[] args) {
        Date end = new Date();
        System.out.println(format(end, DATE_STR));
        Date start = parse("2018-08-03", DATE_STR);
        System.out.println(start.getTime());
        System.out.println(daysBetween(start, end));
    }



}
