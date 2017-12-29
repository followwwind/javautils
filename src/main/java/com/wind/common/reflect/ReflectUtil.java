package com.wind.common.reflect;


import java.lang.reflect.Field;
import java.util.*;

/**
 * 类反射工具类
 * @author wind
 */
public class ReflectUtil {

    /**
     * 获取类成员属性
     * @param c
     * @param flag false表示只获取当前类的成员属性 true表示获取父类的私有成员属性
     * @return
     */
    public static List<Field> getFields(Class c, boolean flag){
        List<Field> fields = new ArrayList<>();
        if(c == null){
            return fields;
        }
        fields.addAll(Arrays.asList(c.getDeclaredFields()));
        if(flag){
            Class supperClass = c.getSuperclass();
            if(!Object.class.equals(supperClass)){
                fields.addAll(getFields(supperClass, flag));
            }
        }
        return fields;
    }

    /**
     * 类成员属性赋值
     * @param instance 实例
     * @param name 属性名称
     * @param value 属性值
     */
    public static void setField(Object instance, String name, Object value){
        if(instance != null && !(instance instanceof Class)){
            try {
                Field field = instance.getClass().getDeclaredField(name);
                setField(instance, field, value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 类成员属性赋值
     * @param instance 实例
     * @param field 属性Field实例
     * @param value 属性值
     */
    public static void setField(Object instance, Field field, Object value){
        if(instance != null && !(instance instanceof Class)){
            if(value == null || field == null){
                return;
            }
            try {
                // 参数值为true，禁止访问控制检查
                Class<?> clazz = field.getType();
                field.setAccessible(true);
                String valStr = value.toString();
                if(clazz.equals(Integer.class) || clazz.equals(int.class)){
                    field.set(instance, Integer.valueOf(valStr));
                }else if(clazz.equals(Double.class) || clazz.equals(double.class)){
                    field.set(instance, Double.valueOf(valStr));
                }else if(clazz.equals(Float.class) || clazz.equals(float.class)){
                    field.set(instance, Float.valueOf(valStr));
                }else if(clazz.equals(Short.class) || clazz.equals(short.class)){
                    field.set(instance, Short.valueOf(valStr));
                }else if(clazz.equals(Boolean.class) || clazz.equals(boolean.class)){
                    field.set(instance, Boolean.valueOf(valStr));
                }else if(clazz.equals(Character.class) || clazz.equals(char.class)){
                    if(valStr.length() == 1){
                        field.set(instance, valStr.charAt(0));
                    }
                }else if(clazz.equals(String.class)){
                    field.set(instance, valStr);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }
}
