package org.example.service.impl;

import org.example.model.EventsEntity;
import org.example.repository.impl.EventsEntityRepositoryImpl;
import org.example.service.EventsService;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.mapper.EventsMapperMapstruct;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class EventsServiceImpl implements EventsService {

    private EventsEntityRepositoryImpl eventsEntityRepository;
    private EventsMapperMapstruct mapper;

    public EventsServiceImpl(EventsEntityRepositoryImpl eventsEntityRepository) {
        this.eventsEntityRepository = eventsEntityRepository;
    }

    @Override
    public EventDTO save(EventDTO eventDTO) {
        return eventsEntityRepository.save((EventsEntity) eventDTO);
    }
    @Override
    public EventDTO findById(Long id) {
        return mapper.eventToEventDTO(eventsEntityRepository.findById(id));
    }
    @Override
    public List<EventDTO> findAll() throws SQLException, ClassNotFoundException {
        return eventsEntityRepository.findAll().stream().map((EventDTO eventsEntity)
                -> mapper.eventToEventDTO((EventsEntity) eventsEntity)).collect(Collectors.toList());
    }
    @Override
    public boolean deleteById(Long id) {
        return eventsEntityRepository.deleteById(id);
    }


}
