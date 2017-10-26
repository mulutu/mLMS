package com.mpango.dao.lms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mpango.Util.db.LMSConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.util.NameRegistrar;
import org.jpos.util.NameRegistrar.NotFoundException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author HUNGPT
 *
 */
public class DataProvider {

    private static BasicDataSource connectionPool;

    public static Connection getConnectionxxx() {
        Connection con = null;
        connectionPool = new BasicDataSource();

        try {

            /**
             * Read all configuration value from configuration file through
             * configuration object which is registered in NameRegistrar.
             */
            LMSConfig dbConfig = (LMSConfig) NameRegistrar.get("MyConfigDB");
            String host = dbConfig.getHost();
            String port = dbConfig.getPort();
            String dbName = dbConfig.getDBName();
            String username = dbConfig.getUserName();
            String password = dbConfig.getPassword();

            String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=2";

            connectionPool.setUsername(username);
            connectionPool.setPassword(password);

            //connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
            connectionPool.setDriverClassName("com.mysql.cj.jdbc.Driver");

            connectionPool.setUrl(connectionUrl);

            connectionPool.setMinIdle(1);
            connectionPool.setMaxIdle(10);
            connectionPool.setInitialSize(30);
            connectionPool.setMaxOpenPreparedStatements(50);

            con = connectionPool.getConnection();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;

        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return con;
    }

    /**
     * Read configuration parameters from configuration XML file through
     * LMSConfig Object. After a while, Get connection with parameters which it
     * got.
     *
     * @return Connection to specify database.
     * @see LMSConfig
     */
    public static Connection getConnection() {

        /**
         * Declare the JDBC objects. *
         */
        Connection con = null;

        try {

            /**
             * Read all configuration value from configuration file through
             * configuration object which is registered in NameRegistrar.
             */
            LMSConfig dbConfig = (LMSConfig) NameRegistrar.get("MyConfigDB");
            String host = dbConfig.getHost();
            String port = dbConfig.getPort();
            String dbName = dbConfig.getDBName();
            String username = dbConfig.getUserName();
            String password = dbConfig.getPassword();

            /**
             * Create a variable for the connection string. *
             */
            String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?autoReconnect=true&useSSL=false";

            /**
             * Register SQL server driver and establish the connection. *
             */
            Class.forName("com.mysql.cj.jdbc.Driver");

            /**
             * Get connection *
             */
            con = DriverManager.getConnection(connectionUrl, username, password);

            dbConfig = null; // make a change to GC deallocate dbConfig
            // reference

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;

        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return con;
    }
}
