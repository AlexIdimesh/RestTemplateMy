package org.example.repository;

import org.example.model.EventsEntity;
import org.example.servlet.dto.EventDTO;

import java.util.UUID;

public interface EventsEntityRepository extends EventsRepository<EventsEntity, Long> {
}
