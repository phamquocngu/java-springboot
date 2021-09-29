package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ngupq
 */
@Getter
@Setter
public class ResetPassReq implements Serializable {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Email(message = ValidationCode.VALIDATED_EMAIL)
    private String email;
}
