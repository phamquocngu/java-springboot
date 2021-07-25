package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author ngupq
 */
public class ReqResetPass {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Email(message = ValidationCode.VALIDATED_EMAIL)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
