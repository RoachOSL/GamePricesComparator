package dev.Roach.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.Store;
import dev.Roach.util.SharedObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class StoresMapper {
    private static final ObjectMapper objectMapper = SharedObjectMapper.getObjectMapper();

    public List<Store> mapArrayOfAllStoresToJava(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ArrayList<Store>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
