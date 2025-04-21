package com.example.billing.entity;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not serialize JSON", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, Map.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize JSON", e);
        }
    }
}
// auto-generated minor edit 13824
// auto-generated minor edit 4951
// auto-generated minor edit 1338
// auto-generated minor edit 26518
// Auto-generated change #34
// Auto-generated change #78
// Auto-generated change #80
// minor edit 8510
// Auto-generated change #6
