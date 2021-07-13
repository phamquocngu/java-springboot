package io.marklove.spring.security.jwt.persistences.repository;

import java.util.Optional;

import io.marklove.spring.security.jwt.persistences.entities.RefreshToken;
import io.marklove.spring.security.jwt.persistences.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);
  @Modifying
  int deleteByUser(User user);
}
