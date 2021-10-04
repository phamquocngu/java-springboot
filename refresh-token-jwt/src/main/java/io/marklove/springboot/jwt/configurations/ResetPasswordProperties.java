package io.marklove.springboot.jwt.configurations;

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
