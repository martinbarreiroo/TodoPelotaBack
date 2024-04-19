package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.UserRepository;
import com.todopelota.todopelota.service.TournamentService;
import com.todopelota.todopelota.service.UserTournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user_tournaments")
@CrossOrigin
public class UserTournamentController {

    @Autowired
    private UserTournamentService userTournamentService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public Set<Tournament> getTournamentsByUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {

            return userTournamentService.getTournamentsByUser(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}