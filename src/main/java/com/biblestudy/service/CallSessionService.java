package com.biblestudy.service;

import com.biblestudy.model.CallSession;
import com.biblestudy.model.User;
import com.biblestudy.repository.CallSessionRepository;
import com.biblestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CallSessionService {
    @Autowired
    private CallSessionRepository callSessionRepository;
    @Autowired
    private UserRepository userRepository;

    public CallSession createSession(List<String> usernames) {
        CallSession session = new CallSession();

        session.setSessionId(UUID.randomUUID().toString());
        List<User> participants = userRepository.findAllById(
                usernames.stream().map(userRepository::findByUserName).map(User::getId).collect(Collectors.toList()));
        session.setParticipants(participants);
        callSessionRepository.save(session);

        for (User user : participants) {
            user.getCallSessions().add(session);
            userRepository.save(user);
        }

        return session;
    }

    public CallSession addParticipant(String sessionId, String username) {
        CallSession session = callSessionRepository.findBySessionId(sessionId);
        User user = userRepository.findByUserName(username);
        if (session != null && user != null) {
            session.getParticipants().add(user);
            callSessionRepository.save(session);
            user.getCallSessions().add(session);
            userRepository.save(user);
        }
        return session;
    }
}