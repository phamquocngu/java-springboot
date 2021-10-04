package io.marklove.springboot.jwt.payloads.requests.security;

import io.marklove.springboot.jwt.constants.ValidationCode;
import io.marklove.springboot.jwt.enums.ERole;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.*;

/**
 * @author ngupq
 */
@Getter
@Setter
public class CreateUserReq implements Serializable {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Size(min = 4, max = 20, message = ValidationCode.VALIDATED_SIZE)
    private String username;
 
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Email(message = ValidationCode.VALIDATED_EMAIL)
    @Size(max = 50, message = ValidationCode.VALIDATED_SIZE)
    private String email;
    
    private Set<ERole> role;
    
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Size(min = 6, max = 50, message = ValidationCode.VALIDATED_SIZE)
    private String password;

    private Boolean enable;

    private Boolean accountLocked;

    private Boolean accountExpired;

    private Boolean credentialsExpired;
}
