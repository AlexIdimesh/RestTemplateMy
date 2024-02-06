package org.example.repository.mapper.events;

import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;
import org.example.repository.mapper.events.EventsResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventsResultSetMapperImpl implements EventsResultSetMapper {
    @Override
    public EventsEntity map(ResultSet resultSet) {
        try {
            EventsEntity eventsEntity = new EventsEntity();
            eventsEntity.setId(resultSet.getLong("events_id"));
            eventsEntity.setName(resultSet.getString("events_name"));
            eventsEntity.setCity(resultSet.getString("events_city"));
            return eventsEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
