package org.cls.javautils.network.http;

import org.cls.javautils.common.Constants;

import java.io.InputStream;
import java.net.URL;


/**
 * @Title: ResourceUtil
 * @Package com.wind.resource
 * @Description: 资源文件加载工具类
 * @author wind
 * @date 2018/10/11 10:46
 * @version V1.0
 */
public class ResourceUtil {

    /**
     * 获取资源文件的输入流
     * @param fileName
     * @return
     */
    public static InputStream getResFile(String fileName){
        InputStream in = null;
        if(fileName != null){
            if(fileName.startsWith(Constants.FILE_SEPARATOR)){
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
     * @return
     */
    public static URL getUrlFile(String fileName){
        URL url = null;
        if(fileName != null){
            if(fileName.startsWith(Constants.FILE_SEPARATOR)){
                url = ResourceUtil.class.getResource(fileName);
            }else{
                url = ClassLoader.getSystemClassLoader().getResource(fileName);
            }
        }
        return url;
    }


    /**
     * 获取class类的编译路径
     * @return
     */
    public static String getClassPath(){
        URL url = Thread.currentThread().getContextClassLoader().getResource(Constants.BLANK_STR);
        return url != null ? url.getPath() : "";
    }

    /**
     * 获取项目的路径
     * @return
     */
    public static String getProjectPath(){
        return System.getProperty(Constants.USER_DIR);
    }
}
