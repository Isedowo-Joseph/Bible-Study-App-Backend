package com.biblestudy.service;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.User;
import com.biblestudy.repository.BibleStudySessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BibleStudySessionService {
    private BibleStudySessionRepository bibleStudySessionRepository;
    private UserService userService;

    @Autowired
    public BibleStudySessionService(BibleStudySessionRepository bsRepository, UserService userService) {
        this.bibleStudySessionRepository = bsRepository;
        this.userService = userService;
    }

    @Transactional // Ensure the method is transactional
    public BibleStudySession saveSession(BibleStudySession bs) {
        // Save the BibleStudySession
        BibleStudySession session = bibleStudySessionRepository.save(bs);

        // Fetch the user by ID
        User user = userService.findUserById(bs.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "user not found with id " + bs.getUserId()));

        // Update the bibleStudySessionId for the user
        user.setBibleStudySessionId(session.getId());

        // Save the updated user back to the database
        userService.saveUser(user); // Ensure you have a method to save the user

        return session;
    }

    public Optional<BibleStudySession> findSessionById(Long id) {
        return bibleStudySessionRepository.findById(id);
    }

    public List<BibleStudySession> findAllSessions() {
        return bibleStudySessionRepository.findAll();
    }

    public void deleteSession(Long id) {
        if (!bibleStudySessionRepository.existsById(id)) {
            // Throw an exception if the biblesession doesn't exist
            throw new RuntimeException("BibleStudySession not found with id " + id);
        }
        bibleStudySessionRepository.deleteById(id);
    }

    public BibleStudySession updateBibleSession(Long id, BibleStudySession bibleSessionDetails) {
        BibleStudySession session = bibleStudySessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BibleStudySession not found with id " + id));
        session.setBible(bibleSessionDetails.getBible());

        bibleStudySessionRepository.save(session);
        return session;
    }

}
