package com.biblestudy.repository;

import com.biblestudy.model.Friendship;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByUserId(Long userId);

    List<Friendship> findByFriendId(Long friendId);

    @Query("SELECT f FROM Friendship f WHERE (f.user.id = :userId AND f.friend.id = :friendId) OR (f.friend.id = :userId AND f.user.id = :friendId)")
    Friendship findByEitherUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
