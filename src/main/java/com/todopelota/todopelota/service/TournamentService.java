package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Role;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.TournamentRepository;
import com.todopelota.todopelota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    public Tournament createTournament(String name, Integer maxPlayers, String type, String description, User creator, Long admin) {
        User user = userRepository.findById(admin)
                .orElseThrow(() -> new NoSuchElementException("User not found with id : " + admin));
        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setMaxParticipants(maxPlayers);
        tournament.setType(type);
        tournament.setDescription(description);
        tournament.setAdminId(admin);
        tournament.setAdminUsername(user.getUsername());
        creator.setRole(Role.ADMIN);
        tournament.setCreator(creator);

        return tournamentRepository.save(tournament);
    }

    public Optional<Tournament> findTournamentById(Long tournamentId) {

        return tournamentRepository.findById(tournamentId);
    }
}