package com.brinkus.labs.cloud.service.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalISODateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(DateTimeFormatter.ISO_DATE.format(value));
    }

}