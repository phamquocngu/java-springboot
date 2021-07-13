package io.marklove.spring.security.jwt.validations.annotations;

import io.marklove.spring.security.jwt.validations.validators.ResetPasswordRequestValidator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ngupq
 */
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ResetPasswordRequestValidator.class)
@Documented
public @interface ResetPassword {
}
