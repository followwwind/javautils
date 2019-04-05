package org.cls.javautils.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @package com.wind.json.jackson
 * @className JsonUtil
 * @note jackson
 * @author wind
 * @date 2018/7/29 0:23
 */
public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper;

    /**
     * 获取对象
     */
    public static ObjectMapper getInstance() {
        if (mapper == null){
            mapper = new ObjectMapper();

            // 允许单引号、允许不带引号的字段名称
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            //在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

            // 空值处理为空串
            mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
                @Override
                public void serialize(Object value, JsonGenerator jsonGenerator,
                                      SerializerProvider provider) throws IOException{
                    jsonGenerator.writeString("");
                }
            });


            // 设置时区 getTimeZone("GMT+8:00")
            mapper.setTimeZone(TimeZone.getDefault());
        }
        return mapper;
    }

    /**
     * json字符串转list数组
     * @param jsonStr
     * @return
     */
    public static <T> List<T> toList(String jsonStr, TypeReference<List<T>> jsonTypeReference){
        try {
            if(jsonStr != null){
                return getInstance().readValue(jsonStr, jsonTypeReference);
            }
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonStr, e);
        }
        return new ArrayList<>();
    }

    /**
     * json字符串转bean对象
     * @param jsonStr
     * @param <T>
     * @return
     */
    public static <T> T toBean(String jsonStr, Class<T> c) {
        try {
            if(jsonStr != null){
                return getInstance().readValue(jsonStr, c);
            }
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonStr, e);
        }
        return null;
    }

    

    /**
     * java对象序列化json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        String result = null;
        try {
            result = getInstance().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.warn("write to json string error:" + obj, e);
        }
        return result;
    }
}
