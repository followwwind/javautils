package org.cls.javautils.json.jsonlib;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;

/**
 * @package com.wind.json.jsonlib
 * @className JsonUtil
 * @note json-lib
 * @author wind
 * @date 2018/7/29 0:24
 */
public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * java对象序列化返回json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        try {
            if(obj instanceof Collection){
                return JSONArray.fromObject(obj).toString();
            }else{
                return JSONObject.fromObject(obj).toString();
            }
        } catch (Exception e) {
            logger.warn("write to json string error:" + obj, e);
            return null;
        }
    }

    /**
     * json字符串反序列化转换成java bean对象
     * @param json
     * @param c
     * @return
     */
    public static <T> T toBean(String json, Class<T> c){
        JSONObject jsonObject = JSONObject.fromObject(json);
        Object obj = JSONObject.toBean(jsonObject, c);
        return (T)obj;
    }
}
