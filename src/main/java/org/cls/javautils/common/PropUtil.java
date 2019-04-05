package org.cls.javautils.common;


import java.io.*;
import java.util.*;

/**
 * @Title: PropUtil
 * @Package com.wind.common
 * @Description: 解析properties文件工具类
 * @author wind
 * @date 2018/10/11 9:30
 * @version V1.0
 */
public class PropUtil {

    /**
     * 解析properties文件
     * @param filePath
     * @return
     */
    public static Properties getProp(String filePath){
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            return getProp(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Properties();
    }

    /**
     * 解析properties文件
     * @param in
     * @return
     */
    public static Properties getProp(InputStream in){
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return props;
    }


    /**
     * 写入properties信息
     * @param filepath
     * @param map
     */
    public static void setProp(String filepath, Map<String, String> map){
        Properties props = getProp(filepath);
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            map.forEach(props::setProperty);
            props.store(fos, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        // 获取系统信息
        Properties prop = System.getProperties();
        prop.list(System.out);
    }
}
