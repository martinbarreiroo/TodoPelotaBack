package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Optional<Tournament> findById(Long tournamentId);
}