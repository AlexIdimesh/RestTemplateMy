package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.DBConnection;
import org.example.model.EventsTagEntity;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapper;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapperImpl;
import org.example.repository.rep.ext.EventsTagEntityRepositoryExt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventsTagEntityRepositoryImpl implements EventsTagEntityRepositoryExt {

    private ConnectionManager connectionManager;

    private EventsTagResultSetMapper resultSetMapper;


    public EventsTagEntityRepositoryImpl() {
        this(new DBConnection(), new EventsTagResultSetMapperImpl());
    }

    public EventsTagEntityRepositoryImpl(DBConnection dbConnection, EventsTagResultSetMapperImpl eventsTagResultSetMapper) {
        this.connectionManager = dbConnection;
        this.resultSetMapper = eventsTagResultSetMapper;
    }

    @Override
    public Optional<EventsTagEntity> findById(Long id) {
        try(Connection connection = connectionManager.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM  event_tags WHERE event_tag_id = ?");
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            EventsTagEntity eventsTagEntity = null;
            if (resultSet.next()) {
                eventsTagEntity = resultSetMapper.map(resultSet);
            }
            return Optional.ofNullable(eventsTagEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM event_tags WHERE event_tag_id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EventsTagEntity> findAll() throws SQLException, ClassNotFoundException {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM event_tags");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<EventsTagEntity> eventsTag = new ArrayList<>();
            while (resultSet.next()) {
                eventsTag.add(resultSetMapper.map(resultSet));
            }
            return eventsTag;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EventsTagEntity save(EventsTagEntity eventsTagEntity) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO event_tags (event_tag_author, event_tag_name) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, eventsTagEntity.getTagName());
            preparedStatement.setString(2, eventsTagEntity.getTagAuthor());
            preparedStatement.executeUpdate();
            return eventsTagEntity;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public EventsTagEntity upDated(EventsTagEntity eventsTagEntity) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE event_tags SET event_tag_author = ?, event_tag_name = ? WHERE event_tag_id = ?");
            preparedStatement.setObject(1, eventsTagEntity.getTagAuthor());
            preparedStatement.setObject(2, eventsTagEntity.getTagName());
            preparedStatement.setObject(3, eventsTagEntity.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return eventsTagEntity;
            } else {
                throw new RuntimeException("Не удалось обновить запись с ID: " + eventsTagEntity.getId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Ошибка при обновлении записи", e);
        }
    }
}
