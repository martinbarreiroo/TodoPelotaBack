package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.*;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import com.todopelota.todopelota.repository.TournamentRepository;
import com.todopelota.todopelota.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private SoccerMatchRepository soccerMatchRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse updateUser(Long userId, UserUpdateRequest request) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setId(userId);
                    user.setUsername(request.getUsername());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setPosition(request.getPosition());
                    user.setDescription(request.getDescription());
                    User updatedUser = userRepository.save(user);

                    // Re-authenticate the user
                    Authentication auth = new UsernamePasswordAuthenticationToken(updatedUser.getUsername(), request.getPassword());
                    Authentication newAuth = authenticationManager.authenticate(auth);
                    SecurityContextHolder.getContext().setAuthentication(newAuth);

                    // Generate a new token
                    String newToken = jwtService.generateToken(updatedUser);

                    // Create a response object containing the new token and the user ID
                    AuthenticationResponse response = new AuthenticationResponse(newToken, updatedUser.getId());

                    return response;
                })
                .orElseThrow(() -> new NoSuchElementException("User not found with ID " + userId));
    }


    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
    }

    public void deleteUser(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id : " + userId));

            // Remove the user from all tournaments that they participate in
            Set<Tournament> tournamentsParticipatedByUser = new HashSet<>(user.getJoinedTournaments());
            for (Tournament tournament : tournamentsParticipatedByUser) {
                Set<User> invitedUsers = new HashSet<>();
                for (User invitedUser : tournament.getInvitedUsers()) {
                    if (!invitedUser.getId().equals(userId)) {
                        invitedUsers.add(invitedUser);
                    }
                }

                tournament.setInvitedUsersDeleteUser(invitedUsers);
                tournamentRepository.save(tournament);
            }

            // Remove the user from all soccer matches that they participate in
            Set<SoccerMatch> soccerMatchesParticipatedByUser = new HashSet<>(user.getSoccerMatches());
            for (SoccerMatch soccerMatch : soccerMatchesParticipatedByUser) {
                soccerMatch.getUsers().remove(user);
                soccerMatchRepository.save(soccerMatch);
            }

            // Delete the user
            logger.info("Deleting user with id: {}", userId);
            userRepository.delete(user);
            logger.info("User with id: {} deleted", userId);
        } catch (Exception e) {
            logger.error("Error deleting user with id: {}", userId, e);
        }
    }

    public User findUserByUsername(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + userName));
    }
}