package io.marklove.spring.security.jwt.validation.validators;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import io.marklove.spring.security.jwt.payload.request.ResetPasswordRequest;
import io.marklove.spring.security.jwt.validation.annotations.ResetPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ngupq
 */
public class ResetPasswordRequestValidator implements ConstraintValidator<ResetPassword, ResetPasswordRequest> {

    private static final String NEW_PASSWORD = "newPassword";

    @Override
    public boolean isValid(ResetPasswordRequest value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (value.getOldPassword() == value.getNewPassword()) {
            context.buildConstraintViolationWithTemplate(ValidationCode.VALIDATED_NEW_PASS).addPropertyNode(NEW_PASSWORD);
            return false;
        }

        return true;
    }
}
