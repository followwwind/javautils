package com.wind.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.List;

/**
 * @package com.wind.json.fastjson
 * @className JsonUtil
 * @note fastjson
 * @author wind
 * @date 2018/7/29 0:20
 */
public class JsonUtil {

    /**
     * java对象序列化json字符串
     * @param obj
     * @param flag true 将对象转换成格式化的json, false 将对象转换成非格式化的json
     * @return
     */
    public static String toJson(Object obj, boolean flag){
        return JSON.toJSONString(obj,flag);
    }


    /**
     * json字符串转bean对象
     * @param jsonStr
     * @param <T>
     * @return
     */
    public static <T> T toBean(String jsonStr, Class<T> c) {
        return JSON.parseObject(jsonStr, c);
    }

    /**
     * json字符串转list数组
     * @param jsonStr
     * @return
     */
    public static <T> List<T> toList(String jsonStr, TypeReference<List<T>> jsonTypeReference){
        return JSON.parseObject(jsonStr, jsonTypeReference);
    }

}
