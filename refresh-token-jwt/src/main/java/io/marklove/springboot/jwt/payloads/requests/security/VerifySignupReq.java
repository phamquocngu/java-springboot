package io.marklove.springboot.jwt.payloads.requests.security;

import io.marklove.springboot.jwt.constants.ValidationCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ngupq
 */
@Getter
@Setter
public class VerifySignupReq implements Serializable {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    private String token;
}
