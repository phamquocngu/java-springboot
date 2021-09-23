package io.marklove.spring.security.jwt.configurations;

import io.marklove.spring.security.jwt.enums.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author ngupq
 */
@Getter
@Setter
public class SignupProperties {
    private Set<ERole>  defaultRoles;

    private int expirationHours;
}
