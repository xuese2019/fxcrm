package com.fxcrm.db.dao;

import com.fxcrm.db.utils.OpSqliteDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class AccountDao {

    /**
     * 根据sql 查询
     *
     * @param sql
     * @return
     */
    public boolean login(String sql) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        boolean b = false;
        try {
            connection = OpSqliteDB.createConnection();
            statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
            // 执行查询语句
            resultSet = statement.executeQuery(sql);
            if (resultSet != null && resultSet.next()) {
                b = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public boolean update(String sql) {
        Connection connection = null;
        Statement statement = null;
        boolean b = false;
        try {
            connection = OpSqliteDB.createConnection();
            statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
            // 执行查询语句
            statement.executeUpdate(sql);
            b = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }
}
