package org.example.repository.mapper;

import org.example.model.EventsEntity;
import org.example.servlet.dto.EventDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventsResultSetMapperImpl implements EventsResultSetMapper {
    @Override
    public EventsEntity map(ResultSet resultSet) {
        try {
            EventsEntity eventsEntity = new EventsEntity();
            eventsEntity.setId(resultSet.getLong("event_id"));
            eventsEntity.setName(resultSet.getString("event_name"));
            eventsEntity.setCity(resultSet.getString("event_city"));
            return eventsEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
