package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import com.todopelota.todopelota.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoccerMatchService {

    @Autowired
    private SoccerMatchRepository soccerMatchRepository;


    private TournamentRepository tournamentRepository;

    public SoccerMatch createMatch(SoccerMatch soccerMatch) {
        return soccerMatchRepository.save(soccerMatch);
    }

    public List<SoccerMatch> findMatchesByTournamentId(Long tournamentId) {
        return soccerMatchRepository.findByTournamentId(tournamentId);
    }

}