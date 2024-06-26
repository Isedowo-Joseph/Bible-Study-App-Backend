package com.biblestudy.service;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.repository.BibleStudySessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class BibleStudySessionService {
    private BibleStudySessionRepository bibleStudysessionRepository;
    @Autowired
    public BibleStudySessionService(BibleStudySessionRepository bsRepository){
        this.bibleStudysessionRepository = bsRepository;
    }
    public BibleStudySession saveSession(BibleStudySession bs){

        return bs;
    }
    public BibleStudySession findSessionById(Long id){

        return null;
    }
    public ArrayList<BibleStudySession> findAllSessions(){
        return null;
    }
    public void deleteSession(Long id){

    }
    public void updateSession(Long id, BibleStudySession sessionDetails){

    }
}
