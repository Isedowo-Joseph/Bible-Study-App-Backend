package com.biblestudy.controller;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.CallSession;
import com.biblestudy.service.CallSessionService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/call")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CallSessionController {
    CallSessionService callSessionService;

    @Autowired
    public CallSessionController(CallSessionService callSessionService) {
        this.callSessionService = callSessionService;
    }

    @GetMapping
    public List<CallSession> getCallSessions() {
        return callSessionService.findAllCallSessions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CallSession> getCallSession(@PathVariable Long id) {
        return this.callSessionService.findCallSessionById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public CallSession createCallSession(@RequestBody List<String> userNames) {
        return this.callSessionService.saveCallSession(userNames);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibleStudySession> updateCallSession(@PathVariable Long id,
            @RequestBody CallSession callSession) {
        try {
            this.callSessionService.updateCallSession(id, callSession);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCallSession(@PathVariable Long id) {
        try {
            this.callSessionService.deleteCallSession(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
