package io.marklove.spring.security.jwt.services;

import io.marklove.spring.security.jwt.persistences.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
  Optional<RefreshToken> findByToken(String token);
  RefreshToken createRefreshToken(Long userId);
  RefreshToken verifyExpiration(RefreshToken token);
  int deleteByUserId(Long userId);
}