package com.wind.image.ocr.tesseract;


import com.wind.common.Const;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;

/**
 * tesseract for java， ocr（Optical Character Recognition，光学字符识别）
 * 工具类
 * @author wind
 */
public class Tess4JUtils {
    /**
     * 从图片中提取文字,默认设置英文字库,使用classpath目录下的训练库
     * @param path
     * @return
     */
    public static String readChar(String path){
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        File imageFile = new File(path);
        //In case you don't have your own tessdata, let it also be extracted for you
        //这样就能使用classpath目录下的训练库了
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        //Set the tessdata path
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        instance.setLanguage(Const.ENG);//英文库识别数字比较准确
        return getOCRText(instance, imageFile);
    }

    /**
     * 从图片中提取文字
     * @param path 图片路径
     * @param dataPath 训练库路径
     * @param language 语言字库
     * @return
     */
    public static String readChar(String path, String dataPath, String language){
        File imageFile = new File(path);
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(dataPath);
        instance.setLanguage(language);//英文库识别数字比较准确
        return getOCRText(instance, imageFile);
    }

    /**
     * 识别图片文件中的文字
     * @param instance
     * @param imageFile
     * @return
     */
    private static String getOCRText(ITesseract instance, File imageFile){
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        /*String path = "src/main/resources/image/text.png";
        System.out.println(readChar(path));*/

        String ch = "src/main/resources/image/ch.png";
        System.out.println(readChar(ch, "src/main/resources", Const.CHI_SIM));
    }

}
