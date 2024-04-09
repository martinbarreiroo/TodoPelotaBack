package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        Tournament createdTournament = tournamentService.createTournament(tournament.getName());
        return ResponseEntity.ok(createdTournament);
    }
}