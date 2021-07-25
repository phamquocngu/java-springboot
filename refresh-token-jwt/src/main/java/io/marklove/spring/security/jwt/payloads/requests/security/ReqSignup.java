package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author ngupq
 */
public class ReqSignup {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Size(min = 3, max = 20, message = ValidationCode.VALIDATED_SIZE)
    private String username;

    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Email(message = ValidationCode.VALIDATED_EMAIL)
    @Size(max = 50, message = ValidationCode.VALIDATED_SIZE)
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
