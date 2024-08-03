package com.biblestudy.controller;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.service.BibleStudySessionService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http:localhost:1304")
@RestController
@RequestMapping("/study")
public class BibleStudySessionController {
    BibleStudySessionService biblestudysessionservice;

    @Autowired
    public BibleStudySessionController(BibleStudySessionService studyService) {
        this.biblestudysessionservice = studyService;
    }

    @GetMapping
    public List<BibleStudySession> getAllBibleSessions() {
        return biblestudysessionservice.findAllSessions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibleStudySession> getBibleSessionById(@PathVariable Long id) {
        return biblestudysessionservice.findSessionById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public BibleStudySession createBibleSession(@RequestBody BibleStudySession bibleStudySession) {
        return biblestudysessionservice.saveSession(bibleStudySession);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibleStudySession> updateBibleStudySession(@PathVariable Long id,
            @RequestBody BibleStudySession bStudySession) {
        try {
            biblestudysessionservice.updateBibleSession(id, bStudySession);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBibleStudySession(@PathVariable Long id) {
        try {
            biblestudysessionservice.deleteSession(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
