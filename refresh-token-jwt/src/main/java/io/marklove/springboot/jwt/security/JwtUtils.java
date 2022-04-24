package io.marklove.springboot.jwt.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.marklove.springboot.jwt.configurations.JwtProperties;
import io.marklove.springboot.jwt.constants.AuthConstants;
import io.marklove.springboot.jwt.exceptions.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
  private Key secretKey;

  @Autowired
  private JwtProperties jwtProp;

  @PostConstruct
  private void init() {
    secretKey = Keys.hmacShaKeyFor(jwtProp.getSecretKey().getBytes(StandardCharsets.UTF_8));
  }

  public String generateJwtToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtProp.getExpirationMs()))
        .signWith(secretKey)
        .compact();
  }

  public Jws<Claims> parseToken(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(jwtProp.getSecretKey()).build()
          .parseClaimsJws(token);
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
      throw new io.marklove.springboot.jwt.exceptions.JwtException(AuthConstants.Error.INVALID_JWT_SIGNATURE, e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw new io.marklove.springboot.jwt.exceptions.JwtException(AuthConstants.Error.INVALID_JWT_TOKEN, e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
      throw new io.marklove.springboot.jwt.exceptions.JwtException(AuthConstants.Error.JWT_TOKEN_EXPIRED, e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
      throw new io.marklove.springboot.jwt.exceptions.JwtException(AuthConstants.Error.JWT_TOKEN_UNSUPPORTED, e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw new JwtException(AuthConstants.Error.JWT_CLAIMS_EMPTY, e.getMessage());
    }
  }
}