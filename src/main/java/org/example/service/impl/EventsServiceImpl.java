package org.example.service.impl;

import org.example.model.EventsEntity;
import org.example.repository.impl.EventsEntityRepositoryImpl;
import org.example.service.EventsService;
import org.example.servlet.dto.EventDTO;

import java.sql.SQLException;
import java.util.List;

public class EventsServiceImpl implements EventsService {

    private EventsEntityRepositoryImpl eventsEntityRepository;

    public EventsServiceImpl(EventsEntityRepositoryImpl eventsEntityRepository) {
        this.eventsEntityRepository = eventsEntityRepository;
    }

    @Override
    public EventsEntity save(EventsEntity eventsEntity) {
        return eventsEntityRepository.save(eventsEntity);
    }
    @Override
    public EventsEntity findById(Long id) {
        return eventsEntityRepository.findById(id);
    }
    @Override
    public List<EventsEntity> findAll() throws SQLException, ClassNotFoundException {
        return eventsEntityRepository.findAll();
    }
    @Override
    public boolean deleteById(Long id) {
        return eventsEntityRepository.deleteById(id);
    }


}
