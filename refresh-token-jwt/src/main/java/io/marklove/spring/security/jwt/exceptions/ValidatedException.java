package io.marklove.spring.security.jwt.exceptions;

import org.springframework.validation.Errors;

/**
 * @author ngupq
 */
public class ValidatedException extends RuntimeException {
    private Errors errors;

    public ValidatedException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
