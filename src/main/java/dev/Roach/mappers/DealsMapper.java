package dev.Roach.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.Deal;
import dev.Roach.util.SharedObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class DealsMapper {
    private static final ObjectMapper objectMapper = SharedObjectMapper.getObjectMapper();

    public Deal mapDealToJava(String json) {
        try {
            return objectMapper.readValue(json, Deal.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Deal> mapArrayOfDealsToJava(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ArrayList<Deal>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
