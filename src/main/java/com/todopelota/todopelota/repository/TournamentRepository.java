package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}