package io.marklove.spring.security.jwt.security.services;

import io.marklove.spring.security.jwt.persistence.entities.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RefreshTokenService {
  Optional<RefreshToken> findByToken(String token);
  RefreshToken createRefreshToken(Long userId);
  RefreshToken verifyExpiration(RefreshToken token);
  int deleteByUserId(Long userId);
}