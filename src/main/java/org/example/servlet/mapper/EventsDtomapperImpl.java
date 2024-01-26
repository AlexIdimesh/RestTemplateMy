package org.example.servlet.mapper;

import org.example.model.EventsEntity;
import org.example.servlet.dto.IncomingDto;
import org.example.servlet.dto.OutGoingDto;

public class EventsDtomapperImpl implements EventsDtomapper {
    @Override
    public EventsEntity map(IncomingDto incomingDto) {
        return null;
    }

    @Override
    public OutGoingDto map(EventsEntity eventsEntity) {
        return null;
    }
}
