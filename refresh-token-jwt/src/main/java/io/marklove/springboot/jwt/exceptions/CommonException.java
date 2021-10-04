package io.marklove.springboot.jwt.exceptions;

import java.util.List;

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
