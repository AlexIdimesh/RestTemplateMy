package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.DBConnection;
import org.example.model.EventsEntity;
import org.example.repository.EventsEntityRepository;
import org.example.repository.mapper.EventsResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EventsEntityRepositoryImpl implements EventsEntityRepository {
    private EventsResultSetMapper resultSetMapper;
    private DBConnection dbConnection;

    @Override
    public EventsEntity findById(UUID id) {
        try(Connection connection = dbConnection.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT*FROM events WHERE event_id = ?");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
        // НАДО ИСПРАВИТЬ МЕТОД delete
    @Override
    public boolean deleteById(UUID id) {
        try(Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM events WHERE event_id = ?");
            ResultSet resultSet = preparedStatement.executeQuery();
            // хз что написать
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public EventsEntity findAll() {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM events");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EventsEntity save(EventsEntity eventsEntity) {
        try(Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO events (name, city) VALUES(?,?)");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
