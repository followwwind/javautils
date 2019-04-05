package org.cls.javautils.jdbc.entity;

/**
 * 表中主键信息
 *
 * @author wind
 */
public class PrimaryKey {
    /**
     * 列名称
     */
    private String colName;
    /**
     * 主键的名称（可为 null）
     */
    private String pkName;
    /**
     * 主键中的序列号
     */
    private short keySeq;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public short getKeySeq() {
        return keySeq;
    }

    public void setKeySeq(short keySeq) {
        this.keySeq = keySeq;
    }
}
