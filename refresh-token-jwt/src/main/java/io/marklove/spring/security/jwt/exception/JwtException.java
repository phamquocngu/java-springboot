package io.marklove.spring.security.jwt.exception;

public class JwtException extends BaseRuntimeException {
    public JwtException(String code) {
        super(code);
    }
}
