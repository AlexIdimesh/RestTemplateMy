package org.example.service.serverImpl;

import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;

import java.sql.SQLException;
import java.util.List;

public interface EventsTagService {

    EventsTagDTO save(EventsTagDTO eventsTagDTO);

    EventsTagDTO findById(Long id);

    List<EventsTagDTO> findAll() throws SQLException, ClassNotFoundException;

    boolean deleteById(Long id);

    EventsTagDTO upDated(EventsTagDTO eventsTagDTO);
}
