package org.example.service.impl;

import org.example.model.EventsEntity;
import org.example.repository.EventsEntityRepository;
import org.example.repository.impl.EventsEntityRepositoryImpl;
import org.example.service.EventsService;

import java.util.UUID;

public class EventsServiceImpl implements EventsService {
    private EventsEntityRepositoryImpl eventsEntityRepository;

    public EventsServiceImpl(EventsEntityRepositoryImpl eventsEntityRepository) {
        this.eventsEntityRepository = eventsEntityRepository;
    }
    @Override
    public EventsEntity save(EventsEntity eventsEntity) {
        if (eventsEntity.getCity() == null && eventsEntity.getName() == null) {
            eventsEntity.setCity(eventsEntity.getCity());
            eventsEntity.setName(eventsEntity.getName());
        }
        return eventsEntityRepository.save(eventsEntity);
    }
    @Override
    public EventsEntity findById(UUID uuid) {
        return eventsEntityRepository.findById(uuid);
    }
    @Override
    public EventsEntity findAll() {
        return eventsEntityRepository.findAll();
    }
    @Override
    public boolean deleteById(UUID uuid) {
        return eventsEntityRepository.deleteById(uuid);
    }


}
