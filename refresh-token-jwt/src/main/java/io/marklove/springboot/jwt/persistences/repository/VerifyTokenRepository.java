package io.marklove.springboot.jwt.persistences.repository;

import io.marklove.springboot.jwt.persistences.entities.User;
import io.marklove.springboot.jwt.persistences.entities.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ngupq
 */
@Repository
public interface VerifyTokenRepository extends JpaRepository<VerifyToken, Long> {
    Optional<VerifyToken> findByVerifyToken(String verifyToken);
    @Modifying
    int deleteByUser(User user);
}
