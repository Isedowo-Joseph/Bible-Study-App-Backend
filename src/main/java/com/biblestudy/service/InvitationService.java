package com.biblestudy.service;

import com.biblestudy.model.Invitation;
import com.biblestudy.model.InvitationStatus;
import com.biblestudy.model.InvitationType;
import com.biblestudy.model.Membership;
import com.biblestudy.model.User;
import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.Friendship;
import com.biblestudy.repository.BibleStudySessionRepository;
import com.biblestudy.repository.FriendshipRepository;
import com.biblestudy.repository.InvitationRepository;
import com.biblestudy.repository.MembershipRepository;
import com.biblestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvitationService {
    private UserRepository userRepository;
    private FriendshipRepository friendshipRepository;
    private InvitationRepository invitationRepository;
    private BibleStudySessionRepository bibleStudySessionRepository;
    private MembershipRepository membershipRepository;

    @Autowired
    public InvitationService(UserRepository userRepository, InvitationRepository invitationRepository,
            BibleStudySessionRepository bibleStudySessionRepository, FriendshipRepository friendshipRepository,
            MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.bibleStudySessionRepository = bibleStudySessionRepository;
        this.friendshipRepository = friendshipRepository;
        this.membershipRepository = membershipRepository;
    }

    public Invitation sendInvitation(Long senderId, Long receiverId, InvitationType type) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + receiverId));

        // Check if an invitation of this type already exists between the sender and
        // receiver
        Optional<Invitation> existingInvitation = invitationRepository.findBySenderIdAndReceiverIdAndType(senderId,
                receiverId, type);
        if (existingInvitation.isPresent()) {
            throw new RuntimeException("Sorry, an invitation of this type already exists.");
        }

        // Create a new invitation if no existing one is found
        Invitation invitation = new Invitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setType(type);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setSentDate(new Date());

        // Save the invitation
        Invitation savedInvitation = invitationRepository.save(invitation);

        // Add the saved invitation to the receiver's invite list
        receiver.getInvites().add(savedInvitation);
        userRepository.save(receiver); // Update the receiver with the new invitation

        return savedInvitation;
    }

    public Object respondToInvitation(Long invitationId, InvitationStatus status) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id " + invitationId));

        invitation.setStatus(status);
        invitationRepository.save(invitation);
        if (invitation.getType() == InvitationType.FRIENDSHIP) {
            if (status == InvitationStatus.ACCEPTED) {
                User sender = invitation.getSender();
                User receiver = invitation.getReceiver();

                // Check if a friendship already exists between the users
                boolean alreadyFriends = sender.getFriends().stream()
                        .anyMatch(friendship -> friendship.getFriend().getId().equals(receiver.getId()));
                if (alreadyFriends) {
                    throw new RuntimeException("Friendship already exists between these users");
                }

                // Create a single Friendship object
                Friendship friendship = new Friendship();
                friendship.setUser(sender); // Sender is the one who initiated the friendship
                friendship.setFriend(receiver); // Receiver is the one being added as a friend

                // Save the new friendship only once
                friendshipRepository.save(friendship);

                // Add the friendship to the sender and receiver list of friends
                sender.getFriends().add(friendship);
                receiver.getFriends().add(friendship);
                userRepository.save(sender);
                userRepository.save(receiver);
                this.removeInvitation(invitationId);
                return friendship;
            } else if (status == InvitationStatus.DECLINED) {
                this.removeInvitation(invitationId);

            }
        } else if (invitation.getType() == InvitationType.MEMBERSHIP) {
            if (status == InvitationStatus.ACCEPTED) {
                User sender = invitation.getSender();
                User receiver = invitation.getReceiver();

                // Create membership object

                Membership membership = new Membership();
                membership.setUserId(receiver.getId());

                BibleStudySession BibleStudySession = bibleStudySessionRepository
                        .findById(sender.getBibleStudySessionId())
                        .orElseThrow(() -> new RuntimeException(
                                "bibleStudy not found with id " + sender.getBibleStudySessionId()));

                membership.setBibleStudySessionId(BibleStudySession.getId());
                membershipRepository.save(membership);

                // Add the receiver to the sender's members list
                BibleStudySession bs = bibleStudySessionRepository.findById(sender.getBibleStudySessionId())
                        .orElseThrow(() -> new RuntimeException(
                                "BibleStudy not found with id " + sender.getBibleStudySessionId()));

                receiver.setHasJoined(true);
                receiver.setMembership(membership);
                bs.getMembers().add(receiver);
                userRepository.save(receiver);
                membershipRepository.save(membership);
                this.removeInvitation(invitationId);
                return membership;
            } else if (status == InvitationStatus.DECLINED) {
                this.removeInvitation(invitationId);
            }
        }
        return invitation; // Return the updated invitation if no friendship/membership was created
    }

    public void removeInvitation(Long invitationId) {
        // find and delete invitation from sender's invite list
        invitationRepository.findById(invitationId).get().getSender().getInvites()
                .removeIf(invitation -> invitation.getId() == invitationId);

        // find and delete invitation from receiver's invite list
        invitationRepository.findById(invitationId).get().getReceiver().getInvites()
                .removeIf(invitation -> invitation.getId() == invitationId);

        // save the updated invitation
        invitationRepository.deleteById(invitationId);
    }

    // Fetch invitations for a specific user by userId
    public List<Invitation> getInvitations(Long userId) {
        // You can fetch invitations where the user is receiver
        return invitationRepository.findByReceiverId(userId);
    }
}