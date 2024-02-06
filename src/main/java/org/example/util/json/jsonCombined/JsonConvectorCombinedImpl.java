package org.example.util.json.jsonCombined;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.servlet.dto.CombinedEntityDTO;

import java.util.List;

public class JsonConvectorCombinedImpl implements JsonConvectorCombined {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String toJson(CombinedEntityDTO combinedEntityDTO) {
        try {
            return mapper.writeValueAsString(combinedEntityDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJson(List<CombinedEntityDTO> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CombinedEntityDTO toDTO(String json) {
        try {
            return mapper.readValue(json, CombinedEntityDTO.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
