package io.marklove.spring.security.jwt.payloads.responses.base;

/**
 * @author ngupq
 */
public class BaseValidatedError {
    private String code;
    private String field;
    private String error;

    public BaseValidatedError(String code, String field, String error) {
        this.code = code;
        this.field = field;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
