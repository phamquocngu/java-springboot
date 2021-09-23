package io.marklove.spring.security.jwt.configurations;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngupq
 */
@Getter
@Setter
public class ResetPasswordProperties {
    private int expirationHours;
}
