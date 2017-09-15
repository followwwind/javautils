package com.wind.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO流工具类
 */
public class IOUtils {
    /**
     * 关闭输入流
     * @param in
     */
    public static void close(InputStream in){
        if(in != null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * @param out
     */
    public static void close(OutputStream out){
        if(out != null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
