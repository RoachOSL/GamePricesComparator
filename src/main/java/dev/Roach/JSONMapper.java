package dev.Roach;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public <T> List<T> mapArrayToJava(List<String> jsonObjects, Class<T> classPlaceholder) {

        List<T> resultList = new ArrayList<>();

        try {
            for (String jsonObject : jsonObjects) {
                T mappedObject = objectMapper.readValue(jsonObject, classPlaceholder);
                resultList.add(mappedObject);
            }
            return resultList;
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