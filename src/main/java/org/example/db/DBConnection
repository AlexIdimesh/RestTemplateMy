package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.example.util.PropertiesUtil;

public final class DBConnection implements ConnectionManager {
    private String DB_USERNAME = "db.username";
    private String DB_PASSWORD = "db.password";
    private String DB_URL = "db.url";
    private Properties properties;
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public DBConnection() {
        this.properties = PropertiesUtil.get();
    }

    public DBConnection(Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            return DriverManager.getConnection(this.properties.getProperty(this.DB_URL), this.properties.getProperty(this.DB_USERNAME), this.properties.getProperty(this.DB_PASSWORD));
        } catch (SQLException var2) {
            throw new RuntimeException(var2);
        }
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var1) {
            throw new RuntimeException(var1);
        }
    }
}
