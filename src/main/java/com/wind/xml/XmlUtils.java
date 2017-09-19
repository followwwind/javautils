package com.wind.xml;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * xml解析工具类
 */
public class XmlUtils {

    public static void parse(String filePath){
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        try {
            //读取文件 转换成Document
            Document document = reader.read(new File(filePath));
            //获取根节点元素对象
            Element root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void parse2(String filePath){
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        reader.addHandler(filePath, new ElementHandler() {
            @Override
            public void onStart(ElementPath elementPath) {

            }

            @Override
            public void onEnd(ElementPath elementPath) {

            }
        });
    }


}
