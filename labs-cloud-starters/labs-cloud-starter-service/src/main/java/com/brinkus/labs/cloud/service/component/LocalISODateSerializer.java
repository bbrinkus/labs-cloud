package com.brinkus.labs.cloud.service.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Serialize {@link LocalDate} to ISO date (YYYY-MM-DD) formatted text.
 */
public class LocalISODateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(final LocalDate value,
                          final JsonGenerator gen,
                          final SerializerProvider serializers) throws IOException {
        gen.writeString(DateTimeFormatter.ISO_DATE.format(value));
    }

}