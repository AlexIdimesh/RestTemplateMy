package org.example.db;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionManager {
    Connection getConnection() throws SQLException, ClassNotFoundException;
}
