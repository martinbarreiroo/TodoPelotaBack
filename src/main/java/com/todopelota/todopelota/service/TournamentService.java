package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Role;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.TournamentRepository;
import com.todopelota.todopelota.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class TournamentService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


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
}