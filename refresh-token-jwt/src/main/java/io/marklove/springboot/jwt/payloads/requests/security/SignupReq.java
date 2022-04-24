package io.marklove.springboot.jwt.payloads.requests.security;

import io.marklove.springboot.jwt.constants.ValidationCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ngupq
 */
@Getter
@Setter
public class SignupReq implements Serializable {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Size(min = 3, max = 20, message = ValidationCode.VALIDATED_SIZE)
    private String username;

    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Email(message = ValidationCode.VALIDATED_EMAIL)
    @Size(max = 50, message = ValidationCode.VALIDATED_SIZE)
    private String email;
}
