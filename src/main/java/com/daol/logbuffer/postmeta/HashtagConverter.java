package com.daol.logbuffer.postmeta;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import java.util.stream.Stream;

@Converter
public class HashtagConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String hashtag : attribute) {
            sb.append(hashtag).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Stream.of(dbData.split(",")).toList();
    }
}