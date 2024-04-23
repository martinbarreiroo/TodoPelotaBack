package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface SoccerMatchRepository extends JpaRepository<SoccerMatch, Long> {
    Set<SoccerMatch> findByTournamentId(Long tournamentId);

    Set<SoccerMatch> findByTournament(Tournament tournament);
}