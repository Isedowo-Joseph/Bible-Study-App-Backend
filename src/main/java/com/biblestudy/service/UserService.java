package com.biblestudy.service;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.Friendship;
import com.biblestudy.model.Invitation;
import com.biblestudy.model.InvitationType;
import com.biblestudy.model.User;
import com.biblestudy.repository.BibleStudySessionRepository;
import com.biblestudy.repository.FriendshipRepository;
import com.biblestudy.repository.InvitationRepository;
import com.biblestudy.repository.MembershipRepository;
import com.biblestudy.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;
    private FriendshipRepository friendshipRepository;
    private InvitationRepository invitationRepository;
    private BibleStudySessionRepository bibleStudySessionRepository;
    private MembershipRepository membershipRepository;

    @Autowired
    public UserService(UserRepository userRepository, FriendshipRepository friendshipRepository,
            InvitationRepository invitationRepository, BibleStudySessionRepository bibleStudySessionRepository,
            MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.invitationRepository = invitationRepository;
        this.bibleStudySessionRepository = bibleStudySessionRepository;
        this.membershipRepository = membershipRepository;

    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            // Throw an exception if the User doesn't exist
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public List<User> getFriendships(Long userId) {
        List<Friendship> friendshipsU = friendshipRepository.findByUserId(userId);
        List<Friendship> friendshipsF = friendshipRepository.findByFriendId(userId);
        List<User> friends = new ArrayList<>();

        // Check friendships where the user is the initiator (userId is the 'user')
        if (friendshipsU != null) {
            for (Friendship friendship : friendshipsU) {
                friends.add(friendship.getFriend()); // Adding the 'friend' from the friendship entry
            }
        }

        // Check friendships where the user is the recipient (userId is the 'friend')
        if (friendshipsF != null) {
            for (Friendship friendship : friendshipsF) {
                friends.add(friendship.getUser()); // Adding the 'user' from the friendship entry
            }
        }

        return friends;
    }

    public List<Invitation> getInvitations(Long id) {
        return userRepository.findById(id).get().getInvites();
    }

    public void updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setName(userDetails.getName());
        user.setUserName(userDetails.getUserName());
        user.setPassword(userDetails.getPassword());
        user.setProfileImage(userDetails.getProfileImage());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setMissedDays(userDetails.getMissedDays());
        user.setBibleStudySessionId(userDetails.getBibleStudySessionId());
        user.setFriends(userDetails.getFriends());
        user.setCallSessions(userDetails.getCallSessions());
        userRepository.save(user);
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        // Fetch the user and friend
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
        // Remove the friend from the user's friends list
        user.getFriends().removeIf(friendship -> (friendship.getUser().getId().equals(userId)
                && friendship.getFriend().getId().equals(friendId))
                || (friendship.getFriend().getId().equals(userId) && friendship.getUser().getId().equals(friendId)));

        // Remove the user from the friend's friends list (bidirectional)
        friend.getFriends().removeIf(friendship -> (friendship.getUser().getId().equals(userId)
                && friendship.getFriend().getId().equals(friendId))
                || (friendship.getFriend().getId().equals(userId) && friendship.getUser().getId().equals(friendId)));
        ;
        // Fetch the friendship between the users
        Friendship friendship = friendshipRepository.findByEitherUserIdAndFriendId(userId, friendId);

        // If no friendship exists, throw an exception
        if (friendship == null) {
            throw new IllegalArgumentException("Friendship not found");
        }

        // Delete the friendship
        friendshipRepository.delete(friendship);
        // Save changes to both users
        userRepository.save(user);
        userRepository.save(friend); // Save both to keep bidirectional relationship

        // Optionally: remove any pending invitations between them
        invitationRepository.deleteBySenderAndReceiverAndType(
                userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")),
                userRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("Friend not found")),
                InvitationType.FRIENDSHIP);
    }

    public User authenticate(String username, String password) {
        // Fetch the user by username
        User user = userRepository.findByUserName(username);

        if (user != null) {
            // Assuming the passwords are stored in plain text (not recommended, use
            // password hashing instead)
            if (user.getPassword().equals(password)) {
                // Authentication successful, return user
                return user;
            } else {
                // Password does not match, throw an exception or handle as you see fit
                throw new RuntimeException("Invalid credentials");
            }
        } else {
            // User not found, throw an exception or handle as you see fit
            throw new RuntimeException("User not found");
        }
    }

    public void leaveGroup(Long userId, Long bibleId) {
        BibleStudySession bibleStudy = bibleStudySessionRepository.findById(bibleId)
                .orElseThrow(() -> new IllegalArgumentException("BibleStudy not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        bibleStudy.getMembers().removeIf(member -> member.getId().equals(userId));

        user.setMembership(null);
        userRepository.save(user);
        membershipRepository.deleteByUserIdAndBibleId(userId, bibleId);
        bibleStudySessionRepository.save(bibleStudy);
    }

}
