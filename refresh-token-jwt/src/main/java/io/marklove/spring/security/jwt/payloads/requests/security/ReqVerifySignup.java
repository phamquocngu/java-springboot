package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;

import javax.validation.constraints.NotBlank;

/**
 * @author ngupq
 */
public class ReqVerifySignup {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
