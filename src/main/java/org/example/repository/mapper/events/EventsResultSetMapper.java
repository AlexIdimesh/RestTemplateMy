package org.example.repository.mapper.events;

import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;

import java.sql.ResultSet;

public interface EventsResultSetMapper {
    EventsEntity map(ResultSet resultSet);
}
