package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.Invitation;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Iterable<Invitation> findByUserId(Long userId);

    Optional<Invitation> findBySenderAndUserAndTournament(User sender, User user, Tournament tournament);
}