package com.wind.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.io.File;

public class XmlTest {

    @Test
    public void test(){
        String filePath = "src/main/resources/xml/test.xml";
        File file = new File(filePath);
        if(!file.exists()){
            long s = System.currentTimeMillis();
            Document doc = DocumentHelper.createDocument();
            Element root = doc.addElement("root");
            for(int i = 0; i <= 10000000; i++){
                Element testElement = DocumentHelper.createElement("eleTest");
                testElement.addAttribute("name", "testEle");
                testElement.addText("this is another text");
                root.add(testElement);
            }
            XmlUtil.writeXml("src/main/resources/xml/test.xml", doc);
            long e = System.currentTimeMillis();
            System.out.println("共耗时:" + (e - s) + "ms");
        }
//        parseXml(filePath);
        XmlUtil.parseHandler(filePath);
    }
}
