package io.marklove.spring.security.jwt.security.jwt;

import io.jsonwebtoken.*;
import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.configuration.JwtProperties;
import io.marklove.spring.security.jwt.exception.JwtException;
import io.marklove.spring.security.jwt.security.services.impl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Autowired
  private JwtProperties jwtProp;

  public String generateJwtToken(UserDetailsImpl userPrincipal) {
    return generateTokenFromUsername(userPrincipal.getUsername());
  }

  public String generateTokenFromUsername(String username) {
    return Jwts.builder().setSubject(username).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() +
                jwtProp.getExpirationMs())).signWith(SignatureAlgorithm.HS512, jwtProp.getSecretKey())
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtProp.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtProp.getSecretKey()).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
      throw new JwtException(MessageCode.Error.code5004);
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
      throw new JwtException(MessageCode.Error.code5005);
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
      throw new JwtException(MessageCode.Error.code5006);
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
      throw new JwtException(MessageCode.Error.code5007);
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
      throw new JwtException(MessageCode.Error.code5008);
    }
  }
}