package com.wind.compress;

import com.wind.common.Const;
import com.wind.common.IOUtils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP压缩工具类
 * @author  wind
 */
public class GZipUtils {

    /**
     * 文件压缩
     * @param source 待压缩文件路径
     * @param target
     */
    public static void compress(String source, String target){
        compress(new File(source), target);
    }

    /**
     * 文件压缩
     * @param source File 待压缩
     * @param target
     */
    public static void compress(File source, String target){
        try {
            FileInputStream fis = new FileInputStream(source);
            File targetFile = new File(target);
            if(targetFile.isFile()){
                System.err.println("target不能是文件，否则压缩失败");
                return;
            }
            target += (source.getName() + Const.FILE_GZ);
            FileOutputStream fos = new FileOutputStream(target);
            compress(fis, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件解压缩
     * @param source 待压缩文件路径
     * @param target
     */
    public static void uncompress(String source, String target){
        uncompress(new File(source), target);
    }

    /**
     * 文件解压缩
     * @param source File 待解压缩
     * @param target
     */
    public static void uncompress(File source, String target){
        try {
            FileInputStream fis = new FileInputStream(source);
            target += source.getName().replace(Const.FILE_GZ, "");
            FileOutputStream fos = new FileOutputStream(target);
            uncompress(fis, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件流压缩
     * @param in
     * @param out
     */
    private static void compress(InputStream in, OutputStream out){
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(out);
            int count;
            byte[] data = new byte[Const.BUFFER_1024];
            while ((count = in.read(data, 0, data.length)) != -1) {
                gos.write(data, 0, count);
            }
            gos.finish();
            gos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(in, gos);
        }
    }


    /**
     * 文件流解压缩
     * @param in
     * @param out
     */
    private static void uncompress(InputStream in, OutputStream out){
        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(in);
            int count;
            byte[] data = new byte[Const.BUFFER_1024];
            while ((count = gis.read(data, 0, Const.BUFFER_1024)) != -1) {
                out.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(gis, out);
        }
    }




    public static void main(String[] args) {
//        String source = "src/main/resources/image/head.jpg";
//        String target = "src/main/resources/";
//        compress(source, target);
//        uncompress(target + "head.jpg.gz", target);
    }




}
