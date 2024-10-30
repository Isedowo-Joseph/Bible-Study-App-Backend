package com.biblestudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblestudy.model.Invitation;
import com.biblestudy.model.InvitationStatus;
import com.biblestudy.model.InvitationType;
import com.biblestudy.service.InvitationService;
import com.biblestudy.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
@RequestMapping("/invitation")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class InvitationController {
    UserService userservice;
    InvitationService invitationService;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public InvitationController(UserService userservice, InvitationService invitationService,
            SimpMessagingTemplate messagingTemplate, InvitationService invitation) {
        this.userservice = userservice;
        this.invitationService = invitationService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/send/{senderId}/{receiverId}/{type}")
    public ResponseEntity<Invitation> sendInvitation(@PathVariable Long senderId, @PathVariable Long receiverId,
            @PathVariable InvitationType type) {
        Invitation invite = invitationService.sendInvitation(senderId, receiverId, type);

        // Notify the receiver (the user who should receive the invitation)
        messagingTemplate.convertAndSend("/topic/invitations/" + receiverId, invite);

        return ResponseEntity.ok(invite);
    }

    @PostMapping("/respond/{invitationId}/{status}")
    public ResponseEntity<Object> respondToInvitation(@PathVariable Long invitationId,
            @PathVariable InvitationStatus status) {
        Object response = this.invitationService.respondToInvitation(invitationId, status);
        return ResponseEntity.ok(response);
    }

    // Get all invitations for a user
    @GetMapping("/getInvitations/{userId}")
    public ResponseEntity<List<Invitation>> getInvitations(@PathVariable Long userId) {
        List<Invitation> invitations = invitationService.getInvitations(userId);
        return ResponseEntity.ok(invitations);
    }

    @DeleteMapping("removeInvitation/{invitationId}")
    public void deleteInvitation(@PathVariable Long invitationId) {
        this.invitationService.removeInvitation(invitationId);
    }
}
