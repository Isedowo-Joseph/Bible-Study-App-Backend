package com.biblestudy.repository;

import com.biblestudy.model.CallSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallSessionRepository extends JpaRepository<CallSession, Long> {
    CallSession findBySessionId(String sessionId);
}
