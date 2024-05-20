package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.Position;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.TournamentPositionsResponse;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.UserRepository;
import com.todopelota.todopelota.service.PositionService;
import com.todopelota.todopelota.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/tournaments")
@CrossOrigin
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionService positionService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Tournament> createTournament(@RequestBody Map<String, Object> tournament) {
        Tournament newTournament = new Tournament();
        newTournament.setName((String) tournament.get("name"));
        newTournament.setMaxParticipants((String) tournament.get("players"));
        newTournament.setType((String) tournament.get("type"));
        newTournament.setDescription((String) tournament.get("description"));
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> creator = userRepository.findByUsername(principal.getUsername());
        if (creator.isPresent()) {
            Long adminId = Long.parseLong((String) tournament.get("adminId"));
            Optional<User> admin = userRepository.findById(adminId);
            if (admin.isPresent()) {
                newTournament.setAdminUsername(admin.get().getUsername());
                newTournament.setAdminId(adminId);
                Tournament createdTournament = tournamentService.createTournament(newTournament.getName(), newTournament.getMaxParticipants(), newTournament.getType(), newTournament.getDescription(), creator.get(), adminId);
                return ResponseEntity.ok(createdTournament);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get/{tournament_id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Optional<Tournament>> getTournamentByName(@PathVariable String tournament_id) {
        Long tournamentId = Long.parseLong(tournament_id);
        Optional<Tournament> tournament = tournamentService.findTournamentById(tournamentId);
        if (tournament.isPresent()) {
            return ResponseEntity.ok(tournament);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{tournamentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long tournamentId, @RequestBody Map<String, Object> tournament) {
        Optional<Tournament> existingTournament = tournamentService.findTournamentById(tournamentId);
        if (existingTournament.isPresent()) {
            Tournament updatedTournament = existingTournament.get();
            updatedTournament.setName((String) tournament.get("name"));
            updatedTournament.setDescription((String) tournament.get("description"));
            try {
                updatedTournament.setMaxParticipants((String) tournament.get("maxParticipants"));
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(updatedTournament);
            }
            updatedTournament.setType((String) tournament.get("type"));
            Tournament savedTournament = tournamentService.save(updatedTournament);
            return ResponseEntity.ok(savedTournament);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{tournamentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long tournamentId) {
        tournamentService.deleteTournament(tournamentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/positions/{tournamentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TournamentPositionsResponse> getTournamentPositions(@PathVariable Long tournamentId) {
        Optional<Tournament> tournamentOpt = tournamentService.findTournamentById(tournamentId);
        if (tournamentOpt.isPresent()) {
            Tournament tournament = tournamentOpt.get();
            List<Position> positions = positionService.getPositionsByTournamentId(tournamentId);
            if (positions.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                TournamentPositionsResponse response = new TournamentPositionsResponse();
                response.setTournamentName(tournament.getName());
                response.setPositions(positions);
                return ResponseEntity.ok(response);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}