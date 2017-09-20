package com.wind.common;

import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

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
     * 关闭输入输出流
     * @param in
     * @param out
     */
    public static void close(InputStream in, OutputStream out){
        close(in);
        close(out);
    }

    /**
     * 批量关闭输入流
     * @param ins
     */
    public static void close(InputStream... ins){
        batchClose(ins);
    }

    /**
     * 批量关闭输出流
     * @param outs
     */
    public static void close(OutputStream... outs){
        batchClose(outs);
    }


    private static void batchClose(Object ... objs){
        Arrays.asList(objs).parallelStream().forEach(obj -> {
            if(obj instanceof InputStream){
                InputStream in = (InputStream) obj;
                close(in);
            }else if(obj instanceof OutputStream){
                OutputStream out = (OutputStream) obj;
                close(out);
            }

        });
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
