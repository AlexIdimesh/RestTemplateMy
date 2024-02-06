package org.example.service.impl;

import org.example.model.CombinedEntity;
import org.example.model.EventsEntity;
import org.example.repository.rep.ext.EntityEntityRepositoryExt;
import org.example.repository.impl.EventsEntityRepositoryImpl;
import org.example.service.serverImpl.EventsService;
import org.example.servlet.dto.CombinedEntityDTO;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.mapper.CombinedEntityMapstruct;
import org.example.servlet.mapper.EventsMapperMapstruct;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class EventsServiceImpl implements EventsService {

    private EntityEntityRepositoryExt repository = new EventsEntityRepositoryImpl();
    private EventsMapperMapstruct mapper = EventsMapperMapstruct.INSTANCE;

    private CombinedEntityMapstruct mapstruct = CombinedEntityMapstruct.INSTANCE;


    @Override
    public EventDTO save(EventDTO eventDTO) {
        return mapper.toDTO(repository.save(mapper.toEntity(eventDTO)));
    }

    @Override
    public List<CombinedEntityDTO> findEventTagsByEventId(Long id) {
        List<CombinedEntity> entityList = repository.findAllEventsByEventTag(id);
        return entityList.stream().map(mapstruct::toDTO).collect(Collectors.toList());
    }
    @Override
    public EventDTO findById(Long id) {
        EventsEntity eventsEntity = repository.findById(id);
        return mapper.toDTO(eventsEntity);
    }
    @Override
    public List<EventDTO> findAll() throws SQLException, ClassNotFoundException {
        return repository.findAll().stream().map(mapper :: toDTO).collect(toList());
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public EventDTO upDated(EventDTO eventDTO) {
        return mapper.toDTO(repository.upDated(mapper.toEntity(eventDTO)));
    }
}
