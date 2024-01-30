package org.example.db;

import com.zaxxer.hikari.HikariDataSource;
import org.example.util.PropertiesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBConnection implements ConnectionManager {
    public DBConnection() {
    }

    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            return DriverManager.getConnection (
                    PropertiesUtil.get(DB_URL),
                    PropertiesUtil.get(DB_USERNAME),
                    PropertiesUtil.get(DB_PASSWORD)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
