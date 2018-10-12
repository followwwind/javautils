package com.wind.compress;

import com.wind.common.Constants;
import com.wind.common.IoUtil;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 通过Java的Zip输入输出流实现压缩和解压文件
 * @author wind
 */
public class ZipUtil {

    /**
     * 压缩文件
     * 若压缩文件，请指定目标文件目录和目标文件名
     * @param source 源文件路径
     * @param target 目标文件路径
     */
    public static void zip(String source, String target) {
        File sourceFile = new File(source);
        if (sourceFile.exists()) {
            File targetFile = new File(target);
            if(targetFile.isDirectory()){
                String zipName = sourceFile.getName() + Constants.FILE_ZIP;
                targetFile = new File(target + zipName);
            }

            if (targetFile.exists()) {
                targetFile.delete(); // 删除旧的文件
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(targetFile);
                ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
                // 添加对应的文件Entry
                addEntry("", sourceFile, zos);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IoUtil.close(fos);
            }
        }
    }

    /**
     * 扫描添加文件Entry
     *
     * @param base 基路径
     * @param source 源文件
     * @param zos Zip文件输出流
     * @throws IOException
     */
    private static void addEntry(String base, File source, ZipOutputStream zos){
        // 按目录分级，形如：aaa/bbb.txt
        String entry = base + source.getName();
        if (source.isDirectory()) {
            File[] files = source.listFiles();
            if(files != null){
                for (File file : files) {
                    // 递归列出目录下的所有文件，添加文件Entry
                    addEntry(entry + "/", file, zos);
                }
            }
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[1024 * 10];
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IoUtil.close(fis, bis);
            }
        }
    }


    /**
     * 解压文件
     * @param filePath 压缩文件路径
     * @param targetPath 解压缩路径
     */
    public static void unzip(String filePath, String targetPath) {
        // TODO java.io.EOFException: Unexpected end of ZLIB input stream该异常目前未解决，不过不影响文件解压
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                    File target = new File(targetPath, entry.getName());
                    if (!target.getParentFile().exists()) {
                        // 创建文件父目录
                        target.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    byte[] buffer = new byte[Constants.BUFFER_512];
                    while (true) {
                        int len = zis.read(buffer);
                        if(len == -1){
                            break;
                        }
                        bos.write(buffer, 0, len);
                    }
                    bos.flush();
                }
                zis.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IoUtil.close(zis, bos);
            }
        }
    }

    public static void main(String[] args) {
//        String source = "src/main/resources/image/head.jpg";
//        String target = "src/main/resources/123.zip";
//        zip(source, target);

        String filePath = "src/main/resources/123.zip";
        String targetPath = "E:/";

        unzip(filePath, targetPath);

    }

}
