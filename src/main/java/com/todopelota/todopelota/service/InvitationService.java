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
                .orElseThrow(() -> new NoSuchElementException("Sender not found with username : " + senderName));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new NoSuchElementException("User not found with username : " + userName));
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new NoSuchElementException("Tournament not found with id : " + tournamentId));

        Invitation invitation = new Invitation();
        invitation.setUser(user);
        invitation.setSenderName(sender);
        invitation.setTournament(tournament);

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