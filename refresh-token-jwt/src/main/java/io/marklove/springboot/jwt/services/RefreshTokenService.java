package io.marklove.springboot.jwt.services;

import io.marklove.springboot.jwt.persistences.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
  Optional<RefreshToken> findByToken(String token);
  RefreshToken createRefreshToken(Long userId);
  RefreshToken resetRefreshToken(RefreshToken refreshToken);
  RefreshToken verifyExpiration(RefreshToken token);
  int deleteByUserId(Long userId);
}