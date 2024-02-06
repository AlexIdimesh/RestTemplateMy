package org.example.repository.mapper.eventstags;

import org.example.model.EventsTagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventsTagResultSetMapperImpl implements EventsTagResultSetMapper {
    @Override
    public EventsTagEntity map(ResultSet resultSet) {
        try {
            EventsTagEntity eventsTagEntity = new EventsTagEntity();
            eventsTagEntity.setId(resultSet.getLong("event_tag_id"));
            eventsTagEntity.setTagName(resultSet.getString("event_tag_name"));
            eventsTagEntity.setTagAuthor(resultSet.getString("event_tag_author"));
            return eventsTagEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
