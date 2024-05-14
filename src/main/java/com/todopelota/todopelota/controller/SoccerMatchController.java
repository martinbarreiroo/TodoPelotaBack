package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.CreateMatchRequest;
import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import com.todopelota.todopelota.service.PositionService;
import com.todopelota.todopelota.service.SoccerMatchService;
import com.todopelota.todopelota.service.TournamentService;
import com.todopelota.todopelota.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/matches")
@CrossOrigin(origins = "http://localhost:3000")
public class SoccerMatchController {

    @Autowired
    private SoccerMatchService soccerMatchService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SoccerMatchRepository soccerMatchRepository;

    @Autowired
    private PositionService positionService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SoccerMatch> createMatch(@RequestBody CreateMatchRequest request) {
        SoccerMatch newMatch = new SoccerMatch();
        newMatch.setDate(request.getDate());
        newMatch.setLocation(request.getLocation());
        newMatch.setDescription(request.getDescription());
        newMatch.setTeam1(request.getTeam1());
        newMatch.setTeam2(request.getTeam2());

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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Set<SoccerMatch>> getMatchesByTournament(@PathVariable Long tournamentId) {
        Set<SoccerMatch> matches = soccerMatchService.findMatchesByTournamentId(tournamentId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/get_match/{matchId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Optional<SoccerMatch>> getMatchById(@PathVariable Long matchId) {
        Optional<SoccerMatch> match = soccerMatchService.findMatchById(matchId);
        if (match.isPresent()) {
            return ResponseEntity.ok(match);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SoccerMatch> updateMatch(@RequestBody CreateMatchRequest request) {
        String matchId = request.getMatchId();
        Optional<SoccerMatch> matchOpt = soccerMatchService.findMatchById(Long.parseLong(matchId));
        if (matchOpt.isPresent()) {
            SoccerMatch match = matchOpt.get();
            match.setResult1(Integer.parseInt(request.getResult1()));
            match.setResult2(Integer.parseInt(request.getResult2()));
            match.setYellowCards(Integer.parseInt(request.getYellowCards()));
            match.setRedCards(Integer.parseInt(request.getRedCards()));
            match.setGoals(Integer.parseInt(request.getGoals()));
            match.setAssists(Integer.parseInt(request.getAssists()));
            match.setTeam1Points(request.getTeam1Points());
            match.setTeam2Points(request.getTeam2Points());
            match.setHasBeenUpdated(true);
            SoccerMatch updatedMatch = soccerMatchRepository.save(match);
            // update positions for all users in both teams
            for (String userName : match.getTeam1()) {
                User user = userService.findUserByUsername(userName);
                positionService.updatePosition(user, match.getTournament());
            }
            for (String userName : match.getTeam2()) {
                User user = userService.findUserByUsername(userName);
                positionService.updatePosition(user, match.getTournament());
            }
            return ResponseEntity.ok(updatedMatch);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{matchId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long matchId) {
        soccerMatchService.deleteMatch(matchId);
        return ResponseEntity.ok().build();
    }
}