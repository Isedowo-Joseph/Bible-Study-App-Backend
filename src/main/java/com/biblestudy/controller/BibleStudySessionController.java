package com.biblestudy.controller;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.service.BibleStudySessionService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
@RequestMapping("/study")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BibleStudySessionController {
    private BibleStudySessionService biblestudysessionservice;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public BibleStudySessionController(BibleStudySessionService studyService,
            SimpMessagingTemplate messagingTemplate) {
        this.biblestudysessionservice = studyService;
        this.messagingTemplate = messagingTemplate;
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

    @PostMapping(consumes = "application/json")
    public BibleStudySession createBibleSession(@RequestBody BibleStudySession bibleStudySession) {
        return biblestudysessionservice.saveSession(bibleStudySession);
    }

    // Assume this method is called when a Bible study is updated
    @PutMapping("/bibleStudy/{bibleStudyId}/update")
    public ResponseEntity<BibleStudySession> updateBibleStudySession(@PathVariable Long bibleStudyId,
            @RequestBody BibleStudySession updatedContent) {
        try {
            // Update the Bible study session
            biblestudysessionservice.updateBibleSession(bibleStudyId, updatedContent);

            // Fetch the updated session
            BibleStudySession updatedSession = biblestudysessionservice.findSessionById(bibleStudyId).orElseThrow();

            // Broadcast the updated session to members via WebSocket
            messagingTemplate.convertAndSend("/topic/bibleStudyUpdates/" + bibleStudyId, updatedSession);

            // Return the updated session as HTTP response
            return ResponseEntity.ok(updatedSession);
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

    @PostMapping("/timer/{bibleStudyId}/{duration}")
    public void startTimer(@PathVariable int bibleStudyId, @PathVariable int duration) {
        // Calculate expiry time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, duration);
        Date expiryTime = calendar.getTime();

        // Broadcast expiry time to all connected clients
        messagingTemplate.convertAndSend("/topic/timer/" + bibleStudyId, expiryTime);
    }
}
