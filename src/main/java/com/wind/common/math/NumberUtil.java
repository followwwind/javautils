package com.wind.common.math;

import java.math.BigDecimal;

/**
 * 数字工具类
 * @author wind
 */
public class NumberUtil {

    /**
     * 获取double
     * @param d double数据
     * @param newScale 保留几位小数点
     * @return
     */
    public static double getDouble(double d, int newScale){
        BigDecimal b = new BigDecimal(d);
        return b.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取百分比
     * @param free
     * @param total
     * @return
     */
    public static double getPercent(long free, long total){
        return getDouble((1 - free * 1.0 / total) * 100, 2);
    }
}
