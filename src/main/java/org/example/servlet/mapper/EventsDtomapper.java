package org.example.servlet.mapper;

import org.example.model.EventsEntity;
import org.example.servlet.dto.IncomingDto;
import org.example.servlet.dto.OutGoingDto;

public interface EventsDtomapper {
    EventsEntity map(IncomingDto incomingDto);

    OutGoingDto map(EventsEntity eventsEntity);
}
