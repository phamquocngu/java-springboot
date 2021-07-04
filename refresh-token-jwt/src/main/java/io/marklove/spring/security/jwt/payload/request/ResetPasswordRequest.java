package io.marklove.spring.security.jwt.payload.request;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import io.marklove.spring.security.jwt.validation.annotations.ResetPassword;

import javax.validation.constraints.NotBlank;

/**
 * @author ngupq
 */
@ResetPassword
public class ResetPasswordRequest {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String token;
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String oldPassword;
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String newPassword;

    public ResetPasswordRequest(String token, String oldPassword, String newPassword) {
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
