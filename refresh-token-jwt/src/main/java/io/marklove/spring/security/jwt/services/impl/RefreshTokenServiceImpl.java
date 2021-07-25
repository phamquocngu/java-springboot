package io.marklove.spring.security.jwt.services.impl;

import io.marklove.spring.security.jwt.constants.AuthConstants;
import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.configurations.JwtProperties;
import io.marklove.spring.security.jwt.exceptions.CommonException;
import io.marklove.spring.security.jwt.exceptions.TokenRefreshException;
import io.marklove.spring.security.jwt.persistences.entities.RefreshToken;
import io.marklove.spring.security.jwt.persistences.entities.User;
import io.marklove.spring.security.jwt.persistences.repository.RefreshTokenRepository;
import io.marklove.spring.security.jwt.persistences.repository.UserRepository;
import io.marklove.spring.security.jwt.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ngupq
 */
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private JwtProperties jwtProps;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtProps.getRefreshExpirationMs()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(AuthConstants.Error.EXPIRED_REFRESH_TOKEN);
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(MessageCode.Error.C5008, null));
        return refreshTokenRepository.deleteByUser(user);
    }
}
