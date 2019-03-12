package com.fxcrm.db.dao;

import com.fxcrm.db.utils.OpSqliteDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class Daos {
    public List<Map<Integer, String>> query(String sql, int x) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        List<Map<Integer, String>> list = new ArrayList<>();
        try {
            connection = OpSqliteDB.createConnection();
            statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
            // 执行查询语句
            resultSet = statement.executeQuery(sql);
            if (resultSet != null && resultSet.next()) {
                do {
                    Map<Integer, String> m = new HashMap<>();
                    for (int i = 1; i <= x; i++) {
                        m.put(i, resultSet.getString(i));
                    }
                    list.add(m);
                } while (resultSet.next());
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
        return list;
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
