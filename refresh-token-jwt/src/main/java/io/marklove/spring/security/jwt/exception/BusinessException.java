package io.marklove.spring.security.jwt.exception;

public class BusinessException extends BaseRuntimeException {
    public BusinessException(String code) {
        super(code);
    }
}
