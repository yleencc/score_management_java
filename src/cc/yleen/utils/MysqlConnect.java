package cc.yleen.utils;

import cc.yleen.config.SQLConfig;

import java.sql.*;

public class MysqlConnect {
    static {
        // 注册 JDBC
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到MySQL JDBC驱动包。" + e.getMessage());
        }
    }

    private static volatile Connection connection;

    private MysqlConnect() {

    }

    public static Connection getInstance() {
        try {
            if (null == connection) {
                // 创建对象前做一些准备工作
                // ...
                synchronized (Connection.class) {
                    if (null == connection) {
                        connection = DriverManager.getConnection(SQLConfig.URL, SQLConfig.USER, SQLConfig.PASSWORD);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("连接数据库失败。" + e.getMessage());
        }
        return connection;
    }

    // 重新连接数据库
    public static boolean reconnectSQL() {
        System.out.println("连接数据库...");
        try {
            connection = DriverManager.getConnection(SQLConfig.URL, SQLConfig.USER, SQLConfig.PASSWORD);
            System.out.println("连接数据库成功...");
            return true;
        } catch (SQLException e) {
            System.out.println("连接数据库失败..." + e.getMessage());
            return false;
        }
    }

    // 执行查询
    public static ResultSet executionStatement(Connection connection, String statements) throws SQLException {
        System.out.println("实例化Statement对象...");
        Statement statement = connection.createStatement();
        return statement.executeQuery(statements);
    }
}
