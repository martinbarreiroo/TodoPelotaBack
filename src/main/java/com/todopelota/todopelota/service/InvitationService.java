package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Invitation;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.InvitationRepository;
import com.todopelota.todopelota.repository.TournamentRepository;
import com.todopelota.todopelota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    public Invitation inviteUserToTournament(String senderName, String userName, Long tournamentId) {
        User sender = userRepository.findByUsername(senderName)
                .orElseThrow(() -> new NoSuchElementException("User not found with username : " + senderName));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new NoSuchElementException("User not found with username : " + userName));
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new NoSuchElementException("Tournament not found with id : " + tournamentId));

        // Check if an invitation already exists
        Optional<Invitation> existingInvitation = invitationRepository.findBySenderAndUserAndTournament(sender, user, tournament);
        if (existingInvitation.isPresent()) {
            // If an invitation already exists, return it
            return existingInvitation.get();
        }

        // Check if the user is already a participant in the tournament
        if (tournament.getInvitedUsers().contains(user)) {
            throw new IllegalArgumentException("User is already a participant in the tournament");
        }

        // If no invitation exists, create a new one
        Invitation invitation = new Invitation();
        invitation.setSenderName(sender);
        invitation.setUser(user);
        invitation.setTournament(tournament);
        invitation.setStatus("PENDING");

        return invitationRepository.save(invitation);
    }

    public void acceptInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found with id : " + invitationId));
        invitation.setStatus("ACCEPTED");

        // Add the user to the tournament's participants
        User user = invitation.getUser();
        Tournament tournament = invitation.getTournament();
        tournament.getParticipants().add(user);
        tournament.getInvitedUsers().add(user); // Add the user to the set of invited users (for the tournament's admin)
        tournament.getInvitedUsersToString().add(user.getUsername()); // Add the user's username to the set of invited users (for the tournament's admin)
        user.getTournaments().add(tournament); // Add the tournament to the user's set of tournaments

        invitationRepository.delete(invitation); // Save the invitation entity first

        userRepository.save(user); // Save the user entity
        tournamentRepository.save(tournament); // Save the tournament entity

    }

    public void rejectInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found with id : " + invitationId));
        invitation.setStatus("REJECTED");
        invitationRepository.delete(invitation);
    }

    public Iterable<Invitation> getInvitationsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id : " + userId));
        return invitationRepository.findByUserId(userId);
    }
}