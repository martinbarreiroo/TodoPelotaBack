package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.SoccerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoccerMatchRepository extends JpaRepository<SoccerMatch, Long> {
    List<SoccerMatch> findByTournamentId(Long tournamentId);
}