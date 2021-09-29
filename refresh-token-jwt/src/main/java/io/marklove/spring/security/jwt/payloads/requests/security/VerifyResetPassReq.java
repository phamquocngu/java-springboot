package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import io.marklove.spring.security.jwt.validations.annotations.ResetPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ngupq
 */
@Getter
@Setter
@ResetPassword
public class VerifyResetPassReq implements Serializable {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String token;

    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String oldPassword;

    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String newPassword;

    public VerifyResetPassReq(String token, String oldPassword, String newPassword) {
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
