package org.example.util.json.jsonEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;
import org.example.util.json.jsonEvent.JsonConvector;

import java.util.List;

public class JsonConvectorImpl implements JsonConvector {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String toJson(EventDTO eventDTO) {
        try {
            return mapper.writeValueAsString(eventDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toJson(List<EventDTO> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch ( JsonProcessingException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public EventDTO toDTO(String json) {
        try {
            return mapper.readValue(json, EventDTO.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
