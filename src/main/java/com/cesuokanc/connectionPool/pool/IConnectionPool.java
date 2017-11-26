package com.cesuokanc.connectionPool.pool;


import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPool {

    /**
     * 获取连接
     * @return
     */
    Connection getConnection() throws SQLException;

    /**
     * 释放连接
     * @param connection
     */
    void releaseConnection(Connection connection);

    /**
     * 关闭连接
     */
    void close();



}
