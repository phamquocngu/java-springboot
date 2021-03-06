package io.marklove.springboot.jwt.validations.validators;

import io.marklove.springboot.jwt.constants.ValidationCode;
import io.marklove.springboot.jwt.payloads.requests.security.VerifyResetPassReq;
import io.marklove.springboot.jwt.validations.annotations.ResetPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ngupq
 */
public class ResetPasswordRequestValidator implements ConstraintValidator<ResetPassword, VerifyResetPassReq> {

    private static final String NEW_PASSWORD = "new-password";

    @Override
    public boolean isValid(VerifyResetPassReq value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (value.getOldPassword() == value.getNewPassword()) {
            context.buildConstraintViolationWithTemplate(ValidationCode.VALIDATED_NEW_PASS).addPropertyNode(NEW_PASSWORD);
            return false;
        }

        return true;
    }
}
