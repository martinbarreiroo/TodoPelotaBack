package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Position;
import com.todopelota.todopelota.model.Role;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.TournamentRepository;
import com.todopelota.todopelota.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentService {

    static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private PositionService positionService;

    public Tournament createTournament(String name, String maxPlayers, String type, String description, User creator, Long admin) {
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

        Tournament savedTournament = tournamentRepository.save(tournament);

        // Create a new Position for the creator
        Position position = new Position();
        position.setUser(creator);
        position.setTournament(savedTournament);
        positionService.savePosition(position);

        return savedTournament;
    }

    public Optional<Tournament> findTournamentById(Long tournamentId) {

        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);
        if (tournament.isPresent()) {
            tournament.get().setAdminUsername(userRepository.findById(tournament.get().getAdminId()).get().getUsername());
            return tournament;
        } else {
            return Optional.empty();
        }

    }

    public void deleteTournament(Long tournamentId) {
        try {
            Tournament tournament = tournamentRepository.findById(tournamentId)
                    .orElseThrow(() -> new NoSuchElementException("Tournament not found with id : " + tournamentId));

            // Remove the tournament from all users that are invited to it
            Set<User> invitedUsers = new HashSet<>(tournament.getInvitedUsers());
            for (User user : invitedUsers) {
                Set<Tournament> joinedTournaments = new HashSet<>(user.getJoinedTournaments());
                joinedTournaments.remove(tournament);
                user.setJoinedTournamentsSet(joinedTournaments);
                userRepository.save(user);
            }

            // Delete the tournament

            tournamentRepository.delete(tournament);

            logger.info("Deleting tournament with id: {}", tournamentId);
            tournamentRepository.delete(tournament);
            logger.info("Tournament with id: {} deleted", tournamentId);
        } catch (Exception e) {
            logger.error("Error deleting tournament with id: {}", tournamentId, e);
        }
    }

    public Tournament save(Tournament updatedTournament) {
        return tournamentRepository.save(updatedTournament);
    }
}