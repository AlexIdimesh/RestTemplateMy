package org.example.service;

import org.example.model.EventsEntity;
import org.example.servlet.dto.EventDTO;

import java.sql.SQLException;
import java.util.List;

public interface EventsService {
    EventDTO save(EventDTO eventDTO);

    EventDTO findById(Long id);

    List<EventDTO> findAll() throws SQLException, ClassNotFoundException;

    boolean deleteById(Long id);
}
