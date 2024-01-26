package org.example.repository.mapper;

import org.example.model.EventsEntity;

import java.sql.ResultSet;

public interface EventsResultSetMapper {
    EventsEntity map(ResultSet resultSet);
}
