package com.cesuokanc.connectionPool.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * @desc 提供数据库连接
 * @author lirb
 * @datetime 2017/11/26,17:04
 */
public class ConnectionProvider {

    private static String url = "jdbc:mysql://192.168.48.1:3307/hibernate";
    private static String user = "lrb";
    private static String password = "1234";

    static {
         try{
             Class.forName(com.mysql.jdbc.Driver.class.getName());
         }catch (ClassNotFoundException e){
             throw new RuntimeException(e);
         }
    }

    private Connection connection;

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }



}
