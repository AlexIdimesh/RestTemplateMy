package org.example.util.json.jsonEventsTag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.servlet.dto.EventsTagDTO;

import java.util.List;

public class JsonConvectorTagImpl implements JsonConvectorTag {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String toJson(EventsTagDTO eventsTagDTO) {
        try {
            return mapper.writeValueAsString(eventsTagDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJson(List<EventsTagDTO> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch ( JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
    @Override
    public EventsTagDTO toDTO(String json) {
        try {
            return mapper.readValue(json, EventsTagDTO.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
