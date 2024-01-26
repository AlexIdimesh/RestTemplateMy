package org.example.service;

import org.example.model.EventsEntity;

import java.util.UUID;

public interface EventsService {
    EventsEntity save(EventsEntity eventsEntity);

    EventsEntity findById(UUID uuid);

    EventsEntity findAll();

    boolean deleteById(UUID uuid);

}
