package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.*;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import com.todopelota.todopelota.repository.UserRepository;
import com.todopelota.todopelota.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

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

    @PutMapping("/update/{matchId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SoccerMatch> updateMatchInfo(@PathVariable Long matchId, @RequestBody CreateMatchRequest match) {
        Optional<SoccerMatch> existingMatch = soccerMatchService.findMatchById(matchId);
        if (existingMatch.isPresent()) {
            SoccerMatch updatedMatch = existingMatch.get();
            boolean isDateChanged = !Objects.equals(updatedMatch.getDate(), match.getDate());
            boolean isLocationChanged = !Objects.equals(updatedMatch.getLocation(), match.getLocation());
            if (isDateChanged || isLocationChanged) {
                updatedMatch.setNotificationSent(false);
            }
            updatedMatch.setDate(match.getDate());
            updatedMatch.setLocation(match.getLocation());
            updatedMatch.setDescription(match.getDescription());
            SoccerMatch savedMatch = soccerMatchService.updateMatch(updatedMatch);
            return ResponseEntity.ok(savedMatch);
        } else {
            return ResponseEntity.notFound().build();
        }
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
            match.setTeam1Points(request.getTeam1Points());
            match.setTeam2Points(request.getTeam2Points());
            match.setGoals(request.getGoals());
            match.setAssists(request.getAssists());
            match.setYellowCards(request.getYellowCards());
            match.setRedCards(request.getRedCards());
            match.setHasBeenUpdated(true);

            // Update stats for each user

            List<String> allUsers = new ArrayList<>();
            allUsers.addAll(match.getTeam1());
            allUsers.addAll(match.getTeam2());

            for (String userName : allUsers) {
                User user = userService.findUserByUsername(userName);
                userService.updateUserMatches(user);
                userService.updateUserPoints(user);
                userService.updateUserStats(user);
                positionService.updatePositionStats(user, match.getTournament());
            }

            int numberOfGoals = 0;
            for (PlayerStat goal : request.getGoals()) {
                User user = userService.findUserByUsername(goal.getPlayerName());
                userService.updateUserStats(user);
                numberOfGoals += Integer.parseInt(goal.getStat());
                positionService.updatePositionStats(user, match.getTournament());
            }
            match.setNumberOfGoals(numberOfGoals);

            int numberOfAssists = 0;
            for (PlayerStat assist : request.getAssists()) {
                User user = userService.findUserByUsername(assist.getPlayerName());
                userService.updateUserStats(user);
                numberOfAssists += Integer.parseInt(assist.getStat());
                positionService.updatePositionStats(user, match.getTournament());
            }
            match.setNumberOfAssists(numberOfAssists);

            int numberOfYellowCards = 0;
            for (PlayerStat yellowCard : request.getYellowCards()) {
                User user = userService.findUserByUsername(yellowCard.getPlayerName());
                userService.updateUserStats(user);
                numberOfYellowCards += Integer.parseInt(yellowCard.getStat());
                positionService.updatePositionStats(user, match.getTournament() );
            }
            match.setNumberOfYellowCards(numberOfYellowCards);

            int numberOfRedCards = 0;

            for (PlayerStat redCard : request.getRedCards()) {
                User user = userService.findUserByUsername(redCard.getPlayerName());
                userService.updateUserStats(user);
                numberOfRedCards += Integer.parseInt(redCard.getStat());
                positionService.updatePositionStats(user, match.getTournament());
            }
            match.setNumberOfRedCards(numberOfRedCards);

            SoccerMatch updatedMatch = soccerMatchRepository.save(match);

            // Update positions for all users in both teams
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
        Optional<SoccerMatch> match = soccerMatchService.findMatchById(matchId);
        if (match.isPresent()) {
            SoccerMatch soccerMatch = match.get();
            String adminName = soccerMatch.getTournament().getAdminUsername();
            List<String> allUsers = new ArrayList<>();
            allUsers.addAll(soccerMatch.getTeam1());
            allUsers.addAll(soccerMatch.getTeam2());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M, HH:mm");
            String matchDate = soccerMatch.getDate().format(formatter);


            // Run the email notification process in a separate thread
            new Thread(() -> {
                for (String userName : allUsers) {

                    if (!Objects.equals(userName, adminName)) {
                        User user = userService.findUserByUsername(userName);
                        notificationService.alertDeletedMatchToParticipants(soccerMatch, user.getEmail(), user.getUsername(), soccerMatch.getLocation(), matchDate);
                    }
                }
            }).start();
        }
        soccerMatchService.deleteMatch(matchId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rescheduleRequest/{matchId}/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> rescheduleRequest(@PathVariable Long matchId, @PathVariable Long userId) {
        Optional<SoccerMatch> matchOpt = soccerMatchService.findMatchById(matchId);
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Tournament> tournamentOpt = tournamentService.findTournamentById(matchOpt.get().getTournament().getId());
        if (userOpt.isPresent() && tournamentOpt.isPresent()) {
            SoccerMatch match = matchOpt.get();
            User user = userOpt.get();
            Tournament tournament = tournamentOpt.get();

            // Get the tournament's admin
            Optional<User> adminOpt = userRepository.findById(match.getTournament().getAdminId());
            if (adminOpt.isPresent()) {
                User admin = adminOpt.get();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M, HH:mm");
                String matchDate = match.getDate().format(formatter);

                // Run the email notification process in a separate thread
                new Thread(() -> {
                    notificationService.alertRescheduleRequestToAdmin(match, admin.getEmail(), admin.getUsername(), match.getLocation(), matchDate, user.getUsername(), tournament.getName());
                }).start();

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}