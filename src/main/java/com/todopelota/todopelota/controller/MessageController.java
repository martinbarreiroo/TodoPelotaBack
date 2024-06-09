package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.Message;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.service.MessageService;
import com.todopelota.todopelota.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private TournamentService tournamentService;

    @PostMapping("/create/{tournamentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Message> createMessage(@PathVariable Long tournamentId, @RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message, tournamentService.findTournamentById(tournamentId).get());
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/get/{tournamentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Message>> getMessagesByTournament(@PathVariable Long tournamentId) {
        Optional<Tournament> tournament = tournamentService.findTournamentById(tournamentId);
        if (tournament.isPresent()) {
            List<Message> messages = messageService.getMessagesByTournament(tournament.get());
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{messageId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }
}