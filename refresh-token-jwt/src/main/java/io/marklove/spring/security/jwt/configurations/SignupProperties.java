package io.marklove.spring.security.jwt.configurations;

import io.marklove.spring.security.jwt.enums.ERole;

import java.util.Set;

/**
 * @author ngupq
 */
public class SignupProperties {
    private Set<ERole>  defaultRoles;
    private int expirationHours;

    public Set<ERole> getDefaultRoles() {
        return defaultRoles;
    }

    public void setDefaultRoles(Set<ERole> defaultRoles) {
        this.defaultRoles = defaultRoles;
    }

    public int getExpirationHours() {
        return expirationHours;
    }

    public void setExpirationHours(int expirationHours) {
        this.expirationHours = expirationHours;
    }
}
