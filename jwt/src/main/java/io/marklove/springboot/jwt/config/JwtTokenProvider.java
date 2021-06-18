package io.marklove.springboot.jwt.config;

import io.jsonwebtoken.*;
import io.marklove.springboot.jwt.model.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private String JWT_SECRET = "Glorfindel";
    private long JWT_EXPIRATION = 604800000L;

    @Autowired
    private JwtConfiguration jwtConfiguration;

    //Init configuration for jwt
    @PostConstruct
    private void init() {
        try {
            if(jwtConfiguration != null) {
                JWT_SECRET = jwtConfiguration.getSecret();
                JWT_EXPIRATION = Long.parseLong(jwtConfiguration.getExpiration());
            }
        } catch (Exception e) {
            log.error("init JwtConfiguration error: " + e.getMessage());
        }
    }

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // create jwt from id của user.
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
