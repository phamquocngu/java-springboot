package io.marklove.spring.security.jwt.exceptions;

import java.util.List;
import java.util.function.Supplier;

public class CommonException extends BaseRuntimeException {
    private List<Object> args;

    public CommonException(String code, List<Object> args) {
        super(code);
        this.args = args;
    }

    public List<Object> getArgs() {
        return args;
    }
}
