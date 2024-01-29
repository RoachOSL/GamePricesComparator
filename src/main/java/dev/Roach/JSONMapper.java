package dev.Roach;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.deal.DealAllPojo;

import java.util.ArrayList;
import java.util.List;


public class JSONMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T mapToJava(String jsonObject, Class<T> classPlaceholder) {

        try {
            return objectMapper.readValue(jsonObject, classPlaceholder);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DealAllPojo> mapArrayOfDealsToJava(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ArrayList<DealAllPojo>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<List<T>> mapDoubleArrayToJava(String jsonArray, TypeReference<List<List<T>>> typeReference) {
        try {
            return objectMapper.readValue(jsonArray, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> String mapToJSON(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}