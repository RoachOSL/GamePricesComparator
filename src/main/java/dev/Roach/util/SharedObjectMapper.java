package dev.Roach.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class SharedObjectMapper {
    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private SharedObjectMapper() {
        // Private constructor to prevent instantiation
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}