package com.todopelota.todopelota.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        jsonGenerator.writeStringField("maxParticipants", tournament.getMaxParticipants());
        jsonGenerator.writeStringField("description", tournament.getDescription());
        jsonGenerator.writeStringField("type", tournament.getType());
        jsonGenerator.writeStringField("adminId", String.valueOf(tournament.getAdminId()));
        jsonGenerator.writeStringField("adminUsername", tournament.getAdminUsername());
        jsonGenerator.writeStringField("participants", tournament.getInvitedUsersToString().toString());

        List<String> allParticipants = new ArrayList<>();
        allParticipants.add(tournament.getCreator().getUsername());
        allParticipants.addAll(tournament.getInvitedUsers().stream()
                .map(User::getUsername)
                .toList());
        jsonGenerator.writeObjectField("allParticipants", allParticipants);

        jsonGenerator.writeEndObject();
    }
}
