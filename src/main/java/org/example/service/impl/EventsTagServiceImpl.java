package org.example.service.impl;

import org.example.model.EventsTagEntity;
import org.example.repository.impl.EventsTagEntityRepositoryImpl;
import org.example.repository.rep.ext.EventsTagEntityRepositoryExt;
import org.example.service.serverImpl.EventsTagService;
import org.example.servlet.dto.EventsTagDTO;
import org.example.servlet.mapper.EventsTagMapperMapstruct;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EventsTagServiceImpl implements EventsTagService {

    private EventsTagEntityRepositoryExt repository = new EventsTagEntityRepositoryImpl();

    private EventsTagMapperMapstruct mapper = EventsTagMapperMapstruct.INSTANCE;


    @Override
    public EventsTagDTO save(EventsTagDTO eventsTagDTO) {
        return mapper.toDto(repository.save(mapper.toEntity(eventsTagDTO)));
    }

    @Override
    public EventsTagDTO findById(Long id) {
        EventsTagEntity eventsTagEntity =  repository.findById(id);
        return mapper.toDto(eventsTagEntity);
    }

    @Override
    public List<EventsTagDTO> findAll() throws SQLException, ClassNotFoundException {
        return repository.findAll().stream().map(mapper :: toDto).collect(toList());
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public EventsTagDTO upDated(EventsTagDTO eventsTagDTO) {
        return mapper.toDto(repository.save(mapper.toEntity(eventsTagDTO)));
    }
}
