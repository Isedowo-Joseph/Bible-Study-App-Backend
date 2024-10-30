package com.biblestudy.repository;

import com.biblestudy.model.Invitation;
import com.biblestudy.model.InvitationType;
import com.biblestudy.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    // Find invitations by receiver ID
    List<Invitation> findByReceiverId(Long receiverId);

    // Find invitations by sender, receiver, and type
    Optional<Invitation> findBySenderIdAndReceiverIdAndType(Long senderId, Long receiverId, InvitationType type);

    // Delete invitations by sender, receiver, and type
    @Modifying
    @Transactional
    @Query("DELETE FROM Invitation i WHERE i.sender = :sender AND i.receiver = :receiver AND i.type = :type")
    void deleteBySenderAndReceiverAndType(@Param("sender") User sender,
            @Param("receiver") User receiver,
            @Param("type") InvitationType type);
}
