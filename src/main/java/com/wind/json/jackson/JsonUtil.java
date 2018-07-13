package com.wind.json.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @fileName JsonUtil
 * @package com.ancda.palmbaby.hm.common.utils
 * @description json工具类
 * @author huanghy
 * @date 2018-05-03 15:39:05
 * @version V1.0
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * json字符串转list数组
     * @param jsonStr
     * @return
     */
    public static <T> List<T> toList(String jsonStr, TypeReference<List<T>> jsonTypeReference){
        try {
            if(jsonStr != null){
                return mapper.readValue(jsonStr,jsonTypeReference);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    /**
     * json字符串转bean对象
     * @param jsonStr
     * @param <T>
     * @return
     */
    public static <T> T toBean(String jsonStr, Class<T> c){
        try {
            if(jsonStr != null){
                return mapper.readValue(jsonStr,c);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    /**
     * 对象序列化json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        String result = null;
        try {
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
