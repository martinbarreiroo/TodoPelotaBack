package com.todopelota.todopelota.controller;

import com.todopelota.todopelota.model.Invitation;
import com.todopelota.todopelota.model.InvitationRequest;
import com.todopelota.todopelota.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitations")
@CrossOrigin
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/send-invitation")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Invitation> inviteUserToTournament(@RequestBody InvitationRequest request) {
        Invitation invitation = invitationService.inviteUserToTournament(request.getSenderName(),request.getUserName(), request.getTournamentId());
        return ResponseEntity.ok(invitation);
    }

    @PostMapping("/accept-invitation/{invitationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> acceptInvitation(@PathVariable Long invitationId) {
        invitationService.acceptInvitation(invitationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject-invitation/{invitationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> rejectInvitation(@PathVariable Long invitationId) {
        invitationService.rejectInvitation(invitationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-invitations/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Iterable<Invitation>> getInvitationsByUserId(@PathVariable Long userId) {
        Iterable<Invitation> invitations = invitationService.getInvitationsByUserId(userId);
        return ResponseEntity.ok(invitations);
    }


}