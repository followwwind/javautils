package com.wind.xml;

import com.wind.common.IoUtil;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * xml解析工具类
 */
public class XmlUtil {

    /**
     * dom解析xml文件
     * @param filePath
     */
    public static void parseXml(String filePath){
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        try {
            //读取文件 转换成Document
            Document document = reader.read(new File(filePath));
            //获取根节点元素对象
            Element root = document.getRootElement();
            long s = System.currentTimeMillis();
            getChildNodes(root);
            long e = System.currentTimeMillis();
            System.out.println(root.getName() + "节点元素遍历结束,耗时共:" + (e - s) + " ms");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历节点下的所有子节点和属性
     * @param ele 元素节点
     */
    private static void getChildNodes(Element ele){

        String eleName = ele.getName();
        System.out.println("开始遍历当前：" + eleName + "节点元素");
        List<Attribute> attributes = ele.attributes();
        for(Attribute attr : attributes){
            System.out.println("name=" + attr.getName() + ",value=" + attr.getValue());
        }

        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<Element> iterator = ele.elementIterator();

        if(!iterator.hasNext()){
            String text = ele.getTextTrim();
            System.out.println("text=" + text);
        }

        while(iterator.hasNext()){
            Element e = iterator.next();
            getChildNodes(e);
        }
    }

    /**
     * sax解析xml配置文件
     * @param filePath
     */
    public static void parseHandler(String filePath){
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        reader.setDefaultHandler(new ElementHandler(){

            @Override
            public void onStart(ElementPath elementPath) {

            }

            @Override
            public void onEnd(ElementPath elementPath) {
                //获得当前节点
                Element e = elementPath.getCurrent();
                System.out.println(e.getName());
                //记得从内存中移去
                e.detach();
            }
        });
        try {
            reader.read(new BufferedInputStream(new FileInputStream(new File(filePath))));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void writeXml(String filePath, Document doc){
        // 设置XML文档格式
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        // 设置XML编码方式,即是用指定的编码方式保存XML文档到字符串(String),这里也可以指定为GBK或是ISO8859-1
        outputFormat.setEncoding("UTF-8");
        //是否生产xml头
        outputFormat.setSuppressDeclaration(true);
        //设置是否缩进
        outputFormat.setIndent(true);
        //以四个空格方式实现缩进
        outputFormat.setIndent("    ");
        //设置是否换行
        outputFormat.setNewlines(true);
        XMLWriter out = null;
        try{
            out = new XMLWriter(new FileWriter(new File(filePath)), outputFormat);
            out.write(doc);
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            IoUtil.close(out);
        }
    }
}
