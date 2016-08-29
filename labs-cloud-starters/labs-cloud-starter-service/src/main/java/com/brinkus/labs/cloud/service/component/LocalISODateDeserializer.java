package com.brinkus.labs.cloud.service.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalISODateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        LocalDate localDate;
        try {
            String text = jp.getText();
            localDate = LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Allowed format is: yyyy-mm-dd!", e);
        }
        return localDate;
    }

}