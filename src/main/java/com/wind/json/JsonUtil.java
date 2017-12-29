package com.wind.json;

import com.wind.common.Const;
import com.wind.date.DateUtil;
import com.wind.common.reflect.ReflectUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.lang.reflect.Field;
import java.util.*;

/**
 * json工具
 * @author wind
 *
 */
public class JsonUtil {
	
	/**
	 * 将实体类对象转换成字符串
	 * @param data
	 * @return
	 */
	public static String toJson(Object data){
		JsonConfig jsonConfig = new JsonConfig(); 
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			
			@Override
			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
				return process(arg1);
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return process(arg0);
			}
			
			private Object process(Object value){  
		        if(value instanceof Date){    
		            return DateUtil.getDateStr((Date)value, Const.DATE_TIME);
		        }    
		        return value == null ? "" : value.toString();    
		    }  
		});  
		// 解析内容
		String str = "";
        
        if(data != null){
        	if(data instanceof List){
        		JSONArray jsonArray = JSONArray.fromObject(data, jsonConfig);
        		str = jsonArray.toString();
        	}else{
        		JSONObject obj = JSONObject.fromObject(data, jsonConfig);
        		str = obj != null ? obj.toString() : "";
        	}
        }
        
        return str;
	}

	/**
	 * bean实体类转换成map，字段名为key，值为value
	 * @param obj
	 * @param flag 若为false,则不获取父类成员属性，true则获取
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj, boolean flag){
		Map<String, Object> map = new HashMap<>(Const.MAP_SIZE);
		if(obj != null && !(obj instanceof Class)){
			List<Field> fields = ReflectUtil.getFields(obj.getClass(), flag);
			fields.forEach(field -> {
				field.setAccessible(true);
				try {
					String name = field.getName();
					Object val = field.get(obj);
					map.put(name, val);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		}
		return map;
	}


	
	/**
	 * json字符串转换成实体类
	 * @param jsonStr
	 * @param c
	 * @return
	 */
	public static Object toEntity(String jsonStr, Class<?> c){
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator<?> it = jsonObject.keys();
		Object instance = null; 
		try {
			if(c != null){
				instance = c.newInstance();
			}	
			while(it.hasNext() && instance != null){
				String key = it.next().toString();
				Object value = jsonObject.get(key);
				if(value instanceof JSONObject){
					Field field = c.getDeclaredField(key);
					ReflectUtil.setField(instance, field, toEntity(value.toString(), field.getType()));
				}else if(value instanceof JSONArray){
					JSONArray jsonArray = (JSONArray) value;
					for(int i = 0; i < jsonArray.size(); i++){
						
					}
				}else{
					ReflectUtil.setField(instance, key, value);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return instance;
	}
}
