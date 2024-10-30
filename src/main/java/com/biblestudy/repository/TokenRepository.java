package com.biblestudy.repository;

import com.biblestudy.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByCustomerIdAndTokenAndTokenType(Long customerId, String token, String tokenType);
}
