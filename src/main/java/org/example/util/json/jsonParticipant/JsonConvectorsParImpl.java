package org.example.util.json.jsonParticipant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.servlet.dto.ParticipantsDTO;
import org.example.util.json.jsonParticipant.JsonConvectorsPar;

import java.util.List;

public class JsonConvectorsParImpl implements JsonConvectorsPar {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String toJson(ParticipantsDTO participantsDTO) {
        try {
            return mapper.writeValueAsString(participantsDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toJson(List<ParticipantsDTO> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch ( JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
    @Override
    public ParticipantsDTO toDTO(String json) {
        try {
            return mapper.readValue(json, ParticipantsDTO.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
