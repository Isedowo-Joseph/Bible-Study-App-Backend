package com.biblestudy.repository;

import com.biblestudy.model.BibleStudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibleStudySessionRepository extends JpaRepository<BibleStudySession, Long> {

}
