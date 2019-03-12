package com.fxcrm.db.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class OpSqliteDB {

    // 创建Sqlite数据库连接
    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String property = System.getProperty("user.dir");
        return DriverManager.getConnection("jdbc:sqlite:" + property + "\\crm.db");
    }

}
