package io.marklove.spring.security.jwt.configurations;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngupq
 */
@Getter
@Setter
public class JwtProperties {
    private String secretKey;

    private int expirationMs;

    private int refreshExpirationMs;
}
