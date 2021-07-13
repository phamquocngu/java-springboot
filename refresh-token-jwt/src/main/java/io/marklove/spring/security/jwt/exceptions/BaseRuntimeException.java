package io.marklove.spring.security.jwt.exceptions;

public class BaseRuntimeException extends RuntimeException{
    private String code;

    public BaseRuntimeException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
