package org.cls.javautils.jdbc.callback;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author wind
 */
@FunctionalInterface
public interface DbCallBack {

    /**
     * 数据库元数据操作
     * @param db
     * @throws SQLException
     */
    void call(DatabaseMetaData db) throws SQLException;
}
