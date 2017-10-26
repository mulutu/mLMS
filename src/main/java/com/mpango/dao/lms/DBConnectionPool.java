/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.dao.lms;

import com.mpango.Util.db.LMSConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.jpos.util.NameRegistrar;

/**
 *
 * @author jmulutu
 */
public class DBConnectionPool {

    private static Logger logger = Logger.getLogger(DBConnectionPool.class.getName());
    private GenericObjectPool connectionPool = null;
    LMSConfig dbConfig;

    public PoolingDataSource setUp() {

        try {
            dbConfig = (LMSConfig) NameRegistrar.get("MyConfigDB");

            String host = dbConfig.getHost();
            String port = dbConfig.getPort();
            String dbName = dbConfig.getDBName();
            String username = dbConfig.getUserName();
            String password = dbConfig.getPassword();
            int ConnectionPool = dbConfig.getConnetionPool();

            // Load JDBC Driver class.
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";

            // Creates an instance of GenericObjectPool that holds our
            // pool of connections object.
            connectionPool = new GenericObjectPool();
            connectionPool.setMaxActive(ConnectionPool);
            connectionPool.setMaxIdle(10);
            connectionPool.setMinIdle(2);

            // Creates a connection factory object which will be use by
            // the pool to create the connection object. We passes the
            // JDBC url info, username and password.
            ConnectionFactory cf = new DriverManagerConnectionFactory(connectionUrl, username, password);
            // Creates a PoolableConnectionFactory that will wraps the
            // connection object created by the ConnectionFactory to add
            // object pooling functionality.
            PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, connectionPool, null, null, false, true);
        } catch (NameRegistrar.NotFoundException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.info("processApplications no CONN: " + ex);
        }

        return new PoolingDataSource(connectionPool);
    }

    public GenericObjectPool getConnectionPool() {
        return connectionPool;
    }

}
