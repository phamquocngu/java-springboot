package io.marklove.spring.security.jwt.payload.response;

import io.marklove.spring.security.jwt.payload.response.base.BaseValidatedError;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ngupq
 */
public class ValidatedErrorResponse {
    private String code;
    private String message;
    private List<BaseValidatedError> errors;
    private LocalDateTime dateTime;

    public ValidatedErrorResponse(String code, String message, List<BaseValidatedError> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.dateTime = LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BaseValidatedError> getErrors() {
        return errors;
    }

    public void setErrors(List<BaseValidatedError> errors) {
        this.errors = errors;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
