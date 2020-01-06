package com.wind.ftl;


import com.wind.common.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * freeMarker工具生成类
 * @author wind
 */
public class FtlUtil {

    /**
     * 生成文件
     * @param freeMarker
     */
    public static void genCode(FreeMarker freeMarker){
        String fileDir = freeMarker.getFileDir();
        String fileName = freeMarker.getFileName();
        try(
            OutputStream fos = new FileOutputStream( new File(fileDir, fileName));
            Writer out = new OutputStreamWriter(fos)
        ) {
            File dir = new File(fileDir);
            boolean sign = true;
            if(!dir.exists()){
                sign = dir.mkdirs();
            }

            if(sign){
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
                cfg.setDirectoryForTemplateLoading(new File(freeMarker.getCfgDir()));
                cfg.setDefaultEncoding(Constants.UTF8);
                cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                Template temp = cfg.getTemplate(freeMarker.getCfgName());
                Map<String, Object> map = freeMarker.getMap();
                if(map == null){
                    map = new HashMap<>(16);
                }
                temp.process(map, out);
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
