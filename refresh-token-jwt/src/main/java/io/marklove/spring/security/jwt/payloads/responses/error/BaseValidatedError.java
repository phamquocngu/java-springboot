package io.marklove.spring.security.jwt.payloads.responses.error;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngupq
 */
@Getter
@Setter
public class BaseValidatedError {
    private String code;
    private String field;
    private String error;

    public BaseValidatedError(String code, String field, String error) {
        this.code = code;
        this.field = field;
        this.error = error;
    }
}
