package com.mpango.ws.dao.lms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author HUNGPT
 *
 */
public class DataProvider {

    private static BasicDataSource connectionPool;

    public static Connection getConnection() {

        Connection con = null;
        connectionPool = new BasicDataSource();

        try {

            String host = "localhost";
            String port = "3306";
            String dbName = "mpango";
            String username = "root";
            String password = "r00t";

            String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            connectionPool.setUsername(username);
            connectionPool.setPassword(password);

            //connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
            connectionPool.setDriverClassName("com.mysql.cj.jdbc.Driver");
            
            connectionPool.setUrl(connectionUrl);

            connectionPool.setMinIdle(1);
            connectionPool.setMaxIdle(10);
            connectionPool.setInitialSize(5);
            connectionPool.setMaxOpenPreparedStatements(20);

            con = connectionPool.getConnection();

        } catch (SQLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
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
    public static Connection getConnectionx() {

        /**
         * Declare the JDBC objects. *
         */
        Connection con = null;

        try {

            /**
             * Read all configuration value from configuration file through
             * configuration object which is registered in NameRegistrar.
             */
            /*LMSConfig dbConfig = (LMSConfig) NameRegistrar.get("MyConfigDB");
             String host = dbConfig.getHost();
             String port = dbConfig.getPort();
             String dbName = dbConfig.getDBName();
             String username = dbConfig.getUserName();
             String password = dbConfig.getPassword(); */
            String host = "localhost";
            String port = "3306";
            String dbName = "mpango";
            String username = "root";
            String password = "r00t";
            /**
             * Create a variable for the connection string. *
             */
            String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

            /**
             * Register SQL server driver and establish the connection. *
             */
            Class.forName("com.mysql.jdbc.Driver");

            /**
             * Get connection *
             */
            con = DriverManager.getConnection(connectionUrl, username, password);

            //dbConfig = null; // make a change to GC deallocate dbConfig
            // reference
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;

        }
        return con;
    }
}
