package com.biblestudy.service;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.CallSession;
import com.biblestudy.model.User;
import com.biblestudy.repository.CallSessionRepository;
import com.biblestudy.repository.UserRepository;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CallSessionService {
    @Autowired
    private CallSessionRepository callSessionRepository;
    @Autowired
    private UserRepository userRepository;

    public CallSession saveCallSession(List<String> usernames) {
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

    public Optional<CallSession> findCallSessionById(Long id) {
        return callSessionRepository.findById(id);
    }

    public List<CallSession> findAllCallSessions() {
        return callSessionRepository.findAll();
    }

    public void deleteCallSession(Long id) {
        if (!callSessionRepository.existsById(id)) {
            // Throw an exception if the task doesn't exist
            throw new RuntimeException("CallSession not found with id " + id);
        }
        callSessionRepository.deleteById(id);
    }

    public CallSession addParticipant(String sessionId, String username) {
        CallSession callSession = callSessionRepository.findBySessionId(sessionId);
        User user = userRepository.findByUserName(username);
        if (callSession != null && user != null) {
            callSession.getParticipants().add(user);
            callSessionRepository.save(callSession);
            user.getCallSessions().add(callSession);
            userRepository.save(user);
        }
        return callSession;
    }

    public void updateTask(Long id, CallSession callSessionDetails) {
        CallSession session = callSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CallSession not found with id " + id));
        session.setSessionId(callSessionDetails.getSessionId());
        session.setParticipants(callSessionDetails.getParticipants());
    }
}