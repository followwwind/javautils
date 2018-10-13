package com.wind.ftl;

import java.util.Map;

/**
 * @Title: FreeMarker
 * @Package com.wind.ftl
 * @Description: freeMarker实体类描述
 * @author wind
 * @date 2018/10/13 11:02
 * @version V1.0
 */
public class FreeMarker {
    /**
     * 模板文件目录
     */
    private String cfgDir;
    /**
     * 模板文件名
     */
    private String cfgName;
    /**
     * 数据
     */
    private Map<String, Object> map;
    /**
     * 生成文件目录
     */
    private String fileDir;
    /**
     * 生成文件名
     */
    private String fileName;

    public String getCfgDir() {
        return cfgDir;
    }

    public void setCfgDir(String cfgDir) {
        this.cfgDir = cfgDir;
    }

    public String getCfgName() {
        return cfgName;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
