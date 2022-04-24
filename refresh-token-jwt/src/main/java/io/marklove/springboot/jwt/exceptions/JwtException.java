package io.marklove.springboot.jwt.exceptions;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * @author ngupq
 */
@Getter
public class JwtException extends AuthenticationException {
    private String code;

    public JwtException(String code, String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtException(String code, String msg) {
        super(msg);
    }
}
