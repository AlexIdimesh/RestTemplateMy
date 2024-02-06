package org.example.service.serverImpl;

import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.ParticipantsDTO;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantsService {

    ParticipantsDTO save(ParticipantsDTO participantsDTO);

    ParticipantsDTO findById(Long id);

    List<ParticipantsDTO> findByIdEvents(Long id);

    List<ParticipantsDTO> findAll() throws SQLException, ClassNotFoundException;

    boolean deleteById(Long id);

    ParticipantsDTO upDated(ParticipantsDTO participantsDTO);
}
