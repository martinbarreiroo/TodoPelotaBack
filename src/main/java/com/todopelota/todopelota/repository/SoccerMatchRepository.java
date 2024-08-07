package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface SoccerMatchRepository extends JpaRepository<SoccerMatch, Long> {
    Set<SoccerMatch> findByTournamentId(Long tournamentId);

    Set<SoccerMatch> findByTournamentAndId(Tournament tournament, Long matchId);

    List<SoccerMatch> findByUsersContainsAndTournament(User user, Tournament tournament);

    List<SoccerMatch> findByDateBetween(ZonedDateTime start, ZonedDateTime end);

    Set<SoccerMatch> findByTournamentAndHasBeenUpdated(Tournament tournament, Boolean hasBeenUpdated);
}