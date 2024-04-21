package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.CreateMatchRequest;
import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import com.todopelota.todopelota.service.SoccerMatchService;
import com.todopelota.todopelota.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matches")
@CrossOrigin
public class SoccerMatchController {

    @Autowired
    private SoccerMatchService soccerMatchService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SoccerMatchRepository soccerMatchRepository;

    @PostMapping("/create")
    public ResponseEntity<SoccerMatch> createMatch(@RequestBody CreateMatchRequest request) {
        SoccerMatch newMatch = new SoccerMatch();
        newMatch.setDate(request.getDate());
        newMatch.setLocation(request.getLocation());
        newMatch.setDescription(request.getDescription());

        Optional<Tournament> tournamentOpt = tournamentService.findTournamentById(request.getTournamentId());
        if (tournamentOpt.isPresent()) {
            newMatch.setTournament(tournamentOpt.get());
        } else {
            throw new RuntimeException("Tournament not found");
        }

        SoccerMatch createdMatch = soccerMatchService.createMatch(newMatch);
        return ResponseEntity.ok(createdMatch);
    }


    @GetMapping("/get/{tournamentId}")
    public ResponseEntity<List<SoccerMatch>> getMatchesByTournament(@PathVariable Long tournamentId) {
        List<SoccerMatch> matches = soccerMatchService.findMatchesByTournamentId(tournamentId);
        return ResponseEntity.ok(matches);
    }
}