package com.cesuokanc.connectionPool.pool;

import com.cesuokanc.connectionPool.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/*
 * @desc 提供简单的连接池实现
 * 1. 获取连接：从连接池中获取连接，如果连接池不为空，获取一个连接，若没有空余连接，则新建一个连接
 * 2. 释放连接：如果连接池未满，则释放连接到池中，否则的话直接关闭该连接
 * 3. 关闭连接池：关闭所有的连接
 * @author lirb
 * @datetime 2017/11/26,16:59
 */
public class SimpleConnectionPool implements IConnectionPool {

    private ConnectionProvider connectionProvider = new ConnectionProvider();

    private List<Connection> connections = new LinkedList<Connection>(); //连接缓存

    private int poolSize = 10; //池大小


    public SimpleConnectionPool(int poolSize) {
        this.poolSize = poolSize;
    }

    public SimpleConnectionPool(){super();};


    public Connection getConnection() throws SQLException {

        synchronized (connections) {
            if (!connections.isEmpty()) {
                return connections.remove(connections.size() - 1);
            }
        }

        return connectionProvider.getConnection();
    }


    public void releaseConnection(Connection connection) {

        synchronized (connections) {
            int currentSize = connections.size();
            if (currentSize < this.poolSize) {
                connections.add(connection);
                return;
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void close() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
