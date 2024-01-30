package org.example.repository.impl;

import org.example.db.DBConnection;
import org.example.model.EventsEntity;
import org.example.repository.EventsEntityRepository;
import org.example.repository.mapper.EventsResultSetMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventsEntityRepositoryImpl implements EventsEntityRepository {
    private EventsResultSetMapper resultSetMapper;
    private DBConnection dbConnection;

    @Override
    public EventsEntity findById(Long id) {
        try(Connection connection = dbConnection.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT*FROM events WHERE event_id = ?");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean deleteById(Long id) {
        try(Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM events WHERE event_id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<EventsEntity> findAll() throws SQLException, ClassNotFoundException {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<EventsEntity> events = new ArrayList<>();
            while (resultSet.next()) {
                events.add(resultSetMapper.map(resultSet));
            }
            return events;
        }
    }
    @Override
    public EventsEntity save(EventsEntity eventsEntity) {
        try(Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO events (event_name, event_city) VALUES(?,?)");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
