package org.example.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        DBConnection connection1 = new DBConnection();
        try (Connection connection = connection1.getConnection()) {
            if (connection != null) {
                System.out.println("Успешно установлено соединение с базой данных!");
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT * from events")) {
                    while (resultSet.next()) {
                        int result = resultSet.getInt(1);
                        System.out.println("Результат запроса: " + result);
                    }
                }
            } else {
                System.out.println("Не удалось установить соединение с базой данных.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
