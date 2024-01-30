package org.example.service;

import org.example.model.EventsEntity;
import org.example.servlet.dto.EventDTO;

import java.sql.SQLException;
import java.util.List;

public interface EventsService {
    EventsEntity save(EventsEntity eventsEntity);

    EventsEntity findById(Long id);

    List<EventsEntity> findAll() throws SQLException, ClassNotFoundException;

    boolean deleteById(Long id);

}
