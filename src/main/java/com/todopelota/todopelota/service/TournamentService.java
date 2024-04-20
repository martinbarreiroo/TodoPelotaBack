package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Role;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public Tournament createTournament(String name, Integer maxPlayers, String type, String description, User creator) {

        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setMaxParticipants(maxPlayers);
        tournament.setType(type);
        tournament.setDescription(description);
        tournament.setAdmin(creator.getUsername());
        creator.setRole(Role.ADMIN);
        tournament.setParticipants(creator);
        creator.setTournaments(tournament);
        return tournamentRepository.save(tournament);
    }

    public Optional<Tournament> findTournamentById(Long tournamentId) {

        return tournamentRepository.findById(tournamentId);
    }
}