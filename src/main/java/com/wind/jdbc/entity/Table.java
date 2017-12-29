package com.wind.jdbc.entity;

import java.util.List;

/**
 * 数据库表详情类
 * @author wind
 */
public class Table {
    /**
     * 数据库实例名
     */
    private String tableCat;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表对应驼峰类名
     */
    private String property;

    /**
     * 主键
     */
    private List<PrimaryKey> primaryKeys;
    /**
     * 所有列的信息
     */
    private List<Column> columns;

    /**
     * 表注释
     */
    private String remarks;

    public Table() {
    }

    public Table(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<PrimaryKey> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
