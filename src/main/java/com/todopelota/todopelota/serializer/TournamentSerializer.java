package com.todopelota.todopelota.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.todopelota.todopelota.model.Tournament;

import java.io.IOException;

public class TournamentSerializer extends StdSerializer<Tournament> {

    public TournamentSerializer() {
        this(null);
    }

    public TournamentSerializer(Class<Tournament> t) {
        super(t);
    }

    @Override
    public void serialize(
            Tournament tournament,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", tournament.getId());
        jsonGenerator.writeStringField("name", tournament.getName());
        jsonGenerator.writeNumberField("maxParticipants", tournament.getMaxParticipants());
        jsonGenerator.writeStringField("description", tournament.getDescription());
        jsonGenerator.writeStringField("type", tournament.getType());
        jsonGenerator.writeStringField("adminId", String.valueOf(tournament.getAdmin()));
        jsonGenerator.writeEndObject();
    }
}
