package org.cls.javautils.jdbc.entity;

/**
 * 数据库列详情类
 * @author wind
 */
public class Column {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 类型
     */
    private String columnType;
    /**
     * 数据库列名对应的java类字段名
     */
    private String property;

    /**
     * 数据库表对应java类型
     */
    private String type;

    /**
     * 占用字节
     */
    private int columnSize;
    /**
     * 是否为空
     * 1.columnNoNulls - 可能不允许使用 NULL 值
     * 2.columnNullable - 明确允许使用 NULL 值
     * 3.columnNullableUnknown - 不知道是否可使用 null
     */
    private int nullable;
    /**
     * 小数部分的位数
     */
    private int digits;
    /**
     * 描述
     */
    private String remarks;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
