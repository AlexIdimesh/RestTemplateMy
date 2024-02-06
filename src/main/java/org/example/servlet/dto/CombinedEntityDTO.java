package org.example.servlet.dto;

import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;

public class CombinedEntityDTO {
    private EventsEntity eventsEntity;

    private EventsTagEntity eventsTagEntity;

    public CombinedEntityDTO(EventsEntity eventsEntity, EventsTagEntity eventsTagEntity) {
        this.eventsEntity = eventsEntity;
        this.eventsTagEntity = eventsTagEntity;
    }

    public EventsEntity getEventsEntity() {
        return eventsEntity;
    }

    public void setEventsEntity(EventsEntity eventsEntity) {
        this.eventsEntity = eventsEntity;
    }

    public EventsTagEntity getEventsTagEntity() {
        return eventsTagEntity;
    }

    public void setEventsTagEntity(EventsTagEntity eventsTagEntity) {
        this.eventsTagEntity = eventsTagEntity;
    }

    @Override
    public String toString() {
        return "CombinedEntity{" +
                "eventsEntity=" + eventsEntity +
                ", eventsTagEntity=" + eventsTagEntity +
                '}';
    }
}
