package org.cls.javautils.common;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类集合
 */
public class DateUtil extends DateUtils {

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
     *
     * @param date    字符串日期
     * @param pattern 默认 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parse(String date, String pattern) {
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
     *
     * @param date    日期
     * @param pattern 默认 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern == null ? DATE_TIME : pattern);
        return dateFormat.format(date);
    }


    /**
     * 获取日期
     *
     * @param date
     * @return
     */
    public static Date getDate(Date date) {
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
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }


    /**
     * 返回当前时间day天之后（day>0）或day天之前（day<0）的时间
     *
     * @param date 日期
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
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
     * 计算两个日期之间的毫秒数
     *
     * @param start 开始事件
     * @param end   截止事件
     * @return 毫秒
     */
    public static long between(Date start, Date end) throws IllegalArgumentException {
        if (start == null || end == null) {
            throw new IllegalArgumentException("The date must not be null. start:" + start + ", end:" + end);
        }
        return end.getTime() - start.getTime();
    }
}
