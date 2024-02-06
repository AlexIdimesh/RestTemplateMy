package org.example.repository.mapper.eventstags;

import org.example.model.EventsTagEntity;

import java.sql.ResultSet;

public interface EventsTagResultSetMapper {

    EventsTagEntity map(ResultSet resultSet);
}
