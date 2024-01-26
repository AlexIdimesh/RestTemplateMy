package org.example.db;

import com.zaxxer.hikari.HikariDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
public class DBConnection implements ConnectionManager {
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "driver.class.name";
    private static final String DB_URL = "db.url";
    private static HikariDataSource dataSource;

    static {
        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream("scr/main/java/resources/db.properties"));
            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(properties.getProperty(DB_DRIVER));
            dataSource.setJdbcUrl(properties.getProperty(DB_URL));
            dataSource.setPassword(properties.getProperty(DB_PASSWORD));
            dataSource.setUsername(properties.getProperty(DB_USERNAME));
            dataSource.setMinimumIdle(100);
            dataSource.setMaximumPoolSize(1000000);
            dataSource.setAutoCommit(true);
            dataSource.setLoginTimeout(3);
        } catch (IOException | SQLException exception) {
            exception.printStackTrace();
        }
    }
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        Connection connection = dataSource.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return connection;
    }
}
