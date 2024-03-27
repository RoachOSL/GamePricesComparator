package dev.Roach.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.Game;
import dev.Roach.util.SharedObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GameMapper {
    private final ObjectMapper objectMapper = SharedObjectMapper.getObjectMapper();

    public List<Game> mapArrayOfGamePojoToJava(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ArrayList<Game>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
