package org.example.repository.impl;

import org.example.db.DBConnection;
import org.example.model.CombinedEntity;
import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapper;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapperImpl;
import org.example.repository.rep.ext.EntityEntityRepositoryExt;
import org.example.repository.mapper.events.EventsResultSetMapper;
import org.example.repository.mapper.events.EventsResultSetMapperImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventsEntityRepositoryImpl implements EntityEntityRepositoryExt {
    private EventsResultSetMapper resultSetMapper;

    private EventsTagResultSetMapper resultSetMapperTag;
    private DBConnection connectionManager;

    public EventsEntityRepositoryImpl() {
        this(new DBConnection(), new EventsResultSetMapperImpl(), new EventsTagResultSetMapperImpl());
    }

    public EventsEntityRepositoryImpl(DBConnection dbConnection, EventsResultSetMapperImpl eventsResultSetMapper, EventsTagResultSetMapperImpl eventsTagResultSetMapper) {
        this.connectionManager = dbConnection;
        this.resultSetMapper = eventsResultSetMapper;
        this.resultSetMapperTag = eventsTagResultSetMapper;
    }
    @Override
    public EventsEntity findById(Long id) {
        try(Connection connection = connectionManager.getConnection()){
            PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM events WHERE events_id = ?");
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            EventsEntity eventsEntity = null;
            if (resultSet.next()) {
                eventsEntity = resultSetMapper.map(resultSet);
            }
            return eventsEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<CombinedEntity> findAllEventsByEventTag(Long id) {
        List<CombinedEntity> entityList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("SELECT e.*, et.* FROM events_event_tags eet JOIN events e ON eet.id" +
                                    " = e.events_id JOIN event_tags et ON eet.event_tag_id = et.event_tag_id WHERE eet.id = ?");
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EventsEntity eventsEntity = resultSetMapper.map(resultSet);
                EventsTagEntity eventsTagEntity = resultSetMapperTag.map(resultSet);
                CombinedEntity combinedEntity = new CombinedEntity(eventsEntity, eventsTagEntity);
                entityList.add(combinedEntity);
            }
            return entityList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EventsEntity> findAll() {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events ");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<EventsEntity> events = new ArrayList<>();
            while (resultSet.next()) {
                events.add(resultSetMapper.map(resultSet));
            }
            return events;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean deleteById(Long id) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM events WHERE events_id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public EventsEntity save(EventsEntity eventsEntity) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO events (events_name, events_city) VALUES(?,?)");
            preparedStatement.setString(1, eventsEntity.getName());
            preparedStatement.setString(2, eventsEntity.getCity());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                eventsEntity.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Вставка неудачна, не удалось получить сгенерированный ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return eventsEntity;
    }
    @Override
    public EventsEntity upDated(EventsEntity eventsEntity) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE events SET events_city = ?, events_name = ? WHERE events_id = ?");
            preparedStatement.setObject(1, eventsEntity.getCity());
            preparedStatement.setObject(2,eventsEntity.getName());
            preparedStatement.setObject(3,eventsEntity.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
