package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByUserIdAndTournamentId(Long userId, Long tournamentId);

    List<Position> findByTournamentId(Long tournamentId);

    Position findByUserId(Long userId);

}