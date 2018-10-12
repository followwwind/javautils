package com.wind.common;

import org.dom4j.io.XMLWriter;

import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.Arrays;

/**
 * @Title: IoUtil
 * @Package com.wind.common
 * @Description: io工具类
 * @author wind
 * @date 2018/10/11 9:28
 * @version V1.0
 */
public class IoUtil {

    /**
     * 关闭字节输入流
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
     * 关闭字节输入输出流
     * @param in
     * @param out
     */
    public static void close(InputStream in, OutputStream out){
        close(in);
        close(out);
    }

    /**
     * 关闭字符输入流
     * @param reader
     */
    public static void close(Reader reader){
        if(reader != null){
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭字符输出流
     * @param writer
     */
    public static void close(Writer writer){
        if(writer != null){
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭image输入流
     * @param iis
     */
    public static void close(ImageInputStream iis){
        if(iis != null){
            try {
                iis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量关闭字节输入流
     * @param ins
     */
    public static void close(InputStream... ins){
        Arrays.asList(ins).parallelStream().forEach(IoUtil::close);
    }

    /**
     * 批量关闭字节输出流
     * @param outs
     */
    public static void close(OutputStream... outs){
        Arrays.asList(outs).parallelStream().forEach(IoUtil::close);
    }

    /**
     * 关闭字节输出流
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

    /**
     * 关闭XMLWriter输出流
     * @param out
     */
    public static void close(XMLWriter out){
        if(out != null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
