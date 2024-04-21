package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.UserRepository;
import com.todopelota.todopelota.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tournaments")
@CrossOrigin
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Tournament> createTournament(@RequestBody Map<String, Object> tournament) {
        Tournament newTournament = new Tournament();
        newTournament.setName((String) tournament.get("name"));
        newTournament.setMaxParticipants(Integer.parseInt((String) tournament.get("players")));
        newTournament.setType((String) tournament.get("type"));
        newTournament.setDescription((String) tournament.get("description"));
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> creator = userRepository.findByUsername(principal.getUsername());
        if (creator.isPresent()) {
            Long adminId = Long.parseLong((String) tournament.get("adminId"));
            Tournament createdTournament = tournamentService.createTournament(newTournament.getName(), newTournament.getMaxParticipants(), newTournament.getType(), newTournament.getDescription(), creator.get(), adminId);
            return ResponseEntity.ok(createdTournament);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get/{tournament_id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Optional<Tournament>> getTournamentByName(@PathVariable Long tournament_id) {
        Optional<Tournament> tournament = tournamentService.findTournamentById(tournament_id);
        if (tournament.isPresent()) {
            return ResponseEntity.ok(tournament);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}