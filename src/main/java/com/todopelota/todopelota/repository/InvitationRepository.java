package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Iterable<Invitation> findByUserId(Long userId);
}