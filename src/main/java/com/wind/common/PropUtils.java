package com.wind.common;


import java.io.*;
import java.util.*;

/**
 * 解析properties文件工具类
 * @author wind
 */
public class PropUtils {
    /**
     * 解析properties文件，用map存储
     * @param filePath
     * @return
     */
    public static Properties readProp(String filePath){
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * 写入properties信息
     * @param filepath
     * @param map
     */
    public static void writeProp(String filepath, Map<String,String>map){
        Properties props = new Properties();
        try {
            OutputStream fos = new FileOutputStream(filepath);
            Set<Map.Entry<String, String>> entrys = map.entrySet();
            for(Map.Entry<String, String> entry : entrys){
                String key = entry.getKey();
                String value = entry.getValue();
                props.setProperty(key, value);
            }
            props.store(fos, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // 获取系统信息
        Properties prop = System.getProperties();
        prop.list(System.out);
    }
}
