package org.example.service.serverImpl;

import org.example.servlet.dto.CombinedEntityDTO;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;

import java.sql.SQLException;
import java.util.List;

public interface EventsService {
    EventDTO save(EventDTO eventDTO);

    List<CombinedEntityDTO> findEventTagsByEventId(Long id);

    EventDTO findById(Long id);

    List<EventDTO> findAll() throws SQLException, ClassNotFoundException;

    boolean deleteById(Long id);

    EventDTO upDated(EventDTO eventDTO);
}
