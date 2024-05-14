package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByUserIdAndTournamentId(Long userId, Long tournamentId);
}