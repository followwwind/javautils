package com.wind.reflect;


import com.wind.common.Const;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 类反射工具类
 * @author wind
 */
public class ReflectUtils {

    public void parseClass(Object obj){
        if(obj != null){
            Class c = obj.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = c.getDeclaredFields();
            for(Field field : fields){

            }

            Method[] methods = c.getDeclaredMethods();
            for(Method method : methods){

            }
        }
    }

    /**
     * 获取该类所有父类的成员属性
     * @param c
     * @param fieldList
     */
    public static void getParentField(Class c, List<Field> fieldList, List<Method> methodList){
        if(c != null){
            Class supperClass = c.getSuperclass();
            if(Object.class.equals(supperClass)){
                fieldList.addAll(Arrays.asList(c.getDeclaredFields()));
                methodList.addAll(Arrays.asList(c.getDeclaredMethods()));
            }else{
                getParentField(supperClass, fieldList, methodList);
            }
        }
    }

    /**
     * bean实体类转换成map，字段名为key，值为value
     * @param obj
     * @param flag 若为false,则不获取父类成员属性，true则获取
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj, boolean flag){
        Map<String, Object> map = null;
        if(obj != null){
            map = new HashMap<>(Const.MAP_SIZE);
            Class c = obj.getClass();
            Field[] fields = c.getDeclaredFields();
            List<Method> methodList = null;
            if(flag){
                List<Field> fieldList = new ArrayList<>();
                methodList = new ArrayList<>();
                getParentField(c, fieldList, methodList);
                if(!fieldList.isEmpty()){
                    int srcLength = fields.length;
                    int size = fieldList.size();
                    int length = fields.length + fieldList.size();
                    fields = Arrays.copyOf(fields, length);
                    System.arraycopy(fieldList.toArray(new Field[size]), 0, fields, srcLength, size);
                }
            }
            for(Field field : fields){
                try {
                    String key = field.getName();
                    String methodName = key.substring(0, 1).toUpperCase() + key.substring(1);
                    Method m = c.getMethod("get" + methodName);
                    Object value = m.invoke(obj);
                    map.put(key, value);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(map);
        }
        return map;
    }

    public static void main(String[] args) {

    }
}
