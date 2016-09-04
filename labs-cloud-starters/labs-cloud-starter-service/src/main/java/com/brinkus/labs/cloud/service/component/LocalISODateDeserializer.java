package com.brinkus.labs.cloud.service.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Deserialize ISO date (YYYY-MM-DD) formatted text to {@link LocalDate}.
 */
public class LocalISODateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        try {
            return LocalDate.parse(jp.getText());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Allowed format is: yyyy-mm-dd!", e);
        }
    }

}