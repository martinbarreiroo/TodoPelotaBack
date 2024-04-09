package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public Tournament createTournament(String name) {
        Tournament tournament = new Tournament();
        tournament.setName(name);
        return tournamentRepository.save(tournament);
    }
}