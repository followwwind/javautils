package com.wind.jdbc;


import com.wind.common.PropUtil;
import com.wind.common.StringUtil;
import com.wind.jdbc.callback.DbCallBack;
import com.wind.jdbc.entity.Column;
import com.wind.jdbc.entity.PrimaryKey;
import com.wind.jdbc.entity.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Db工具类
 * @author wind
 */
public class DbUtil {

    private static Properties props = PropUtil.readProp("src/main/resources/jdbc.properties");
    static{
        try {
            Class.forName(props.getProperty("driverClass"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    private static Connection getConn(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(props.getProperty("jdbcUrl"), props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }



    /**
     * 获取元数据信息
     * @param callBack
     */
    private static void exec(DbCallBack callBack){
        Connection con = getConn();
        try {
            DatabaseMetaData db = con.getMetaData();
            callBack.call(db);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
    }

    /**
     * 获取所有数据库实例
     * @return
     */
    public static List<String> getAllDb(){
        List<String> dbs = new ArrayList<>();
        exec(db -> {
            ResultSet rs = db.getCatalogs();
            while(rs.next()){
                String dbName = rs.getString("TABLE_CAT");
                dbs.add(dbName);
            }
        });
        return dbs;
    }

    /**
     * 获取数据库所有表
     * @param catalog
     * @return
     */
    public static List<Table> getTables(String catalog){
        List<Table> tables = new ArrayList<>();
        exec(db -> {
            ResultSet rs = db.getTables(catalog,null,null, new String[]{"TABLE"});
            while(rs.next()){
                tables.add(getTable(rs));
            }
        });
        return tables;
    }

    /**
     * 组装table数据
     * @param rs
     * @return
     * @throws SQLException
     */
    private static Table getTable(ResultSet rs){
        Table table = null;
        try {
            if(rs != null){
                table = new Table();
                String tableName = rs.getString("TABLE_NAME");
                table.setTableName(tableName);
                table.setProperty(StringUtil.getCamelCase(tableName, true));
                String catalog = rs.getString("TABLE_CAT");
                table.setTableCat(catalog);
                table.setRemarks(rs.getString("REMARKS"));
                table.setPrimaryKeys(getPrimaryKeys(catalog, tableName));
                table.setColumns(getColumns(catalog, tableName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }



    /**
     * 获取数据库列信息
     * @param catalog
     * @param tableName
     * @return
     */
    public static List<Column> getColumns(String catalog, String tableName){
        List<Column> columns = new ArrayList<>();
        exec(db -> {
            ResultSet rs = db.getColumns(catalog,"%", tableName,"%");
            while (rs.next()){
                Column column = new Column();
                String colName = rs.getString("COLUMN_NAME");
                String typeName = rs.getString("TYPE_NAME");
                column.setColumnName(colName);
                column.setColumnType(typeName);
                column.setProperty(StringUtil.getCamelCase(colName, false));
                column.setType(getFieldType(typeName));
                column.setColumnSize(rs.getInt("COLUMN_SIZE"));
                column.setNullable(rs.getInt("NULLABLE"));
                column.setRemarks(rs.getString("REMARKS"));
                column.setDigits(rs.getInt("DECIMAL_DIGITS"));

                columns.add(column);
            }
        });
        return columns;
    }

    /**
     * TABLE_CAT String => 表类别（可为 null）
     * TABLE_SCHEM String => 表模式（可为 null）
     * TABLE_NAME String => 表名称
     * COLUMN_NAME String => 列名称
     * KEY_SEQ short => 主键中的序列号
     * PK_NAME String => 主键的名称（可为 null）
     * @param catalog
     * @param tableName
     * @return
     */
    public static List<PrimaryKey> getPrimaryKeys(String catalog, String tableName){
       List<PrimaryKey> keys = new ArrayList<>();
       exec(db -> {
           ResultSet rs = db.getPrimaryKeys(catalog, null, tableName);
           while(rs.next()) {
               PrimaryKey primaryKey = new PrimaryKey();
               primaryKey.setColName(rs.getString("COLUMN_NAME"));
               primaryKey.setPkName(rs.getString("PK_NAME"));
               primaryKey.setKeySeq(rs.getShort("KEY_SEQ"));
               keys.add(primaryKey);
           }
       });
       return keys;
    }


    /**
     * 获取数据库表的描述信息
     * @param catalog
     * @param tableName
     * @return
     */
    public static Table getTable(String catalog, String tableName){
        Connection con = getConn();
        Table table = null;
        try {
            DatabaseMetaData db = con.getMetaData();
            ResultSet rs = db.getTables(catalog,null,tableName, new String[]{"TABLE"});
            if(rs.first()) {
                table = getTable(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return table;
    }


    /**
     * 表字段类型转换成java类型
     * @param columnType
     * @return
     */
    private static String getFieldType(String columnType){
        String result;
        columnType = columnType != null ? columnType : "";
        switch (columnType){
            case "CHAR" : result = "String";break;
            case "VARCHAR" : result = "String";break;
            case "INT" : result = "Integer";break;
            case "DOUBLE" : result = "Double";break;
            case "TIMESTAMP" : result = "Date";break;
            default: result = "String"; break;
        }
        return result;
    }

    /**
     *
     * @param con
     */
    private static void close(Connection con){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
