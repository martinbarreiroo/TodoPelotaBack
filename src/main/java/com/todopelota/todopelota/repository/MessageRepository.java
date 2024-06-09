package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.Message;
import com.todopelota.todopelota.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTournament(Tournament tournament);
}
