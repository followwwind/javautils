package org.cls.javautils.json;

import org.cls.javautils.AAA;
import org.cls.javautils.BeanObj;
import org.cls.javautils.json.gson.GsonUtil;
import org.cls.javautils.json.jsonlib.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.wind.json
 * @className JsonTest
 * @note json工具类测试
 * json-lib 依赖第三方包多，json转bean无法识别date，大数量转换性能差，停止维护，只支持到jdk5
 * jackson 依赖jar包比较少，json转bean日期类型，默认只识别yyyy-MM-dd，扩展性好，大数量转换性能不如fastjson，gson
 * fastjson ali开源，无额外依赖，fastjson性能比较快
 * gson google，无依赖，Gson是目前功能最全的Json解析神器，Gson在功能上面无可挑剔，但是性能上面比FastJson有所差距
 * @author wind
 * @date 2018/7/29 22:38
 */
public class JsonTest {

    private String jsonStr = "{name:'123', age:18, date:'2018-07-27 12:00:00', list:['123', '333'], " +
            "map:{name:'333', age:18}, objList:[{code:1,value:'1'}]}";

    private String jsonStr2 = "{name:'123', age:18, date:null, list:['123', '333'], " +
            "map:{name:'333', age:18}, objList:[{code:1,value:'1'}]}";

    private int count = 1000000;

    List<BeanObj> list = new ArrayList<>();

    @Before
    public void init(){
        for(int i = 0; i < count; i++){
            BeanObj obj = new BeanObj("");
            list.add(obj);
        }
    }
    /**
     * json-lib默认处理日期格式有问题
     */
    @Test
    public void testJsonLib(){
        long start = System.currentTimeMillis();
        BeanObj obj = JsonUtil.toBean(jsonStr, BeanObj.class);
        System.out.println(obj.getDate());
        System.out.println(JsonUtil.toJson(obj));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void testJsonLib2(){
        long start = System.currentTimeMillis();
        for(int i = 0; i < count; i++){
            BeanObj obj = JsonUtil.toBean(jsonStr, BeanObj.class);
//            System.out.println(obj.getDate());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        long start2 = System.currentTimeMillis();
        list.forEach(JsonUtil::toJson);
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

    /**
     * jackson处理json字符串转bean日期，默认设置yyyy-MM-dd
     */
    @Test
    public void testJackson(){
        long start = System.currentTimeMillis();
        BeanObj obj = org.cls.javautils.json.jackson.JsonUtil.toBean(jsonStr2, BeanObj.class);
        System.out.println(obj != null ? obj.getDate() : null);
        System.out.println(org.cls.javautils.json.jackson.JsonUtil.toJson(obj));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void testJackson2(){
        long start = System.currentTimeMillis();
        for(int i = 0; i < count; i++){
            BeanObj obj = org.cls.javautils.json.jackson.JsonUtil.toBean(jsonStr, BeanObj.class);
//            System.out.println(obj != null ? obj.getDate() : null);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        long start2 = System.currentTimeMillis();
        list.forEach(org.cls.javautils.json.jackson.JsonUtil::toJson);
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

    @Test
    public void testFastjson(){
        long start = System.currentTimeMillis();
        BeanObj obj = JsonUtil.toBean(jsonStr, BeanObj.class);
        System.out.println(obj.getDate());
        System.out.println(JsonUtil.toJson(obj));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void testFastjson2(){
        long start = System.currentTimeMillis();
        for(int i = 0; i < count; i++){
            BeanObj obj = JsonUtil.toBean(jsonStr, BeanObj.class);
//            System.out.println(obj.getDate());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        long start2 = System.currentTimeMillis();
        list.forEach(obj -> JsonUtil.toJson(obj));
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

    @Test
    public void testGson(){
        long start = System.currentTimeMillis();
        BeanObj obj = GsonUtil.toBean(jsonStr, BeanObj.class);
        System.out.println(obj.getDate());
        System.out.println(GsonUtil.toJson(obj));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(GsonUtil.format(jsonStr));
    }

    @Test
    public void testGson2(){
        long start = System.currentTimeMillis();
        for(int i = 0; i < count; i++){
            BeanObj obj = GsonUtil.toBean(jsonStr, BeanObj.class);
//            System.out.println(obj.getDate());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        long start2 = System.currentTimeMillis();
        list.forEach(GsonUtil::toJson);
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

    @Test
    public void test(){
        AAA aaa = org.cls.javautils.json.jackson.JsonUtil.toBean("{code:200}", AAA.class);
        System.out.println(aaa);
    }
}
