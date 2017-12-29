package com.wind.resource;

import com.wind.common.Const;

import java.io.InputStream;
import java.net.URL;


/**
 * 资源文件加载工具类
 */
public class ResourceUtil {

    /**
     * 获取资源文件的输入流
     * @param fileName
     * @return file:/E:/code/javacode/javautils/target/classes/image/code.png
     */
    public static InputStream getResFile(String fileName){
        InputStream in = null;
        if(fileName != null){
            if(fileName.startsWith(Const.FILE_SEPARATOR)){
                in = ResourceUtil.class.getResourceAsStream(fileName);
            }else{
                in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            }
        }
        return in;
    }

    /**
     * 获取资源文件的url
     * @param fileName
     * @return  file:/E:/code/javacode/javautils/target/classes/image/code.png
     */
    public static URL getUrlFile(String fileName){
        URL url = null;
        if(fileName != null){
            if(fileName.startsWith(Const.FILE_SEPARATOR)){
                url = ResourceUtil.class.getResource(fileName);
            }else{
                url = ClassLoader.getSystemClassLoader().getResource(fileName);
            }
        }
        return url;
    }


    /**
     * 获取class类的编译路径
     * @return  /E:/code/javacode/javautils/target/classes/
     */
    public static String getClassPath(){
        return Thread.currentThread().getContextClassLoader().getResource(Const.BLANK_STR).getPath();
    }

    /**
     * 获取项目的路径
     * @return E:\code\javacode\javautils
     */
    public static String getRelPath(){
        return System.getProperty(Const.USER_DIR);
    }


    public static void main(String[] args) {
        URL url = getUrlFile("image/code.png");
        URL url2 = getUrlFile("/image/code.png");
        System.out.println(url);
        System.out.println(url2);

        String relativelyPath = getRelPath();
        System.out.println(relativelyPath);

        String path = getClassPath();
        System.out.println(path);
    }

}
