package com.biblestudy.service;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.repository.BibleStudySessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BibleStudySessionService {
    private BibleStudySessionRepository bibleStudySessionRepository;

    @Autowired
    public BibleStudySessionService(BibleStudySessionRepository bsRepository) {
        this.bibleStudySessionRepository = bsRepository;
    }

    public BibleStudySession saveSession(BibleStudySession bs) {
        return bibleStudySessionRepository.save(bs);
    }

    public Optional<BibleStudySession> findSessionById(Long id) {
        return bibleStudySessionRepository.findById(id);
    }

    public List<BibleStudySession> findAllSessions() {
        return bibleStudySessionRepository.findAll();
    }

    public void deleteSession(Long id) {
        if (!bibleStudySessionRepository.existsById(id)) {
            // Throw an exception if the task doesn't exist
            throw new RuntimeException("BibleStudySession not found with id " + id);
        }
        bibleStudySessionRepository.deleteById(id);
    }

    public void updateTask(Long id, BibleStudySession bibleSessionDetails) {
        BibleStudySession session = bibleStudySessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BibleStudySession not found with id " + id));
        session.setDuration(bibleSessionDetails.getDuration());
        session.setReaderStarter(bibleSessionDetails.getReaderStarter());
        session.setNextDate(bibleSessionDetails.getNextDate());
        session.setDueDate(bibleSessionDetails.getDueDate());
        session.setWeekRatio(bibleSessionDetails.getWeekRatio());
        session.setCompleted(bibleSessionDetails.getCompleted());
        session.setMembers(bibleSessionDetails.getMembers());
        session.setInvites(bibleSessionDetails.getInvites());
        session.setBible(bibleSessionDetails.getBible());
    }

}
