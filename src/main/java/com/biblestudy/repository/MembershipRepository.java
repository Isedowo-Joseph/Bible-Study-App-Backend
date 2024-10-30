package com.biblestudy.repository;

import org.springframework.stereotype.Repository;

import com.biblestudy.model.Membership;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Membership m WHERE m.userId = :userId AND m.bibleStudySessionId = :bibleId")
    void deleteByUserIdAndBibleId(@Param("userId") Long userId, @Param("bibleId") Long bibleId);
}
