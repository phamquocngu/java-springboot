package io.marklove.spring.security.jwt.exceptions;

import java.util.List;

public class BusinessException extends BaseRuntimeException {
    private List<Object> args;

    public BusinessException(String code, List<Object> args) {
        super(code);
        this.args = args;
    }

    public List<Object> getArgs() {
        return args;
    }
}
