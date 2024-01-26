package org.example.repository.mapper;

import org.example.model.EventsEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventsResultSetMapperImpl implements EventsResultSetMapper {
    @Override
    public EventsEntity map(ResultSet resultSet) {
        try {
            EventsEntity eventsEntity = new EventsEntity();
            eventsEntity.setName(resultSet.getString("event_name"));
            eventsEntity.setCity(resultSet.getString("event_city"));
            return eventsEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
